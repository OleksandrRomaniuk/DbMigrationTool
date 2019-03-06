package com.dbbest.controllers;

import com.dbbest.consolexmlmanager.exceptions.CommandException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.models.TreeNode;
import com.dbbest.services.TestTreeBuilder;
import com.dbbest.services.TreeNodeService;
import com.dbbest.xmlmanager.container.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@RestController
public class TreeNodeController {

    @Autowired
    private TestTreeBuilder builder;

    @Autowired
    private TreeNodeService service;


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/testTree")
    private Container getTestTree(HttpServletRequest request, HttpServletResponse response) throws CommandException, DatabaseException {
        return builder.build();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/check")
    private Container checkTree(@RequestBody Container root, HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();

        return service.checkTree(root);
    }


}
