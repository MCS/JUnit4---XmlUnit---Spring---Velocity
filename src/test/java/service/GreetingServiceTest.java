package service;

import org.apache.velocity.exception.VelocityException;
import static org.custommonkey.xmlunit.XMLAssert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * An example how to combine JUnit, XMLUnit, Spring and Velocity to create 
 * powerful XML tests.
 * 
 * @author Marcus Krassmann
 */
@ContextConfiguration(locations = "classpath:test-context.xml")
public class GreetingServiceTest extends AbstractJUnit4SpringContextTests {

    private static final String TEMPLATE = "greeter.vm.xml";
    @Autowired
    VelocityEngine velocityEngine;
    GreetingService greeter;

    @BeforeClass
    public static void setupXmlUnit() {
        XMLUnit.setIgnoreWhitespace(true);
    }

    @Before
    public void setup() {
        greeter = new GreetingService();
    }

    private String renderExpectedXml(String name) throws VelocityException {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", name);
        String expected = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, TEMPLATE, "UTF-8", model);
        return expected;
    }

    @Test
    public void greetWorld() throws Exception {
        // GIVEN
        String name = "World";
        String expected = renderExpectedXml(name);

        // WHEN
        String result = greeter.xmlGreet(name);

        // THEN
        assertXMLEqual(expected, result);
    }

    @Test
    public void greetMom() throws Exception {
        // GIVEN
        String name = "Mom";
        String expected = renderExpectedXml(name);

        // WHEN
        String result = greeter.xmlGreet(name);

        // THEN
        assertXMLEqual(expected, result);
    }

    @Test
    public void greetingWrongPerson() throws Exception {
        String expected = renderExpectedXml("Bar");

        // WHEN
        String result = greeter.xmlGreet("Foo");

        // THEN
        assertXMLNotEqual(expected, result);
    }
}
