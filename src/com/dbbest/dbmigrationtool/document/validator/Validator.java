package com.dbbest.dbmigrationtool.document.validator;

import com.dbbest.dbmigrationtool.exceptions.ParsingException;
import org.w3c.dom.Document;

public interface Validator {
    Document validate(String targetFileUrl) throws ParsingException;
}