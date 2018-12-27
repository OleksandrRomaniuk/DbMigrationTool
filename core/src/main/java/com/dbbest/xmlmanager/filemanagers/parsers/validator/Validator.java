package com.dbbest.xmlmanager.filemanagers.parsers.validator;

import com.dbbest.xmlmanager.exceptions.ParsingException;
import org.w3c.dom.Document;

/**
 *A simple validator which carries out a check that a document is valid and can be parsed.
 * The only method of the interface returns a document got from parsing of a file.
 */
public interface Validator {
    Document validate(String targetFileUrl) throws ParsingException;
}

