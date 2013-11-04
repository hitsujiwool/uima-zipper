package net.hitsujiwool.uima.zipper.resources;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

public class MultiKeyMapSaxHandler extends DefaultHandler2 {

  private static String INITIAL_STATE = "*initial*";

  private static String MAPPING_STATE = "mapping";

  private static String MAP_STATE = "map";

  private static String SOURCES_STATE = "sources";

  private static String SOURCE_STATE = "source";

  private static String TARGET_STATE = "target";

  private Deque<State> stateStack = new ArrayDeque<State>();

  private ResultMap currentResult;

  private Map<Map<String, String>, String> index = new HashMap<Map<String, String>, String>();

  public MultiKeyMapSaxHandler() throws SAXException {
    this.stateStack.addFirst(StateFactory.create(INITIAL_STATE));
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attr)
          throws SAXException {
    this.getCurrentState().accept(qName);
    this.stateStack.addFirst(StateFactory.create(qName));
    if (qName.equals(MAP_STATE)) {
      this.registerMap();
    } else if (qName.equals(SOURCE_STATE)) {
      this.registerSource(attr.getValue("feature"), attr.getValue("value"));
    } else if (qName.equals(TARGET_STATE)) {
      this.registerTarget(attr.getValue("value"));
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) {
    this.stateStack.removeFirst();
    if (qName.equals(MAP_STATE)) {
      this.buildIndex(this.currentResult);
    }
  }

  @Override
  public void characters(char[] chars, int start, int length) throws SAXException {
    String value = new String(chars, start, length);
    if (value.matches("\\s*\\S+\\s*")) {
      throw new SAXException("Invalid sequence " + value);
    }
  }

  public Map<Map<String, String>, String> getIndex() {
    return this.index;
  }

  private State getCurrentState() {
    return this.stateStack.getFirst();
  }

  private void buildIndex(ResultMap map) {
    this.index.put(map.getKeys(), map.getValue());
  }

  private void registerMap() throws SAXException {
    this.currentResult = new ResultMap();
  }

  private void registerSource(String feature, String value) throws SAXException {
    this.currentResult.setKey(feature, value);
  }

  private void registerTarget(String value) throws SAXException {
    this.currentResult.setValue(value);
  }

  @SuppressWarnings("serial")
  private static class StateFactory {

    private static List<String> TAGS = Arrays.asList(INITIAL_STATE, MAPPING_STATE, MAP_STATE,
            SOURCES_STATE, SOURCE_STATE, TARGET_STATE);

    private static Map<String, List<String>> EXTRACTION_RULES = new HashMap<String, List<String>>() {
      {
        put(INITIAL_STATE, Arrays.asList(MAPPING_STATE));
        put(MAPPING_STATE, Arrays.asList(MAP_STATE));
        put(MAP_STATE, Arrays.asList(SOURCES_STATE, TARGET_STATE));
        put(SOURCES_STATE, Arrays.asList(SOURCE_STATE));
      }
    };

    private static Map<String, Integer> NUM_CHILDREN = new HashMap<String, Integer>() {
      {
        put(MAP_STATE, 1);
        put(SOURCE_STATE, 0);
        put(TARGET_STATE, 0);
      }
    };

    public static State create(String name) throws SAXException {
      if (TAGS.contains(name)) {
        List<String> rule = EXTRACTION_RULES.get(name);
        if (rule == null) {
          rule = new ArrayList<String>();
        }
        if (NUM_CHILDREN.containsKey(name)) {
          return new State(name, rule, NUM_CHILDREN.get(name));
        } else {
          return new State(name, rule);
        }
      } else {
        throw new SAXException("Invalid tag <" + name + ">.");
      }
    }

  }

  private static class ResultMap {

    private Map<String, String> keys = new HashMap<String, String>();

    private String value;

    public void setKey(String f, String val) throws SAXException {
      if (this.keys.get(f) == null) {
        this.keys.put(f, val);
      } else {
        throw new SAXException("Key for feature " + f + " is already set.");
      }
    }

    public Map<String, String> getKeys() {
      return this.keys;
    }

    public String getValue() {
      return this.value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }

  private static class State {

    private String name;

    private Deque<String> toBeExtracted;

    private List<String> extractionRule;

    private int numChildren = 0;

    private int maxChildren;

    private boolean hasLimitation = false;

    public State(String name, List<String> extractionRule, int maxChildren) {
      this(name, extractionRule);
      this.maxChildren = maxChildren;
      this.hasLimitation = true;
    }

    public State(String name, List<String> extractionRule) {
      this.name = name;
      this.extractionRule = extractionRule;
      this.toBeExtracted = new ArrayDeque<String>(extractionRule);
    }

    private boolean isVisited() {
      return this.hasLimitation && this.maxChildren <= this.numChildren;
    }

    private String getName() {
      return name;
    }

    private void accept(String tagName) throws SAXException {
      if (this.isVisited()) {
        throw new SAXException("Tag <" + this.getName() + "> cannot accept it's children any more.");
      }
      String expected = this.toBeExtracted.getFirst();
      if (expected.equals(tagName)) {
        this.toBeExtracted.removeFirst();
      } else {
        throw new SAXException("Expected tag was <" + expected + ">, but <" + tagName
                + "> was given.");
      }
      if (this.toBeExtracted.isEmpty()) {
        this.numChildren++;
        this.toBeExtracted = new ArrayDeque<String>(extractionRule);
      }
    }

    @Override
    public String toString() {
      return this.getName();
    }
  }

}