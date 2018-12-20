package tests;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.exceptions.SerializingException;
import com.dbbest.dbmigrationtool.filemanagers.serializers.XmlSerializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class XmlSerializerTest {

    @Before
    public void setUp() throws Exception {
        String text = "<author>Corets, Eva</author>";
        File file = new File("test.xml");
        try (BufferedWriter output = new BufferedWriter(new FileWriter(file));) {
            output.write(text);
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        File file = new File("test.xml");
        file.delete();
    }

    @Test (expected = SerializingException.class)
    public void writeFileMethodTestContainerNotSetException() throws SerializingException {
        XmlSerializer testXmlSerializer = new XmlSerializer();
        testXmlSerializer.writeFile("test2.xml");
    }
/*
    @Test (expected = SerializingException.class)
    public void writeFileMethodTestFileAlreadyExistsException() throws SerializingException {
        XmlSerializer testXmlSerializer = new XmlSerializer();
        Container<String> container = new Container();
        container.setName("test");
        testXmlSerializer.setContainer(container);
        testXmlSerializer.writeFile("test.xml");
    }
*/
}