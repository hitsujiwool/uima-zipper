

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
public class Target extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Target.class);
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
  protected Target() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Target(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Target(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Target(JCas jcas, int begin, int end) {
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
  //* Feature: foo

  /** getter for foo - gets 
   * @generated */
  public String getFoo() {
    if (Target_Type.featOkTst && ((Target_Type)jcasType).casFeat_foo == null)
      jcasType.jcas.throwFeatMissing("foo", "net.hitsujiwool.uima.zipper.Target");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Target_Type)jcasType).casFeatCode_foo);}
    
  /** setter for foo - sets  
   * @generated */
  public void setFoo(String v) {
    if (Target_Type.featOkTst && ((Target_Type)jcasType).casFeat_foo == null)
      jcasType.jcas.throwFeatMissing("foo", "net.hitsujiwool.uima.zipper.Target");
    jcasType.ll_cas.ll_setStringValue(addr, ((Target_Type)jcasType).casFeatCode_foo, v);}    
  }

    