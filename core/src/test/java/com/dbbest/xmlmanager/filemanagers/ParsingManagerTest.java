package com.dbbest.xmlmanager.filemanagers;

import com.dbbest.xmlmanager.container.Container;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.ParsingException;
import com.dbbest.xmlmanager.filemanagers.parsers.XmlParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ParsingManagerTest {

    @Test
    public void shouldParseXmlFileAndReturnValidContainer() throws ParsingException, ContainerException {
        ParsingManager parsingManager = new ParsingManager();
        parsingManager.setParser(new XmlParser());
        parsingManager.parse("src/test/resources/validFile.xml");
        Container container = parsingManager.getContainer();
        Assert.assertEquals(container.getName(), "root");
        List children = container.getChildren();
        Container leaf1 = ((Container)((Container)children.get(0)).getChildren().get(0));
        Assert.assertEquals(leaf1.getValue(), "test text 1");
        Assert.assertEquals(((Container)children.get(0)).getAttributes().get("id"), "bk106");
    }

    @Test(expected = ParsingException.class)
    public void shouldThrowParsingException() throws ParsingException, ContainerException {
        ParsingManager parsingManager = new ParsingManager();
        parsingManager.parse("src/test/resources/validFile.xml");
    }
}