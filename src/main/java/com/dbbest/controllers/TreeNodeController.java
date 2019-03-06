package com.dbbest.controllers;

import com.dbbest.models.TreeNode;
import com.dbbest.services.TestTreeBuilder;
import com.dbbest.services.TreeNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
public class TreeNodeController {

    @Autowired
    private TestTreeBuilder builder;

    @Autowired
    private TreeNodeService service;


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/testTree")
    private TreeNode getTestTree(HttpServletRequest request, HttpServletResponse response) {
        return builder.build();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/check")
    private TreeNode checkTree(@RequestBody TreeNode root, HttpServletRequest request, HttpServletResponse response) {
        return service.checkTree(root);
    }


}
