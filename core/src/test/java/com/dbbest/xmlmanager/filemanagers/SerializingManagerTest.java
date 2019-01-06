package com.dbbest.xmlmanager.filemanagers;

import com.dbbest.xmlmanager.container.Container;
import com.dbbest.exceptions.SerializingException;
import com.dbbest.xmlmanager.filemanagers.serializers.XmlSerializer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class SerializingManagerTest {

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

        Writer writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream("fileTestSerializingManager3.xml"), StandardCharsets.UTF_8));
            writer.write("<root></root>");
            writer.close();
    }

    @After
    public void DeleteExistingFile(){
        File testFile = new File("fileTestSerializingManager.xml");
        testFile.delete();
        File testFile1 = new File("fileTestSerializingManager1.xml");
        testFile1.delete();
        File testFile2 = new File("fileTestSerializingManager2.xml");
        testFile2.delete();
        File testFile3 = new File("fileTestSerializingManager3.xml");
        testFile3.delete();
    }

    @Test
    public void shouldWriteValidXmlFileFromContainer() throws SerializingException, IOException {
        SerializingManager serializingManager = new SerializingManager();
        serializingManager.setContainer(container);
        serializingManager.setSerializer(new XmlSerializer());
        serializingManager.writeFile("fileTestSerializingManager.xml");
        String contents = new String(Files.readAllBytes(Paths.get("fileTestSerializingManager.xml")));
        Assert.assertEquals(contents,
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                "<root testKey=\"testValue\">testValue<![CDATA[<test>testValue</test>]]></root>");
    }

    @Test(expected = SerializingException.class)
    public void shouldThrowSerializingExceptionAsContainerNull() throws SerializingException {
        SerializingManager serializingManager = new SerializingManager();
        serializingManager.setSerializer(new XmlSerializer());
        serializingManager.writeFile("fileTestSerializingManager1.xml");
    }

    @Test(expected = SerializingException.class)
    public void shouldThrowSerializingExceptionAsSerializerNull() throws SerializingException {
        SerializingManager serializingManager = new SerializingManager();
        serializingManager.setContainer(container);
        serializingManager.writeFile("fileTestSerializingManager2.xml");
    }

    @Test(expected = SerializingException.class)
    public void shouldThrowSerializingExceptionAsFileExists() throws SerializingException {
        SerializingManager serializingManager = new SerializingManager();
        serializingManager.setContainer(container);
        serializingManager.setSerializer(new XmlSerializer());
        serializingManager.writeFile("fileTestSerializingManager3.xml");
    }
}