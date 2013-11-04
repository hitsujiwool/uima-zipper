

/* First created by JCasGen Fri Nov 08 18:04:34 CET 2013 */
package net.hitsujiwool.uima.zipper;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Fri Nov 08 18:04:34 CET 2013
 * XML source: /Users/ryo/uima-zipper/src/test/resources/ZipperTypeSystem.xml
 * @generated */
public class Source extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Source.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Source() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Source(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Source(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Source(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: hoge

  /** getter for hoge - gets 
   * @generated */
  public String getHoge() {
    if (Source_Type.featOkTst && ((Source_Type)jcasType).casFeat_hoge == null)
      jcasType.jcas.throwFeatMissing("hoge", "net.hitsujiwool.uima.zipper.Source");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Source_Type)jcasType).casFeatCode_hoge);}
    
  /** setter for hoge - sets  
   * @generated */
  public void setHoge(String v) {
    if (Source_Type.featOkTst && ((Source_Type)jcasType).casFeat_hoge == null)
      jcasType.jcas.throwFeatMissing("hoge", "net.hitsujiwool.uima.zipper.Source");
    jcasType.ll_cas.ll_setStringValue(addr, ((Source_Type)jcasType).casFeatCode_hoge, v);}    
   
    
  //*--------------*
  //* Feature: fuga

  /** getter for fuga - gets 
   * @generated */
  public String getFuga() {
    if (Source_Type.featOkTst && ((Source_Type)jcasType).casFeat_fuga == null)
      jcasType.jcas.throwFeatMissing("fuga", "net.hitsujiwool.uima.zipper.Source");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Source_Type)jcasType).casFeatCode_fuga);}
    
  /** setter for fuga - sets  
   * @generated */
  public void setFuga(String v) {
    if (Source_Type.featOkTst && ((Source_Type)jcasType).casFeat_fuga == null)
      jcasType.jcas.throwFeatMissing("fuga", "net.hitsujiwool.uima.zipper.Source");
    jcasType.ll_cas.ll_setStringValue(addr, ((Source_Type)jcasType).casFeatCode_fuga, v);}    
  }

    