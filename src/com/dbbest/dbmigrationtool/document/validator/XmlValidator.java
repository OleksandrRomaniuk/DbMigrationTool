package com.dbbest.dbmigrationtool.document.validator;

import com.dbbest.dbmigrationtool.exceptions.ParsingException;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;

/**
 * Implements the interface validator. Realises validation of an xml file and returns a document got from the parsing of the file.
 */
public class XmlValidator implements Validator {

    private static final Logger logger = Logger.getLogger("Parsing logger");

    @Override
    public Document validate(String targetFileUrl) throws ParsingException {
        try {
            if (targetFileUrl == null) {
                String message = "The file url has not been set in the xml validator. "
                    + "Please set the file url before evoking the method";
                logger.log(Level.SEVERE, message);
                throw new ParsingException(message);
            } else if (!Files.exists(Paths.get(targetFileUrl))) {
                throw new NoSuchFileException("Can not find the XML file to parse. The file does not exist");
            } else if (!checkExtension(targetFileUrl)) {
                String message = "The extension of the file is not supported";
                logger.log(Level.SEVERE, message);
                throw new ParsingException(message);
            } else {
                return validateXml(new File(targetFileUrl));
            }
        } catch (NoSuchFileException exception) {
            logger.log(Level.SEVERE, exception.getMessage(), exception);
            throw new ParsingException(exception);
        } catch (FileNotFoundException exception) {
            logger.log(Level.SEVERE, exception.getMessage(), exception);
            throw new ParsingException(exception);
        }
    }

    private Document validateXml(File file) throws ParsingException {
        return new XmlValidationFactory().validateAndGetDocument(file);
    }

    private boolean checkExtension(String url) throws FileNotFoundException {
        String path = "Extensions.json";
        BufferedReader bufferedReader = null;
        bufferedReader = new BufferedReader(new FileReader(path));
        Gson gson = new Gson();
        String[] extensions = gson.fromJson(bufferedReader, String[].class);

        for (String extension : extensions) {
            return url.toLowerCase().endsWith(extension);
        }
        return false;
    }
}

