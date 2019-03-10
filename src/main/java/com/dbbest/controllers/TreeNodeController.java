package com.dbbest.controllers;

import com.dbbest.consolexmlmanager.exceptions.CommandException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.models.LoadQueryWrapper;
import com.dbbest.services.TestTreeBuilder;
import com.dbbest.services.TreeNodeService;
import com.dbbest.xmlmanager.container.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@SessionAttributes({"login", "password", "dbName", "dbType"})
public class TreeNodeController {

    @Autowired
    private TestTreeBuilder builder;

    @Autowired
    private TreeNodeService service;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/tree")
    private Container getTestTree(@RequestBody LoadQueryWrapper loadQueryWrapper,
                                  HttpServletRequest request, HttpServletResponse response) throws CommandException,
            DatabaseException, ContainerException {
        String dbType = loadQueryWrapper.getDbType();
        String sbName = loadQueryWrapper.getSchemaName();
        String login = loadQueryWrapper.getLogin();
        String password = loadQueryWrapper.getPassword();
        return builder.build(dbType, sbName, login, password);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/check")
    private Container checkTree(@RequestBody LoadQueryWrapper loadQueryWrapper,
                                HttpServletRequest request, HttpServletResponse response) throws CommandException, ContainerException, DatabaseException {

        return service.checkTree(loadQueryWrapper.getDbType(), loadQueryWrapper.getSchemaName(),
                loadQueryWrapper.getLogin(), loadQueryWrapper.getPassword(), loadQueryWrapper.getFullPath(),
                loadQueryWrapper.getLoadType(), loadQueryWrapper.getContainer());
    }


}
