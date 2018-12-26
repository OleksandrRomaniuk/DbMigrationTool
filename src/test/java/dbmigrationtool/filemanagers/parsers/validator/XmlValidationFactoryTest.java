package dbmigrationtool.filemanagers.parsers.validator;

import com.dbbest.dbmigrationtool.exceptions.ParsingException;
import com.dbbest.dbmigrationtool.filemanagers.parsers.validator.XmlValidationFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

public class XmlValidationFactoryTest {

    @Before
    public void createTestFile() throws IOException {
        Files.write(Paths.get("invalidTestFileXmlCreatTestFile.xml"), Collections.singleton("ERROR<root></root>"));
        Files.write(Paths.get("validTestFileXmlCreatTestFile.xml"), Collections.singleton("<root>test</root>"));
    }

    @After
    public void deleteTestFile(){
        File testFile1 = new File("invalidTestFileXmlCreatTestFile.xml");
        testFile1.delete();
        //File testFile2 = new File("validTestFileXmlCreatTestFile.xml");
       // testFile2.delete();
    }

    @Test (expected = ParsingException.class)
    public void shouldThrowParsinfExceptionAsFileNotValid() throws ParsingException {
        new XmlValidationFactory().validateAndGetDocument(new File("invalidTestFileXmlCreatTestFile.xml"));
    }

    @Test
    public void shouldReturnValidDocument() throws ParsingException {
        Document document = new XmlValidationFactory().
            validateAndGetDocument(new File("validTestFileXmlCreatTestFile.xml"));
        System.out.println(document.getDocumentElement().getNodeName());
        Assert.assertEquals(document.getDocumentElement().getNodeName(), "root");
        Assert.assertEquals(document.getDocumentElement().getTextContent(), "test");
    }
}
