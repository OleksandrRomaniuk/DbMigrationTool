package com.dbbest.consolexmlmanager;

import com.dbbest.consolexmlmanager.exceptions.CommandException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.ParsingException;
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
    public void shouldReadValidTestFile() throws CommandException, DatabaseException {
        CommandManager commandManager = new CommandManager();
        String[] commandLine = new String[2];
        commandLine[0] = "-read";
        commandLine[1] = "src/test/resources/validFile.xml";
        commandManager.addCommands(commandLine);
        commandManager.execute();
        Container testContainer = commandManager.getContext().getBuiltContainer();
        Assert.assertEquals(testContainer.getName(), "root");
    }

    @Test
    public void shouldReadAndThenWriteValidFile() throws CommandException, ParsingException, ContainerException, DatabaseException {
        CommandManager commandManager = new CommandManager();
        String[] commandLine = new String[4];
        commandLine[0] = "-read";
        commandLine[1] = "src/test/resources/validFile.xml";
        commandLine[2] = "-write";
        commandLine[3] = "commandManagerTestFile.xml";
        commandManager.addCommands(commandLine);
        commandManager.execute();
        Container testContainerExpected = commandManager.getContext().getBuiltContainer();
        ParsingManager parsingManager = new ParsingManager();
        parsingManager.setParser(new XmlParser());
        parsingManager.parse("commandManagerTestFile.xml");
        Container testContainerActual = parsingManager.getContainer();
        Assert.assertEquals(testContainerExpected.getName(), testContainerActual.getName());
    }

    @Test
    public void shouldFindElementSearchHorizontalName() throws CommandException, DatabaseException {
        CommandManager commandManager = new CommandManager();
        String[] commandLine = new String[5];
        commandLine[0] = "-read";
        commandLine[1] = "src/test/resources/validFile.xml";
        commandLine[2] = "-search";
        commandLine[3] = "h/n";
        commandLine[4] = "child";
        commandManager.addCommands(commandLine);
        commandManager.execute();
        List<Container> listOfFoundElements = commandManager.getContext().getListOfFoundElements();
        Assert.assertEquals(listOfFoundElements.size(), 2);
    }

    @Test
    public void shouldFindElementSearchHorizontalValue() throws CommandException, DatabaseException {
        CommandManager commandManager = new CommandManager();
        String[] commandLine = new String[5];
        commandLine[0] = "-read";
        commandLine[1] = "src/test/resources/validFile.xml";
        commandLine[2] = "-search";
        commandLine[3] = "h/v";
        commandLine[4] = "test";
        commandManager.addCommands(commandLine);
        commandManager.execute();
        List<Container> listOfFoundElements = commandManager.getContext().getListOfFoundElements();
        Assert.assertEquals(listOfFoundElements.size(), 1);
    }

    @Test
    public void shouldFindElementSearchHorizontalKeyValue() throws CommandException, DatabaseException {
        CommandManager commandManager = new CommandManager();
        String[] commandLine = new String[6];
        commandLine[0] = "-read";
        commandLine[1] = "src/test/resources/validFile.xml";
        commandLine[2] = "-search";
        commandLine[3] = "h/a";
        commandLine[4] = "id";
        commandLine[5] = "bk106";
        commandManager.addCommands(commandLine);
        commandManager.execute();
        List<Container> listOfFoundElements = commandManager.getContext().getListOfFoundElements();
        Assert.assertEquals(listOfFoundElements.size(), 1);
    }

    @Test
    public void shouldFindElementSearchVerticalName() throws CommandException, DatabaseException {
        CommandManager commandManager = new CommandManager();
        String[] commandLine = new String[5];
        commandLine[0] = "-read";
        commandLine[1] = "src/test/resources/validFile.xml";
        commandLine[2] = "-search";
        commandLine[3] = "v/n";
        commandLine[4] = "root";
        commandManager.addCommands(commandLine);
        commandManager.execute();
        List<Container> listOfFoundElements = commandManager.getContext().getListOfFoundElements();
        Assert.assertEquals(listOfFoundElements.size(), 1);
    }

    @Test
    public void shouldFindElementSearchVerticalValue() throws CommandException, DatabaseException {
        CommandManager commandManager = new CommandManager();
        String[] commandLine = new String[5];
        commandLine[0] = "-read";
        commandLine[1] = "src/test/resources/validFile.xml";
        commandLine[2] = "-search";
        commandLine[3] = "v/v";
        commandLine[4] = "test";
        commandManager.addCommands(commandLine);
        commandManager.execute();
        List<Container> listOfFoundElements = commandManager.getContext().getListOfFoundElements();
        Assert.assertEquals(listOfFoundElements.size(), 1);
    }

    @Test
    public void shouldFindElementSearchVerticalKeyValue() throws CommandException, DatabaseException {
        CommandManager commandManager = new CommandManager();
        String[] commandLine = new String[6];
        commandLine[0] = "-read";
        commandLine[1] = "src/test/resources/validFile.xml";
        commandLine[2] = "-search";
        commandLine[3] = "v/a";
        commandLine[4] = "id";
        commandLine[5] = "bk106";
        commandManager.addCommands(commandLine);
        commandManager.execute();
        List<Container> listOfFoundElements = commandManager.getContext().getListOfFoundElements();
        Assert.assertEquals(listOfFoundElements.size(), 1);
    }

    @Test(expected = CommandException.class)
    public void shouldThrowCommandException() throws CommandException, DatabaseException {
        CommandManager commandManager = new CommandManager();
        String[] commandLine = new String[5];
        commandLine[0] = "read";
        commandLine[1] = "src/test/resources/validFile.xml";
        commandLine[2] = "search";
        commandLine[3] = "fff";
        commandLine[4] = "id";
        commandManager.addCommands(commandLine);
        commandManager.execute();
        List<Container> listOfFoundElements = commandManager.getContext().getListOfFoundElements();
    }
}