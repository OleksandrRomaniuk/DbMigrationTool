package com.dbbest.databasemanager;

import com.dbbest.databasemanager.loadingmanager.LoaderManagerTest;
import com.dbbest.databasemanager.loadingmanager.loaders.mysql.*;
import com.dbbest.databasemanager.loadingmanager.printers.mysql.*;
import com.dbbest.databasemanager.reflectionutil.classloader.DirectorySearcherTest;
import com.dbbest.xmlmanager.IntegrationTest;
import com.dbbest.xmlmanager.container.ContainerTest;
import com.dbbest.xmlmanager.container.HorizontalPassageSearchManagerTest;
import com.dbbest.xmlmanager.container.ListOfChildrenTest;
import com.dbbest.xmlmanager.container.VerticalPassageSearchManagerTest;
import com.dbbest.xmlmanager.filemanagers.ParsingManagerTest;
import com.dbbest.xmlmanager.filemanagers.SerializingManagerTest;
import com.dbbest.xmlmanager.filemanagers.parsers.XmlParserTest;
import com.dbbest.xmlmanager.filemanagers.parsers.XmlSingleNodeParserTest;
import com.dbbest.xmlmanager.filemanagers.parsers.validator.XmlValidationFactoryTest;
import com.dbbest.xmlmanager.filemanagers.parsers.validator.XmlValidatorTest;
import com.dbbest.xmlmanager.filemanagers.serializers.XmlNodeBuilderTest;
import com.dbbest.xmlmanager.filemanagers.serializers.XmlSerializerTest;
import com.dbbest.xmlmanager.logger.CustomLogerFormaterTest;
import com.dbbest.xmlmanager.logger.CustomLoggerFilterTest;
import com.dbbest.xmlmanager.logger.CustomLoggerTest;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({XmlParserTest.class,
    XmlSingleNodeParserTest.class, XmlValidationFactoryTest.class, XmlValidatorTest.class,
    XmlNodeBuilderTest.class, XmlSerializerTest.class, ParsingManagerTest.class,
    SerializingManagerTest.class, ContainerTest.class,
    HorizontalPassageSearchManagerTest.class, VerticalPassageSearchManagerTest.class,
    ListOfChildrenTest.class, XmlParserTest.class, XmlSingleNodeParserTest.class,
    CustomLogerFormaterTest.class, CustomLoggerFilterTest.class, CustomLoggerTest.class,
    IntegrationTest.class, ConnectionPropertiesEditorTest.class, ConnectionPropertiesManagerTest.class,
    SimpleConnectionBuilderTest.class, ConstraintLoaderTest.class, ForeignKeyLoaderTest.class,
    FunctionLoaderTest.class, IndexLoaderTest.class, ProcedureFunctionParameteresLoaderTest.class,
    SchemaLoaderTest.class, StoredProcedureLoaderTest.class, TableColumnLoaderTest.class,
    TableLoaderTest.class, TriggerLoaderTest.class, ViewColumnLoaderTest.class, ViewLoaderTest.class,
    ForeignKeyPrinterHelperTest.class, ForeignKeyPrinterTest.class, FunctionPrinterTest.class, IndexPrinterHelperTest.class,
    IndexPrinterTest.class, PrimaryKeyPrinterHelperTest.class, SchemaPrinterTest.class, StoredProcedurePrinterTest.class,
    TablePrinterTest.class, TriggerPrinterTest.class, UniquePrinterHelperTest.class, ViewPrinterTest.class,
    LoaderManagerTest.class, DirectorySearcherTest.class, ConnectionPropertiesEditorTest.class, ConnectionPropertiesManagerTest.class,
    SimpleConnectionBuilderTest.class})
public class TestSuite {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
}
