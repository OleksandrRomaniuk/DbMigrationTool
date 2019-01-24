package com.dbbest.databasemanager.reflectionutil;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomClassLoader extends ClassLoader {
    private static final Logger logger = Logger.getLogger("Database logger");

    public Class<?> createClass(File classFile) throws ClassNotFoundException {
        if (!classFile.isFile()) {
            throw new ClassNotFoundException("Class not found " + classFile.getPath());
        }
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(classFile));) {
            byte[] byteRepresentationOfClass = new byte[(int) classFile.length()];
            inputStream.read(byteRepresentationOfClass);
            Class loadedClass = defineClass(null, byteRepresentationOfClass, 0, byteRepresentationOfClass.length);
            return loadedClass;
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Can not find the file: " + classFile.getPath() + " " + e.getMessage());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "A problem with reading the file: " + e.getMessage());
        }
        return null;
    }
}
