package com.dbbest.xmlmanager.filemanagers.serializers;

import com.dbbest.xmlmanager.container.Container;
import com.dbbest.exceptions.SerializingException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class XmlSerializerTest {
    private Container<String> container;

    @Before
    public void setUpConzainer() throws Exception {
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

    }

    @After
    public void deleteTestFile() throws Exception {
        File testFile = new File("testFileXml.xml");
        testFile.delete();
    }

    @Test
    public void shouldWriteValidXmlDocument() throws SerializingException, IOException {
        XmlSerializer xmlSerializer = new XmlSerializer();
        xmlSerializer.setContainer(container);
        xmlSerializer.writeFile("testFileXml.xml");
        String contents = new String(Files.readAllBytes(Paths.get("testFileXml.xml")));
        Assert.assertEquals(contents,
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><root testKey=" +
                "\"testValue\">testValue<![CDATA[<test>testValue</test>]]></root>");
    }

    @Test(expected = SerializingException.class)
    public void shouldThrowSerializingException() throws SerializingException {
        XmlSerializer xmlSerializer = new XmlSerializer();
        //xmlSerializer.setContainer(container);
        xmlSerializer.writeFile("testFileXml.xml");
    }
}