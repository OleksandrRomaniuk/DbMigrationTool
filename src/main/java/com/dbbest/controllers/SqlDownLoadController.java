package com.dbbest.controllers;

import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.models.LoadQueryWrapper;
import com.dbbest.services.PrintService;
import com.dbbest.xmlmanager.container.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class SqlDownLoadController {
    private static final Logger logger = Logger.getLogger("UI logger");

    @Autowired
    PrintService printService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/download")
    private ResponseEntity<byte[]> checkTree(@RequestBody LoadQueryWrapper loadQueryWrapper, HttpServletRequest request, HttpServletResponse response, ModelMap model, HttpSession httpSession) {

        String sqlQuery = "";
        try {
            sqlQuery = printService.print((Container) loadQueryWrapper.getContainer().getChildren().get(0),
                    loadQueryWrapper.getDbType(), loadQueryWrapper.getFullPath());
        } catch (DatabaseException | ContainerException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        System.out.println(sqlQuery);
        System.out.println("test");

        byte[] output = sqlQuery.getBytes();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("charset", "utf-8");
        responseHeaders.setContentType(MediaType.valueOf("application/txt"));
        responseHeaders.setContentLength(output.length);
        responseHeaders.set("Content-disposition", "attachment: filename=filename.txt");
        responseHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        responseHeaders.set("Access-Control-Allow-Credentials", "true");


        return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);

        //InputStream in = getClass()
          //      .getResourceAsStream("/com/baeldung/produceimage/data.txt");
        //return output;
    }

}
