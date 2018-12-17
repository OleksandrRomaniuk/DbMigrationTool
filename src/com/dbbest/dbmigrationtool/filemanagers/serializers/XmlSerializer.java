package com.dbbest.dbmigrationtool.filemanagers.serializers;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.exceptions.SerializingException;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XmlSerializer implements Serializer<String, String> {
    private static final Logger logger = Logger.getLogger("Serilising logger");
    private Container<String, String> container;

    @Override
    public void setContainer(Container<String, String> container) {

        this.container = container;
    }

    @Override
    public void writeFile(String targetFileUrl) throws SerializingException {
        try {
            if (container != null) {
                System.out.println("--------------------------");
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.newDocument();
                document = setTreeToDocument(document, container);
                System.out.println(document.getDocumentElement().getNodeName());
                writeNewFile(document, targetFileUrl);
            }
        } catch (ParserConfigurationException exception) {
            logger.log(Level.SEVERE, exception.getMessage(), exception);
            throw new SerializingException(exception);
        } catch (TransformerConfigurationException exception) {
            logger.log(Level.SEVERE, exception.getMessage(), exception);
            throw new SerializingException(exception);
        } catch (TransformerException exception) {
            logger.log(Level.SEVERE, exception.getMessage(), exception);
            throw new SerializingException(exception);
        }
    }

    private Document setTreeToDocument(Document document, Container<String, String> container) {
        return new XmlNodeBuilder(document, container).build().getDocument();
    }

    private void writeNewFile(Document document, String targetFileUrl) throws TransformerConfigurationException, TransformerException {
        DOMSource domSource = new DOMSource(document);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        File file = new File(targetFileUrl);
        StreamResult result = new StreamResult(file);
        transformer.transform(domSource, result);
    }

}
