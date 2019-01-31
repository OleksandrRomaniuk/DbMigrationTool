package com.dbbest.xmlmanager.filemanagers.parsers;

import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.ParsingException;
import com.dbbest.xmlmanager.container.Container;

/**
 * A parser which manages the whole process of validation, parsing and building a tree from a file.
 */
public interface Parser {
    Container parse(String targetFileUrl) throws ParsingException, ContainerException;

}

