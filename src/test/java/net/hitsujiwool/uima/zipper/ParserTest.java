package net.hitsujiwool.uima.zipper;

import java.io.FileInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.hitsujiwool.uima.zipper.resources.MultiKeyMapSaxHandler;

import org.junit.Test;

public class ParserTest {
  
  @Test
  public void parse() {
    
    try {
      
      SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
      MultiKeyMapSaxHandler handler = new MultiKeyMapSaxHandler();
      parser.parse(new FileInputStream("src/test/resources/test-map.xml"), handler);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println(e.getMessage());      
    }
    
    
  }

}
