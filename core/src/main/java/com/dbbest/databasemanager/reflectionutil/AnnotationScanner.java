package com.dbbest.databasemanager.reflectionutil;

import com.dbbest.exceptions.DatabaseException;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class AnnotationScanner {

    public void scanElements() throws DatabaseException {
        try {
            //Get the package name from configuration file
            String packageName = readConfig();

            //Load the classLoader which loads this class.
            ClassLoader classLoader = getClass().getClassLoader();

            //Change the package structure to directory structure
            String packagePath = packageName.replace('.', '/');
            URL urls = classLoader.getResource(packagePath);

            //Get all the class files in the specified URL Path.
            File folder = new File(urls.getPath());
            File[] classes = folder.listFiles();

            int size = classes.length;
            List<Class<?>> classList = new ArrayList<Class<?>>();

            for (int i = 0; i < size; i++) {
                int index = classes[i].getName().indexOf(".");
                String className = classes[i].getName().substring(0, index);
                String classNamePath = packageName + "." + className;
                Class<?> repoClass;
                repoClass = Class.forName(classNamePath);

                Annotation[] annotations = repoClass.getAnnotations();
                for (int j = 0; j < annotations.length; j++) {
                    System.out.println("Annotation in class " + repoClass.getName() + " is " + annotations[j].annotationType().getName());
                }
                classList.add(repoClass);
            }
        } catch (ClassNotFoundException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    /**
     * Unmarshall the configuration file
     * @return
     */
    public String readConfig() {
        try {
            /*
            URL url = getClass().getClassLoader().getResource("WEB-INF/config.xml");
            JAXBContext jContext = JAXBContext.newInstance(RepositoryConfig.class);
            Unmarshaller um = jContext.createUnmarshaller();
            RepositoryConfig rc = (RepositoryConfig) um.unmarshal(new File(url.getFile()));
            return rc.getRepository().getPackageName();
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
