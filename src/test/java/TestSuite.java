import com.dbbest.dbmigrationtool.containers.*;
import com.dbbest.dbmigrationtool.containers.ContainerTest;
import com.dbbest.dbmigrationtool.containers.HorizontalPassageSearchManagerTest;
import com.dbbest.dbmigrationtool.containers.ListOfChildrenTest;
import com.dbbest.dbmigrationtool.containers.VerticalPassageSearchManagerTest;
import com.dbbest.dbmigrationtool.filemanagers.ParsingManagerTest;
import com.dbbest.dbmigrationtool.filemanagers.SerializingManagerTest;
import com.dbbest.dbmigrationtool.filemanagers.parsers.XmlParserTest;
import com.dbbest.dbmigrationtool.filemanagers.parsers.XmlSingleNodeParserTest;
import com.dbbest.dbmigrationtool.filemanagers.parsers.validator.XmlValidationFactoryTest;
import com.dbbest.dbmigrationtool.filemanagers.parsers.validator.XmlValidator;
import com.dbbest.dbmigrationtool.filemanagers.parsers.validator.XmlValidatorTest;
import com.dbbest.dbmigrationtool.filemanagers.serializers.XmlNodeBuilderTest;
import com.dbbest.dbmigrationtool.filemanagers.serializers.XmlSerializerTest;
import logger.CustomLogerFormaterTest;
import logger.CustomLoggerFilterTest;
import logger.CustomLoggerTest;
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
    CustomLogerFormaterTest.class, CustomLoggerFilterTest.class, CustomLoggerTest.class})
public class TestSuite {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
}
