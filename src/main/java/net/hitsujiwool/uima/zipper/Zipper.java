package net.hitsujiwool.uima.zipper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.hitsujiwool.uima.zipper.resources.MultiKeyMap;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceAccessException;
import org.apache.uima.resource.ResourceInitializationException;

public class Zipper extends JCasAnnotator_ImplBase {
  
  public static final String MESSAGE_DIGEST = "net.hitsujiwool.uima.zipper.Zipper_Messages";  
  
  private MultiKeyMap map;
  
  private String sourceTypeName;
  
  private String[] sourceFeatureNames;
  
  private String targetTypeName;
  
  private String targetFeatureName;
    
  public void initialize(UimaContext ctx) throws ResourceInitializationException {
    try {
      this.map = (MultiKeyMap) ctx.getResourceObject("MultiKeyMap");    
    } catch (ResourceAccessException e) {
      throw new ResourceInitializationException(e);
    }
        
    this.sourceTypeName = (String) ctx.getConfigParameterValue("SourceType");
    
    if (this.sourceTypeName == null) {
      throw new ResourceInitializationException(MESSAGE_DIGEST, "configuration_empty_error", new Object[]{ "SourceType" });
    }
        
    this.targetTypeName = (String) ctx.getConfigParameterValue("TargetType");
    
    if (this.targetTypeName == null) {
      throw new ResourceInitializationException(MESSAGE_DIGEST, "configuration_empty_error", new Object[]{ "TargetType" });
    }
    
    this.targetFeatureName = (String) ctx.getConfigParameterValue("TargetFeature");
    
    this.sourceFeatureNames = (String[]) ctx.getConfigParameterValue("SourceFeatures");
  }
  
  @Override
  public void process(JCas jCas) throws AnalysisEngineProcessException {
    Type sourceType = this.findType(jCas, this.sourceTypeName);
    Type targetType = this.findType(jCas, this.targetTypeName);
    
    try {
      Feature targetFeature = this.findFeature(targetType, this.targetFeatureName);
      List<Feature> features = this.findFeatures(sourceType, this.sourceFeatureNames);
              
      AnnotationIndex<Annotation> subIterIndex = jCas.getAnnotationIndex();
      AnnotationIndex<Annotation> index = jCas.getAnnotationIndex(sourceType);

      for (Annotation anno : index) {            
        Annotation target = this.detectTargetAnnotation(subIterIndex, anno, targetType);
                
        Map<String, String> values = this.getStringValues(anno, features.toArray(new Feature[features.size()]));
              
        String mappedValue = this.map.get(values);
        if (mappedValue != null) {      
          if (target == null) {
            this.create(jCas, targetFeature, mappedValue, anno.getBegin(), anno.getEnd());
          } else {
            this.update(target, targetFeature, mappedValue);
          }
        }
      }
    } catch (Exception e) {
      throw new AnalysisEngineProcessException(e);
    }    
  }
  
  private void create(JCas jCas, Feature f, String val, int begin, int end) {
    Type type = f.getDomain();
    AnnotationFS anno = jCas.getCas().createAnnotation(type, begin, end);
    anno.setStringValue(f, val);
    jCas.addFsToIndexes(anno);
  }
  
  private void update(Annotation anno, Feature f, String value) {
    anno.setStringValue(f, value);
  }
    
  private Annotation detectTargetAnnotation(AnnotationIndex<Annotation> index, Annotation anno, Type type) {
    FSIterator<Annotation> iter = index.subiterator(anno);
    Annotation target = null;
    while (iter.isValid()) {
      Annotation targetCandidate = iter.get();
      if (targetCandidate.getType().equals(type)) {
        target = targetCandidate;
        break;
      }
      iter.moveToNext();
    }
    return target;
  }
  
  private Map<String, String> getStringValues(Annotation anno, Feature... features) {
    Map<String, String> values = new HashMap<String, String>();
    for (Feature f : features) {
      values.put(f.getShortName(), anno.getStringValue(f));
    }
    return values;
  }
  
  private Type findType(JCas jCas, String typeName) throws AnalysisEngineProcessException {
    Type type = jCas.getTypeSystem().getType(typeName);
    if (type == null) {
      throw new AnalysisEngineProcessException(MESSAGE_DIGEST, "type_not_found_error", new Object[]{ typeName });
    }
    return type;
  }
  
  private Feature findFeature(Type type, String featureName) throws AnalysisEngineProcessException {
    Feature feature = type.getFeatureByBaseName(featureName);
    if (feature == null) {
      throw new AnalysisEngineProcessException(MESSAGE_DIGEST, "feature_not_found_error", new Object[]{ featureName, type.getName() });      
    }
    return feature;
  }
  
  private List<Feature> findFeatures(Type type, String[] featureNames) throws AnalysisEngineProcessException {
    List<Feature> features = new ArrayList<Feature>();
    try {
      for (String featureName : featureNames) {
        features.add(this.findFeature(type, featureName));
      }
    } catch (AnalysisEngineProcessException e) {
      throw e;
    }
    return features;
  }
  
}
