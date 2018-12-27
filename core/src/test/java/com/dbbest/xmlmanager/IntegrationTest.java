package com.dbbest.xmlmanager;

import com.dbbest.consolexmlmanager.CommandRead;
import com.dbbest.consolexmlmanager.CommandWrite;
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

    @Test
    public void shouldReadXmlFileThenWriteFileAfterComparesTwoFileAndAsksTesterToValidateDifferences() throws ParsingException, SerializingException, IOException, SAXException {

        String expectedXml = "src/test/resources/TestFile.xml";
        String actualXml = "src/test/resources/TestFileResultedFromIntegrationTest.xml";

        CommandRead commandRead = new CommandRead(expectedXml);
        commandRead.execute();
        Container container = commandRead.getBuiltContainer();

        CommandWrite commandWrite = new CommandWrite(actualXml);
        commandWrite.setContainerToWrite(container);
        commandWrite.execute();

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
        File testFile = new File("src/test/resources/TestFileResultedFromIntegrationTest.xml");
        testFile.delete();
    }
}
