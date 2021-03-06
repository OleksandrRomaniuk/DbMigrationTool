package com.dbbest.databasemanager.reflectionutil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The custom class loader which loads classes defined by the path.
 */
public class CustomClassLoader extends ClassLoader {
    private static final Logger logger = Logger.getLogger("Database logger");

    /**
     * @param classFile retrieves the file of a class to load.
     * @return returns the loaded class.
     * @throws ClassNotFoundException throws exception if loading of the class has not been successfully completed.
     */
    public Class<?> getClass(File classFile) throws ClassNotFoundException {
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
