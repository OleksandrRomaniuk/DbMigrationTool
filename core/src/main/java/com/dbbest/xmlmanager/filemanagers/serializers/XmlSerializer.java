package com.dbbest.xmlmanager.filemanagers.serializers;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.dbbest.xmlmanager.container.Container;
import com.dbbest.xmlmanager.exceptions.SerializingException;
import org.w3c.dom.Document;

/**
 * Realisable serializer implementation of the interface serializer
 * The class manages the whole process of serialisation from reading a tree from a container to writing it to an xml file.
 */
public class XmlSerializer implements Serializer<String> {
    private Container<String> container;

    @Override
    public void setContainer(Container<String> container) {

        this.container = container;
    }

    @Override
    public void writeFile(String targetFileUrl) throws SerializingException {
        try {
            if (container != null) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.newDocument();
                document = setTreeToDocument(document, container);
                writeNewFile(document, targetFileUrl);
            } else {
                throw new SerializingException("The container has not been set");
            }
        } catch (ParserConfigurationException | TransformerException exception) {
            throw new SerializingException(exception);
        }
    }

    private Document setTreeToDocument(Document document, Container<String> container) {
        return new XmlNodeBuilder(document, container).build().getDocument();
    }

    private void writeNewFile(Document document, String targetFileUrl) throws TransformerConfigurationException,
        TransformerException {
        DOMSource domSource = new DOMSource(document);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        File file = new File(targetFileUrl);
        StreamResult result = new StreamResult(file);
        transformer.transform(domSource, result);
    }
}

