package com.dbbest.xmlmanager.filemanagers.serializers;

import com.dbbest.xmlmanager.container.Container;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;

public class XmlNodeBuilderTest {

    private Container<String> container;
    private Document document;
    private XmlNodeBuilder xmlNodeBuilder;

    @Before
    public void setUp() throws Exception {
        container = new Container();
        container.setName("root");
        HashMap attributes = new HashMap();
        attributes.put("testKey", "testValue");
        container.setAttributes(attributes);
        Container<String> childContainer1 = new Container();
        childContainer1.setValue("testValue");
        childContainer1.setParent(container);
        container.addChild(childContainer1);

        Container<String> childContainer2 = new Container();
        childContainer2.setValue("<test>testValue</test>");
        childContainer2.setParent(container);
        container.addChild(childContainer2);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.newDocument();

        xmlNodeBuilder = new XmlNodeBuilder(document, container);
        xmlNodeBuilder.build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldBildAndRetrunValidNode() {
        document = xmlNodeBuilder.getDocument();
        Assert.assertEquals(document.getDocumentElement().getNodeName(), "root");
        Assert.assertEquals(document.getDocumentElement().getAttribute("testKey"), "testValue");
        Assert.assertEquals(document.getDocumentElement().getChildNodes()
            .item(0).getNodeValue(), "testValue");
        Assert.assertEquals(document.getDocumentElement().getChildNodes()
            .item(1).getNodeValue(), "<test>testValue</test>");
    }
}