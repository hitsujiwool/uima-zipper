package net.hitsujiwool.uima.zipper.resources;

import java.util.Map;

import org.apache.uima.resource.SharedResourceObject;

/**
 * Interface of MultiKeyMap which maps multiple feature values to a value.
 * 
 * @author Ryo Murayama
 *
 */

public interface MultiKeyMap extends SharedResourceObject {
  
  /**
   * Gets the mapped value. 
   * The argument must be a Map which maps (feature name) => (value used for key).
   */
  
  public String get(Map<String, String> key);

}
