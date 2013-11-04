
/* First created by JCasGen Fri Nov 08 18:04:34 CET 2013 */
package net.hitsujiwool.uima.zipper;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Fri Nov 08 18:04:34 CET 2013
 * @generated */
public class Source_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Source_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Source_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Source(addr, Source_Type.this);
  			   Source_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Source(addr, Source_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Source.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("net.hitsujiwool.uima.zipper.Source");
 
  /** @generated */
  final Feature casFeat_hoge;
  /** @generated */
  final int     casFeatCode_hoge;
  /** @generated */ 
  public String getHoge(int addr) {
        if (featOkTst && casFeat_hoge == null)
      jcas.throwFeatMissing("hoge", "net.hitsujiwool.uima.zipper.Source");
    return ll_cas.ll_getStringValue(addr, casFeatCode_hoge);
  }
  /** @generated */    
  public void setHoge(int addr, String v) {
        if (featOkTst && casFeat_hoge == null)
      jcas.throwFeatMissing("hoge", "net.hitsujiwool.uima.zipper.Source");
    ll_cas.ll_setStringValue(addr, casFeatCode_hoge, v);}
    
  
 
  /** @generated */
  final Feature casFeat_fuga;
  /** @generated */
  final int     casFeatCode_fuga;
  /** @generated */ 
  public String getFuga(int addr) {
        if (featOkTst && casFeat_fuga == null)
      jcas.throwFeatMissing("fuga", "net.hitsujiwool.uima.zipper.Source");
    return ll_cas.ll_getStringValue(addr, casFeatCode_fuga);
  }
  /** @generated */    
  public void setFuga(int addr, String v) {
        if (featOkTst && casFeat_fuga == null)
      jcas.throwFeatMissing("fuga", "net.hitsujiwool.uima.zipper.Source");
    ll_cas.ll_setStringValue(addr, casFeatCode_fuga, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Source_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_hoge = jcas.getRequiredFeatureDE(casType, "hoge", "uima.cas.String", featOkTst);
    casFeatCode_hoge  = (null == casFeat_hoge) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_hoge).getCode();

 
    casFeat_fuga = jcas.getRequiredFeatureDE(casType, "fuga", "uima.cas.String", featOkTst);
    casFeatCode_fuga  = (null == casFeat_fuga) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_fuga).getCode();

  }
}



    