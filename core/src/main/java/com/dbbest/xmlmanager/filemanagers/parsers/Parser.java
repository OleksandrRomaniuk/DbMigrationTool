package com.dbbest.xmlmanager.filemanagers.parsers;

import com.dbbest.xmlmanager.container.Container;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.ParsingException;

/**
 * A parser which manages the whole process of validation, parsing and building a tree from a file.
 */
public interface Parser {
    Container parse(String targetFileUrl) throws ParsingException, ContainerException;

}

