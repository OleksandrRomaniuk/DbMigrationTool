package dbmigrationtool.filemanagers.parsers;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.filemanagers.parsers.XmlSingleNodeParser;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.CharacterData;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.HashMap;

import static org.mockito.Mockito.*;

public class XmlSingleNodeParserTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldReturnContainerWithNameAndValue() {
        Mockery cnt = new Mockery();
        final Node node = cnt.mock(Node.class);
        cnt.checking(new Expectations(){{
            oneOf (node).getNodeType(); will(returnValue((short)1));
            oneOf (node).getNodeName(); will(returnValue("name"));
            oneOf (node).getNodeName(); will(returnValue("name"));
            oneOf (node).getNodeValue(); will(returnValue("value"));
            oneOf (node).hasChildNodes(); will(returnValue(false));
            oneOf (node).hasChildNodes(); will(returnValue(false));
        }});

        XmlSingleNodeParser xmlSingleNodeParser = new XmlSingleNodeParser();
        Container<String> actualContainer = xmlSingleNodeParser.parse(new Container<String>(), node);
        Container<String> expectedContainer = new Container();
        expectedContainer.setName("name");
        expectedContainer.setValue("value");
        Assert.assertEquals(actualContainer.getName(), expectedContainer.getName());
        Assert.assertEquals(actualContainer.getValue(), expectedContainer.getValue());
    }

    @Test
    public void shouldReturnContainerWithAttributes() {
        Mockery cnt = new Mockery();
        final Node node = cnt.mock(Node.class);

        cnt.checking(new Expectations(){{
            oneOf (node).getNodeType(); will(returnValue((short)1));
            oneOf (node).getNodeName(); will(returnValue(null));
            oneOf (node).getNodeValue(); will(returnValue(null));
            oneOf (node).hasChildNodes(); will(returnValue(true));
            oneOf (node).getAttributes(); will(returnValue(getAttributesMockery()));
            oneOf (node).hasChildNodes(); will(returnValue(false));
        }});


        XmlSingleNodeParser xmlSingleNodeParser = new XmlSingleNodeParser();
        Container<String> actualContainer = xmlSingleNodeParser.parse(new Container<String>(), node);
        Container<String> expectedContainer = new Container();
        HashMap attr = new HashMap();
        attr.put("key", "value");
        expectedContainer.setAttributes(attr);
        Assert.assertEquals(actualContainer.getAttributes(), expectedContainer.getAttributes());
    }

    private NamedNodeMap getAttributesMockery(){
        Mockery context = new Mockery();
        final NamedNodeMap attributesMap = context.mock(NamedNodeMap.class);
        context.checking(new Expectations(){{
            oneOf (attributesMap).getLength(); will(returnValue(1));
            oneOf (attributesMap).getLength(); will(returnValue(1));
            oneOf (attributesMap).item(0); will(returnValue(getMockeryNode()));
            oneOf (attributesMap).item(0); will(returnValue(getMockeryNode()));
        }});
        return attributesMap;
    }

    private Node getMockeryNode(){
        Mockery context = new Mockery();
        final Node node = context.mock(Node.class);
        context.checking(new Expectations(){{
            oneOf (node).getNodeName(); will(returnValue("key"));
            oneOf (node).getNodeValue(); will(returnValue("value"));
        }});
        return node;
    }


    @Test
    public void shouldInvokeMethodSetCdataNodeAndSetCorrectlyValueToContainer() {

        Mockery cnt = new Mockery();
        final CharacterData node = cnt.mock(CharacterData.class);
        cnt.checking(new Expectations(){{
            oneOf (node).getNodeType(); will(returnValue((short)4));
            oneOf (node).getNodeType(); will(returnValue((short)4));
            oneOf (node).getNodeName(); will(returnValue(null));
            oneOf (node).getData(); will(returnValue("value"));
        }});
        XmlSingleNodeParser xmlSingleNodeParser = new XmlSingleNodeParser();
        Container<String> actualContainer = xmlSingleNodeParser.parse(new Container<String>(), node);
        Assert.assertEquals(actualContainer.getValue(), "value");

    }

    @Test
    public void shouldInvokeMethodsetTextNodeAndSetCorrectlyValueToContainer() {

        Node node = mock(Node.class);
        when(node.getNodeType()).thenReturn((short)3);
        when(node.getTextContent()).thenReturn("value");
        XmlSingleNodeParser xmlSingleNodeParser = new XmlSingleNodeParser();
        Container<String> actualContainer = xmlSingleNodeParser.parse(new Container<String>(), node);
        Assert.assertEquals(actualContainer.getValue(), "value");
    }
}