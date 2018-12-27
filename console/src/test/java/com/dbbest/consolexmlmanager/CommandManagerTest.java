package com.dbbest.consolexmlmanager;

import com.dbbest.consolexmlmanager.exceptions.CommandException;
import com.dbbest.xmlmanager.container.Container;
import com.dbbest.xmlmanager.exceptions.ParsingException;
import com.dbbest.xmlmanager.filemanagers.ParsingManager;
import com.dbbest.xmlmanager.filemanagers.parsers.XmlParser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class CommandManagerTest {

    File file;
    String filePathToValidFile = "src/test/resources/validFile.xml";

    @Before
    public void createFile() {

        file = new File(filePathToValidFile);
    }

    @After
    public void deleteFile() {
        File testFile = new File("commandManagerTestFile.xml");
        testFile.delete();
    }


    @Test
    public void shouldReadValidTestFile() throws CommandException {
        CommandManager commandManager = new CommandManager();
        String[] commandLine = new String[2];
        commandLine[0] = "-read";
        commandLine[1] = "src/test/resources/validFile.xml";
        commandManager.addCommands(commandLine);
        commandManager.execute();
        Container testContainer = commandManager.getInvoker().getContainerBuilt();
        Assert.assertEquals(testContainer.getName(), "root");
    }

    @Test
    public void shouldReadAndThenWriteValidFile() throws CommandException, ParsingException {
        CommandManager commandManager = new CommandManager();
        String[] commandLine = new String[4];
        commandLine[0] = "-read";
        commandLine[1] = "src/test/resources/validFile.xml";
        commandLine[2] = "-write";
        commandLine[3] = "commandManagerTestFile.xml";
        commandManager.addCommands(commandLine);
        commandManager.execute();
        Container testContainerExpected = commandManager.getInvoker().getContainerBuilt();
        ParsingManager parsingManager = new ParsingManager();
        parsingManager.setParser(new XmlParser());
        parsingManager.parse("commandManagerTestFile.xml");
        Container testContainerActual = parsingManager.getContainer();
        Assert.assertEquals(testContainerExpected.getName(), testContainerActual.getName());
    }

    @Test
    public void shouldFindElementSearchHorizontalName() throws CommandException {
        CommandManager commandManager = new CommandManager();
        String[] commandLine = new String[4];
        commandLine[0] = "-read";
        commandLine[1] = "src/test/resources/validFile.xml";
        commandLine[2] = "-searchHorizontalName";
        commandLine[3] = "child";
        commandManager.addCommands(commandLine);
        commandManager.execute();
        List<Container> listOfFoundElements = commandManager.getInvoker().getListOfFoundElements();
        Assert.assertEquals(listOfFoundElements.size(), 2);
    }

    @Test
    public void shouldFindElementSearchHorizontalValue() throws CommandException {
        CommandManager commandManager = new CommandManager();
        String[] commandLine = new String[4];
        commandLine[0] = "-read";
        commandLine[1] = "src/test/resources/validFile.xml";
        commandLine[2] = "-searchHorizontalValue";
        commandLine[3] = "text";
        commandManager.addCommands(commandLine);
        commandManager.execute();
        List<Container> listOfFoundElements = commandManager.getInvoker().getListOfFoundElements();
        Assert.assertEquals(listOfFoundElements.size(), 2);
    }

    @Test
    public void shouldFindElementSearchHorizontalKeyValue() throws CommandException {
        CommandManager commandManager = new CommandManager();
        String[] commandLine = new String[4];
        commandLine[0] = "-read";
        commandLine[1] = "src/test/resources/validFile.xml";
        commandLine[2] = "-searchHorizontalKeyValue";
        commandLine[3] = "id";
        commandManager.addCommands(commandLine);
        commandManager.execute();
        List<Container> listOfFoundElements = commandManager.getInvoker().getListOfFoundElements();
        Assert.assertEquals(listOfFoundElements.size(), 1);
    }

    @Test
    public void shouldFindElementSearchVerticalName() throws CommandException {
        CommandManager commandManager = new CommandManager();
        String[] commandLine = new String[4];
        commandLine[0] = "-read";
        commandLine[1] = "src/test/resources/validFile.xml";
        commandLine[2] = "-searchVerticalName";
        commandLine[3] = "root";
        commandManager.addCommands(commandLine);
        commandManager.execute();
        List<Container> listOfFoundElements = commandManager.getInvoker().getListOfFoundElements();
        Assert.assertEquals(listOfFoundElements.size(), 1);
    }

    @Test
    public void shouldFindElementSearchVerticalValue() throws CommandException {
        CommandManager commandManager = new CommandManager();
        String[] commandLine = new String[4];
        commandLine[0] = "-read";
        commandLine[1] = "src/test/resources/validFile.xml";
        commandLine[2] = "-searchVerticalValue";
        commandLine[3] = "text";
        commandManager.addCommands(commandLine);
        commandManager.execute();
        List<Container> listOfFoundElements = commandManager.getInvoker().getListOfFoundElements();
        Assert.assertEquals(listOfFoundElements.size(), 2);
    }

    @Test
    public void shouldFindElementSearchVerticalKeyValue() throws CommandException {
        CommandManager commandManager = new CommandManager();
        String[] commandLine = new String[4];
        commandLine[0] = "-read";
        commandLine[1] = "src/test/resources/validFile.xml";
        commandLine[2] = "-searchVerticalKeyValue";
        commandLine[3] = "id";
        commandManager.addCommands(commandLine);
        commandManager.execute();
        List<Container> listOfFoundElements = commandManager.getInvoker().getListOfFoundElements();
        Assert.assertEquals(listOfFoundElements.size(), 1);
    }

    @Test(expected = CommandException.class)
    public void shouldThrowCommandException() throws CommandException {
        CommandManager commandManager = new CommandManager();
        String[] commandLine = new String[4];
        commandLine[0] = "read";
        commandLine[1] = "src/test/resources/validFile.xml";
        commandLine[2] = "searchVerticalKeyValue";
        commandLine[3] = "id";
        commandManager.addCommands(commandLine);
        commandManager.execute();
        List<Container> listOfFoundElements = commandManager.getInvoker().getListOfFoundElements();
    }
}