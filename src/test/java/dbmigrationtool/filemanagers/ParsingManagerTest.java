package dbmigrationtool.filemanagers;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.containers.DbList;
import com.dbbest.dbmigrationtool.exceptions.ParsingException;
import com.dbbest.dbmigrationtool.filemanagers.ParsingManager;
import com.dbbest.dbmigrationtool.filemanagers.parsers.XmlParser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class ParsingManagerTest {

    @Test
    public void shouldParseXmlFileAndReturnValidContainer() throws ParsingException {
        ParsingManager parsingManager = new ParsingManager();
        parsingManager.setParser(new XmlParser());
        parsingManager.parse("src/test/resources/validFile.xml");
        Container container = parsingManager.getContainer();
        Assert.assertEquals(container.getName(), "root");
        DbList children = container.getChildren();
        Container leaf1 = ((Container)((Container)children.item(0)).getChildren().item(0));
        Assert.assertEquals(leaf1.getValue(), "test text 1");
        Assert.assertEquals(((Container)children.item(0)).getAttributes().get("id"), "bk106");
    }

    @Test(expected = ParsingException.class)
    public void shouldThrowParsingException() throws ParsingException {
        ParsingManager parsingManager = new ParsingManager();
        parsingManager.parse("src/test/resources/validFile.xml");
    }
}