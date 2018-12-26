import com.dbbest.dbmigrationtool.console.CommandManager;
import com.dbbest.dbmigrationtool.console.CommandRead;
import com.dbbest.dbmigrationtool.console.CommandWrite;
import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.exceptions.ParsingException;
import com.dbbest.dbmigrationtool.exceptions.SerializingException;
import com.dbbest.dbmigrationtool.filemanagers.ParsingManager;
import com.dbbest.dbmigrationtool.filemanagers.parsers.XmlParser;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;
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
