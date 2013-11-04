package net.hitsujiwool.uima.zipper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.hitsujiwool.uima.zipper.Zipper;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;

public class ZipperTest {

  private Zipper zipper;

  private JCas jCas;

  private Type sourceType;

  private Type targetType;

  /**
   * Helper method to access to private method.
   * 
   * @param name
   * @param classes
   * @return
   * @throws NoSuchMethodException
   */

  private Method getMethod(String name, Class<?>... classes) throws NoSuchMethodException {
    Method m = Zipper.class.getDeclaredMethod(name, classes);
    m.setAccessible(true);
    return m;
  }

  /**
   * Helper method for method calling.
   * 
   * @throws Exception
   */

  @SuppressWarnings("unchecked")
  private <T> T call(Method m, Object receiver, Object... args) throws Throwable {
    try {
      return (T) m.invoke(receiver, args);
    } catch (InvocationTargetException e) {
      throw e.getCause();
    } catch (Exception e) {
      Assert.fail(e.getMessage());
      throw e;
    }
  }

  @Before
  public void before() throws Exception {
    this.zipper = new Zipper();
    this.jCas = JCasFactory.createJCas("src.test.resources.ZipperTypeSystem");
    this.sourceType = this.jCas.getTypeSystem().getType("net.hitsujiwool.uima.zipper.Source");
    this.targetType = this.jCas.getTypeSystem().getType("net.hitsujiwool.uima.zipper.Target");
  }

  /**
   * Executes overall process using analysis engine description XML. Featues used for mapping is
   * (hoge, fuga) => foo, mapped value is (baaaa, bleat) => baaaableat and create new annotation.
   * See src/test/resources/test-map.xml.
   * 
   * @throws Exception
   */

  @Test
  public void createAndMap() throws Exception {
    AnalysisEngine ae = AnalysisEngineFactory.createEngine("src.test.resources.Zipper");
    JCas jCas = ae.newJCas();
    Source source = (Source) jCas.getCas().createAnnotation(this.sourceType, 0, 5);
    source.setHoge("baaaa");
    source.setFuga("bleat");
    jCas.addFsToIndexes(source);
    ae.process(jCas);
    FSIterator<Annotation> iter = jCas.getAnnotationIndex(this.targetType).iterator();

    if (iter.isValid()) {
      Target target = (Target) iter.get();
      Assert.assertEquals("baaaableat", target.getFoo());
    } else {
      Assert.fail("no annotation");
    }
  }

  /**
   * Executes overall process using analysis engine description XML. Featues used for mapping is
   * (hoge, fuga) => foo, mapped value is (baaaa, bleat) => baaaableat, and update existing
   * annotation. See src/test/resources/test-map.xml.
   * 
   * @throws Exception
   */

  @Test
  public void updateAndMap() throws Exception {
    AnalysisEngine ae = AnalysisEngineFactory.createEngine("src.test.resources.Zipper");
    JCas jCas = ae.newJCas();
    Source source = (Source) jCas.getCas().createAnnotation(this.sourceType, 0, 5);
    source.setHoge("baaaa");
    source.setFuga("bleat");
    Target target = (Target) jCas.getCas().createAnnotation(this.targetType, 0, 5);
    jCas.addFsToIndexes(source);
    jCas.addFsToIndexes(target);
    ae.process(jCas);

    Assert.assertEquals("baaaableat", target.getFoo());
  }

  /**
   * Executes overall process using analysis engine description XML. Featues used for mapping is
   * (hoge, fuga) => foo, key pairs (bow, meow) are ignored. See src/test/resources/test-map.xml.
   * 
   * @throws Exception
   */

  @Test
  public void ignoreMapping() throws Exception {
    AnalysisEngine ae = AnalysisEngineFactory.createEngine("src.test.resources.Zipper");
    JCas jCas = ae.newJCas();
    Source source = (Source) jCas.getCas().createAnnotation(this.sourceType, 0, 5);
    source.setHoge("bow");
    source.setFuga("meow");
    Target target = (Target) jCas.getCas().createAnnotation(this.targetType, 0, 2);
    target.setFoo("this value is alive");
    jCas.addFsToIndexes(source);
    jCas.addFsToIndexes(target);
    ae.process(jCas);

    Assert.assertEquals("this value is alive", target.getFoo());
  }

  /**
   * Create a new annotation, set the value of feature foo, and index it.
   * 
   * @throws Exception
   */

  @Test
  public void create() throws Throwable {
    Method m = this.getMethod("create", JCas.class, Feature.class, String.class, int.class,
            int.class);

    Feature foo = this.targetType.getFeatureByBaseName("foo");
    String expected = "new value";

    this.call(m, this.zipper, this.jCas, foo, expected, 0, 1);

    FSIterator<Annotation> iter = this.jCas.getAnnotationIndex(this.targetType).iterator();
    if (iter.isValid()) {
      Assert.assertEquals(expected, iter.get().getStringValue(foo));
    } else {
      Assert.fail("no annotation");
    }

  }

  /**
   * Update the value of feature foo.
   * 
   * @throws Exception
   */

