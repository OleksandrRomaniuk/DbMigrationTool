package com.dbbest.xmlmanager;

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
    IntegrationTest.class})
public class TestSuite {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
}
