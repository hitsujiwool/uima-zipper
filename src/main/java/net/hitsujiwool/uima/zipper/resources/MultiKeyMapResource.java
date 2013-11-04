package net.hitsujiwool.uima.zipper.resources;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.xml.sax.SAXException;

public class MultiKeyMapResource implements MultiKeyMap {
  
  private Map<Map<String, String>, String> map;
  
  @Override
  public String get(Map<String, String> key) {
    return this.map.get(key);
  }
    
  @Override
  public void load(DataResource data) throws ResourceInitializationException {
    try {
      
      SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
      MultiKeyMapSaxHandler handler = new MultiKeyMapSaxHandler();
      parser.parse(data.getInputStream(), handler);
      this.map = handler.getIndex();
      
    } catch (IOException e) {
      
    } catch (SAXException e) {
      
    } catch (ParserConfigurationException e) {
      
    }
  }

}
