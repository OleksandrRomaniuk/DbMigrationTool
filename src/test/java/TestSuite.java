import com.dbbest.dbmigrationtool.console.CommandManagerTest;
import dbmigrationtool.containers.ContainerTest;
import dbmigrationtool.containers.HorizontalPassageSearchManagerTest;
import dbmigrationtool.containers.ListOfChildrenTest;
import dbmigrationtool.containers.VerticalPassageSearchManagerTest;
import dbmigrationtool.filemanagers.ParsingManagerTest;
import dbmigrationtool.filemanagers.SerializingManagerTest;
import dbmigrationtool.filemanagers.parsers.XmlParserTest;
import dbmigrationtool.filemanagers.parsers.XmlSingleNodeParserTest;
import dbmigrationtool.filemanagers.parsers.validator.XmlValidationFactoryTest;
import dbmigrationtool.filemanagers.parsers.validator.XmlValidatorTest;
import dbmigrationtool.filemanagers.serializers.XmlNodeBuilderTest;
import dbmigrationtool.filemanagers.serializers.XmlSerializerTest;
import dbmigrationtool.logger.CustomLogerFormaterTest;
import dbmigrationtool.logger.CustomLoggerFilterTest;
import dbmigrationtool.logger.CustomLoggerTest;
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
    CommandManagerTest.class, IntegrationTest.class})
public class TestSuite {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
}
