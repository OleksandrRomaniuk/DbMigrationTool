package com.dbbest.xmlmanager.filemanagers.parsers.validator;

import com.dbbest.xmlmanager.exceptions.ParsingException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

public class XmlValidatorTest {

    @Before
    public void createTestFile() throws IOException {
        Files.write(Paths.get("testFileXml.txt"), Collections.singleton("<root></root>"));
        Files.write(Paths.get("testFileXml.xml"), Collections.singleton("<root></root>"));
    }

    @After
    public void deleteTestFile(){
        File testFile = new File("testFileXml.txt");
        testFile.delete();
        File testFile2 = new File("testFileXml.xml");
        testFile2.delete();
    }

    @Test (expected = ParsingException.class)
    public void shouldThrowParsingExceptionAsFileNull() throws ParsingException {
        new XmlValidator().validate("");
    }

    @Test (expected = ParsingException.class)
    public void shouldThrowParsingExceptionAsFileNotExists() throws ParsingException {
        new XmlValidator().validate("testxml.xml");
    }

    @Test (expected = ParsingException.class)
    public void shouldThrowParsingExceptionAsExtensionNotSupported() throws ParsingException {
        new XmlValidator().validate("testFileXml.txt");
    }

    @Test
    public void shoulReturnValidDocument() throws ParsingException {
        Assert.assertEquals(new XmlValidator().validate("testFileXml.xml").
            getDocumentElement().getNodeName(),"root");
        Assert.assertEquals(new XmlValidator().validate("testFileXml.xml").
            getDocumentElement().getNodeValue(),null);
        Assert.assertEquals(new XmlValidator().validate("testFileXml.xml").
            getDocumentElement().hasAttributes(),false);
        Assert.assertEquals(new XmlValidator().validate("testFileXml.xml").
            getDocumentElement().hasChildNodes(),false);
    }

}