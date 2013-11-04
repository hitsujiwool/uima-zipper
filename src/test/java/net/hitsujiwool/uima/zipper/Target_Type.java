
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
public class Target_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Target_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Target_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Target(addr, Target_Type.this);
  			   Target_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Target(addr, Target_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Target.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("net.hitsujiwool.uima.zipper.Target");
 
  /** @generated */
  final Feature casFeat_foo;
  /** @generated */
  final int     casFeatCode_foo;
  /** @generated */ 
  public String getFoo(int addr) {
        if (featOkTst && casFeat_foo == null)
      jcas.throwFeatMissing("foo", "net.hitsujiwool.uima.zipper.Target");
    return ll_cas.ll_getStringValue(addr, casFeatCode_foo);
  }
  /** @generated */    
  public void setFoo(int addr, String v) {
        if (featOkTst && casFeat_foo == null)
      jcas.throwFeatMissing("foo", "net.hitsujiwool.uima.zipper.Target");
    ll_cas.ll_setStringValue(addr, casFeatCode_foo, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Target_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_foo = jcas.getRequiredFeatureDE(casType, "foo", "uima.cas.String", featOkTst);
    casFeatCode_foo  = (null == casFeat_foo) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_foo).getCode();

  }
}



    