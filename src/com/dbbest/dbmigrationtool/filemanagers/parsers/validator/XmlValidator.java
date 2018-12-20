package com.dbbest.dbmigrationtool.filemanagers.parsers.validator;

import com.dbbest.dbmigrationtool.exceptions.ParsingException;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import org.w3c.dom.Document;

/**
 * Implements the interface validator. Realises validation of an xml file and returns a document got from the parsing of the file.
 */
public class XmlValidator implements Validator {


    @Override
    public Document validate(String targetFileUrl) throws ParsingException {
        try {
            if (targetFileUrl == null) {
                throw new ParsingException("The file url has not been set in the xml validator. "
                    + "Please set the file url before evoking the method");
            } else if (!Files.exists(Paths.get(targetFileUrl))) {
                throw new NoSuchFileException("Can not find the XML file to parse. The file does not exist");
            } else if (!checkExtension(targetFileUrl)) {
                throw new ParsingException("The extension of the file is not supported");
            } else {
                return validateXml(new File(targetFileUrl));
            }
        } catch (NoSuchFileException | FileNotFoundException exception) {
            throw new ParsingException(exception);
        }
    }

    private Document validateXml(File file) throws ParsingException {
        return new XmlValidationFactory().validateAndGetDocument(file);
    }

    private boolean checkExtension(String url) throws FileNotFoundException {
        String path = "resources/XmlExtensions.json";
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

