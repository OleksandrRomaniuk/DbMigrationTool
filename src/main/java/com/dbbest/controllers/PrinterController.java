package com.dbbest.controllers;

import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.models.LoadQueryWrapper;
import com.dbbest.services.PrintService;
import com.dbbest.xmlmanager.container.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class PrinterController {
    private static final Logger logger = Logger.getLogger("UI logger");

    @Autowired
    PrintService printService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/print")
    private String checkTree(@RequestBody LoadQueryWrapper loadQueryWrapper, HttpServletRequest request, HttpServletResponse response, ModelMap model, HttpSession httpSession) {

        String sqlQuery = "";
        try {
            sqlQuery = printService.print((Container) loadQueryWrapper.getContainer().getChildren().get(0),
                    loadQueryWrapper.getDbType(), loadQueryWrapper.getFullPath());
        } catch (DatabaseException | ContainerException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return sqlQuery;
    }
}
