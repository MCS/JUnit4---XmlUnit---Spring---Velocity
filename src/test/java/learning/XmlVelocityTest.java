package learning;

import static org.custommonkey.xmlunit.XMLAssert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.ui.velocity.VelocityEngineUtils;

@ContextConfiguration(locations = "classpath:test-context.xml")
public class XmlVelocityTest extends AbstractJUnit4SpringContextTests {
  
  private static final String TEMPLATE = "greeter.vm.xml";
  
  @Autowired
  VelocityEngine velocityEngine;
  
  @BeforeClass
  public static void setupXmlUnit() {
    XMLUnit.setIgnoreWhitespace(true);
  }
  
  @Test
  public void greetWorld() throws Exception {
    // GIVEN
    String expected = "<greeter><greeting>Hello World!</greeting></greeter>";
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("name", "World");
    
    // WHEN
    String result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, TEMPLATE, "UTF-8", model);
    
    // THEN
    assertXMLEqual(expected, result);
  }
  
  @Test
  public void greetMom() throws Exception {
    // GIVEN
    String expected = "<greeter><greeting>Hello Mom!</greeting></greeter>";
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("name", "Mom");
    
    // WHEN
    String result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, TEMPLATE, "UTF-8", model);
    
    // THEN
    assertXMLEqual(expected, result);
  }
  
  @Test
  public void greetingWrongPerson() throws Exception {
    // GIVEN
    String expected = "<greeter><greeting>Hello Foo!</greeting></greeter>";
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("name", "Bar");
    
    // WHEN
    String result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, TEMPLATE, "UTF-8", model);
    
    // THEN
    assertXMLNotEqual(expected, result);
  }
}
