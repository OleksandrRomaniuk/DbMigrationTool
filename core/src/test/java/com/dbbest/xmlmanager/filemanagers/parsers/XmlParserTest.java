package com.dbbest.xmlmanager.filemanagers.parsers;

import com.dbbest.xmlmanager.container.Container;
import com.dbbest.xmlmanager.exceptions.ContainerException;
import com.dbbest.xmlmanager.exceptions.ParsingException;
import com.dbbest.xmlmanager.exceptions.SerializingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class XmlParserTest {

    private XmlParser testInstance;

    @Before
    public void setUp() {
        testInstance = new XmlParser();
    }

    @Test
    public void shouldParseValidFile() throws ParsingException, SerializingException, ContainerException {
        String filePathToValidFile = "src/test/resources/validFile.xml";

        Container<String> actualContainer = actualContainer = testInstance.parse(filePathToValidFile);
        Container<String> expectedContainer = getExpectedContainer();

        checkIfEquals(actualContainer, expectedContainer);
    }

    @Test(expected = ParsingException.class)
    public void shouldThrowExceptionIfFileIsInvalid() throws ParsingException, ContainerException {
        String filePathToInvalidFile = "src/test/resources/invalidFile.xml";

        Container<String> actualContainer = testInstance.parse(filePathToInvalidFile);
    }

    private void checkIfEquals(Container<String> actualContainer, Container<String> expectedContainer){
        Assert.assertEquals(actualContainer.getName(), expectedContainer.getName());
        Assert.assertEquals(actualContainer.getValue(), expectedContainer.getValue());
        if(actualContainer.hasAttributes() || expectedContainer.hasAttributes()) {
            Assert.assertEquals(actualContainer.hasAttributes(), expectedContainer.hasAttributes());
        }
        if (actualContainer.hasChildren() || expectedContainer.hasChildren()) {
            Assert.assertEquals(actualContainer.getChildren().size(), expectedContainer.getChildren().size());
        }
    }

    private void print(Container<String> container){
        System.out.println(container.getName());
        System.out.println(container.getValue());
        if(container.hasAttributes())
        for(Object entry: container.getAttributes().entrySet()) {
            System.out.println(((Map.Entry)entry).getKey() + " " + ((Map.Entry)entry).getValue());
        }
        if(container.hasChildren())
            for(Object childContainer: container.getChildren())
                print((Container) childContainer);
    }

    private Container<String> getExpectedContainer() throws SerializingException, ContainerException {
        Container<String> expectedContainer = new Container();
        expectedContainer.setName("root");
        expectedContainer.setAttributes(new HashMap());

        Container<String> child1Container = new Container();
        Container<String> child2Container = new Container();

        Container<String> child11Container = new Container();
        Container<String> child21Container = new Container();

        expectedContainer.addChild(child1Container);
        expectedContainer.addChild(child2Container);

        child1Container.setName("child1");
        child1Container.setParent(expectedContainer);
        HashMap attributes = new HashMap();
        attributes.put("id", "bk106");
        child1Container.setAttributes(attributes);
        child1Container.addChild(child11Container);

        child11Container.setValue("test text 1");
        child11Container.setParent(child1Container);
        child11Container.setAttributes(new HashMap());

        child2Container.setName("child2");
        child2Container.setParent(expectedContainer);
        child2Container.setAttributes(new HashMap());
        child2Container.addChild(child21Container);

        child21Container.setValue("<message>test text 1</message>");
        child21Container.setAttributes(new HashMap());
        child21Container.setParent(child2Container);

        return expectedContainer;
    }
}
