package com.dbbest.dbmigrationtool.filemanagers.parsers;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.document.validator.Validator;
import com.dbbest.dbmigrationtool.exceptions.ParsingException;
import org.w3c.dom.Document;

public interface Parser {
    Container parse(Document document) throws ParsingException;

    Container getContainer();

    Validator getFileValidator();
}
