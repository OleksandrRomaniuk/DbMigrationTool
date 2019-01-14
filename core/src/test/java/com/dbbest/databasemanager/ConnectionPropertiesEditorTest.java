package com.dbbest.databasemanager;

import com.dbbest.databasemanager.connectionbuilder.PropertyFileManager.ConnectionPropertiesEditor;
import com.dbbest.databasemanager.connectionbuilder.PropertyFileManager.ConnectionPropertiesManager;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.ParsingException;
import com.dbbest.exceptions.SerializingException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class ConnectionPropertiesEditorTest {

    @Test
    public void remove() {
    }

    @Test
    public void shouldAddNewConnectionPropertiesToFileAndThenRemoveThem() throws DatabaseException, ContainerException, ParsingException, SerializingException {
        String conName = "ConnectionPropertiesEditorTestConnectionName";
        String conUrl = "ConnectionPropertiesEditorTestUrl";
        String conDriver = "ConnectionPropertiesEditorTestDriver";
        String conLogin = "ConnectionPropertiesEditorTestLogin";
        String conPassword = "ConnectionPropertiesEditorTestPassword";
        String fileName = "src\\test\\resources\\ConnectionPropertiesConManTest.xml";

        ConnectionPropertiesEditor connectionPropertiesEditor = new ConnectionPropertiesEditor();
        connectionPropertiesEditor.add(fileName,
            conName,
            conUrl,
            conDriver,
            conLogin,
            conPassword);

        ConnectionPropertiesManager connectionPropertiesManager = new ConnectionPropertiesManager();
        Map<String, String> connectionPropertiesMap = connectionPropertiesManager
            .getConnectionUrlDriverUserAndPass(fileName, conName);
        Assert.assertEquals(conUrl,
            connectionPropertiesMap.get("url"));
        Assert.assertEquals(conDriver,
            connectionPropertiesMap.get("driver"));
        Assert.assertEquals(conPassword,
            connectionPropertiesMap.get("password"));
        Assert.assertEquals(conLogin,
            connectionPropertiesMap.get("username"));

        Assert.assertEquals(true, connectionPropertiesEditor.remove(fileName, conName));
        Assert.assertEquals(false, connectionPropertiesEditor.remove(fileName, conName));
    }
}