  @Test
  public void update() throws Throwable {
    Method m = this.getMethod("update", Annotation.class, Feature.class, String.class);

    Target target = new Target(this.jCas);
    target.setFoo("before");
    Feature foo = this.targetType.getFeatureByBaseName("foo");
    String expected = "after";

    this.call(m, this.zipper, target, foo, expected);

    Assert.assertEquals(expected, target.getFoo());
  }

  /**
   * Get multiple feature (hoge and fuga) values at once.
   * 
   * @throws Exception
   */

  @Test
  public void getStringValues() throws Throwable {
    Method m = this.getMethod("getStringValues", Annotation.class, Feature[].class);

    Source source = new Source(this.jCas);
    source.setHoge("value of hoge");
    source.setFuga("value of fuga");

    Feature hoge = this.sourceType.getFeatureByBaseName("hoge");
    Feature fuga = this.sourceType.getFeatureByBaseName("fuga");

    Map<String, String> expected = new HashMap<String, String>();
    expected.put("hoge", source.getHoge());
    expected.put("fuga", source.getFuga());

    Map<String, String> actual = this.call(m, this.zipper, source, new Feature[] { hoge, fuga });

    Assert.assertEquals(expected, actual);
  }

  /**
   * If source annotation covers an annotation whose type equals to target type, return it for
   * mapping target.
   * 
   * @throws Exception
   */

  @Test
  public void detectTargetAnnotationForCoveredTarget() throws Throwable {
    Method m = this.getMethod("detectTargetAnnotation", AnnotationIndex.class, Annotation.class,
            Type.class);

    AnnotationFS source = this.jCas.getCas().createAnnotation(this.sourceType, 0, 5);
    AnnotationFS target = this.jCas.getCas().createAnnotation(this.targetType, 0, 2);
    AnnotationIndex<Annotation> index = this.jCas.getAnnotationIndex();
    this.jCas.addFsToIndexes(source);
    this.jCas.addFsToIndexes(target);

    Object actual = this.call(m, this.zipper, index, source, this.targetType);

    Assert.assertTrue(actual instanceof Target);
  }

  /**
   * If there is no covered annotation whose type equals to target type, return null.
   * 
   * @throws Exception
   */

  @Test
  public void detectTargetAnnotationForUncoveredTarget() throws Throwable {
    Method m = this.getMethod("detectTargetAnnotation", AnnotationIndex.class, Annotation.class,
            Type.class);

    AnnotationFS source = this.jCas.getCas().createAnnotation(this.sourceType, 0, 5);
    AnnotationFS target = this.jCas.getCas().createAnnotation(this.targetType, 0, 7);
    AnnotationIndex<Annotation> index = this.jCas.getAnnotationIndex();
    this.jCas.addFsToIndexes(source);
    this.jCas.addFsToIndexes(target);

    Target actual = this.call(m, this.zipper, index, source, this.targetType);

    Assert.assertNull(actual);
  }

  /**
   * Finds a type Source by its name "net.hitsujiwool.uima.Source".
   * 
   * @throws Exception
   */

  @Test
  public void findType() throws Throwable {
    Method m = this.getMethod("findType", JCas.class, String.class);

    Type actual = this.call(m, this.zipper, this.jCas, this.sourceType.getName());

    Assert.assertEquals(this.sourceType, actual);
  }

  /**
   * Throws an Exception for unknown type name "baaaaa".
   * 
   * @throws Exception
   */

  @Test(expected = AnalysisEngineProcessException.class)
  public void findTypeThrowsExceptionForUnknownTypeName() throws Throwable {
    Method m = this.getMethod("findType", JCas.class, String.class);

    String unknownTypeName = "baaaaa";

    this.call(m, this.zipper, this.jCas, unknownTypeName);
  }

  /**
   * Find a feature by its name "hoge".
   * 
   * @throws Exception
   */

  @Test
  public void findFeature() throws Throwable {
    Method m = this.getMethod("findFeature", Type.class, String.class);

    String featureNameToFind = "hoge";

    Feature feature = this.call(m, this.zipper, this.sourceType, featureNameToFind);

    Assert.assertEquals(feature.getName(), "net.hitsujiwool.uima.zipper.Source:hoge");
  }

  /**
   * Throws an Exception for unknown feature name "baaaaa".
   * 
   * @throws Exception
   */

  @Test(expected = AnalysisEngineProcessException.class)
  public void findFeatureThrowsExceptionForUnknownFeatureName() throws Throwable {
    Method m = this.getMethod("findFeature", Type.class, String.class);

    String unknownFeatureName = "baaaaa";

    this.call(m, this.zipper, this.sourceType, unknownFeatureName);
  }

  /**
   * Find multiple features (hoge, fuga) at once.
   * 
   * @throws Exception
   */

  @Test
  public void findFeatures() throws Throwable {
    Method m = this.getMethod("findFeatures", Type.class, String[].class);
    String[] fNames = { "hoge", "fuga" };

    List<Feature> features = this.call(m, this.zipper, this.sourceType, fNames);

    String[] actual = new String[2];
    for (int i = 0; i < 2; i++) {
      actual[i] = features.get(i).getName();
    }
    String[] expected = { "net.hitsujiwool.uima.zipper.Source:hoge", "net.hitsujiwool.uima.zipper.Source:fuga" };

    Assert.assertArrayEquals(expected, actual);
  }
}
