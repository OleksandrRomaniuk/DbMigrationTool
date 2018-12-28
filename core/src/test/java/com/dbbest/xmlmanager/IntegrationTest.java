package com.dbbest.xmlmanager;

import com.dbbest.consolexmlmanager.CommandManager;
import com.dbbest.consolexmlmanager.CommandRead;
import com.dbbest.consolexmlmanager.CommandWrite;
import com.dbbest.consolexmlmanager.exceptions.CommandException;
import com.dbbest.xmlmanager.container.Container;
import com.dbbest.xmlmanager.exceptions.ParsingException;
import com.dbbest.xmlmanager.exceptions.SerializingException;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.List;

public class IntegrationTest {

    String expectedXml = "src/test/resources/TestFile.xml";
    String actualXml = "src/test/resources/TestFileResultedFromIntegrationTest.xml";

    @Test
    public void shouldReadXmlFileThenWriteFileAfterComparesTwoFileAndAsksTesterToValidateDifferences() throws ParsingException, SerializingException, IOException, SAXException, CommandException {

        String[] commandLine = new String[4];
        commandLine[0] = "-read";
        commandLine[1] = expectedXml;
        commandLine[2] = "-write";
        commandLine[3] = actualXml;

        CommandManager commandManager = new CommandManager();
        commandManager.addCommands(commandLine);
        commandManager.execute();

        BufferedReader expectedXmlContent = new BufferedReader(new FileReader(new File(expectedXml)));
        BufferedReader actualXmlContent = new BufferedReader(new FileReader(new File(actualXml)));

        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);

        DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(expectedXmlContent, actualXmlContent));

        List<?> allDifferences = diff.getAllDifferences();

        Assert.assertEquals("Differences found: "+ diff.toString(), 0, allDifferences.size());
    }

    @After
    public void deleteFile() {
        File testFile = new File(actualXml);
        testFile.delete();
    }
}
