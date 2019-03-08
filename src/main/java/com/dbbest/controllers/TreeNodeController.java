package com.dbbest.controllers;

import com.dbbest.consolexmlmanager.exceptions.CommandException;
import com.dbbest.exceptions.ContainerException;
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
import java.util.Enumeration;


@RestController
public class TreeNodeController {

    @Autowired
    private TestTreeBuilder builder;

    @Autowired
    private TreeNodeService service;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/testTree")
    private Container getTestTree(HttpServletRequest request, HttpServletResponse response) throws CommandException, DatabaseException, ContainerException {

        HttpSession session = request.getSession();
        return builder.build();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/check")
    private String checkTree(@RequestBody String root, HttpServletRequest request, HttpServletResponse response) throws CommandException, ContainerException {
        HttpSession session = request.getSession();

        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            System.out.println(attributeName + " = " + session.getAttribute(attributeName));
        }

        String attribute = "rootContainer";
        Container root1 = new Container();
        root1.setName("test");
        session.putValue("one", "two");

        if (root.equals("\"hi\"")) {
            return "{\"phrase\":\"hello yeeeeeeeeees\"}";
        } else {
            return "{\"phrase\":\"hi\"}";
        }

        //return service.checkTree(root);
    }


}
