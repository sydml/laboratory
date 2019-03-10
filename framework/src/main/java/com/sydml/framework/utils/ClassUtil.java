package com.sydml.framework.utils;

import com.sydml.common.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Liuym
 * @date 2019/3/10 0010
 */
public final class ClassUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);
    private static final String REGEX = "%20";
    private static final String BLANK = " ";

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("class.not.found.class.name.is:" + className, e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();

        try {
            String basePackagePath = packageName.replace(".", "/");
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(basePackagePath);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        String packagePath = url.getPath().replaceAll(REGEX, BLANK);
                        addClass(classSet, packagePath, packageName);
                    } else if ("jar".equals(protocol)) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        addJarClass(classSet, jarURLConnection,basePackagePath);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classSet;
    }

    private static void addJarClass(Set<Class<?>> classSet, JarURLConnection jarURLConnection, String basePackagePath) throws IOException {
        if (jarURLConnection != null) {
            JarFile jarFile = jarURLConnection.getJarFile();
            if (jarFile != null) {
                Enumeration<JarEntry> jarEntries = jarFile.entries();
                while (jarEntries.hasMoreElements()) {
                    JarEntry jarEntry = jarEntries.nextElement();
                    String jarEntryName = jarEntry.getName();
                    if (jarEntryName.startsWith(basePackagePath) && jarEntryName.endsWith(".class") && !jarEntryName.contains("$")) {
                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                        doAddClass(classSet,className);
                    }
                }
            }
        }
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(FileFilterUtil.getClassFileFilter());

        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.indexOf("."));
                if (StringUtil.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
                String subPackagePath = fileName;
                if (StringUtil.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtil.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }

    public static void main(String[] args) {
        getJar("com.sydml.framework.test.lib");

    }

    public static void getJar(String path) {
        String basePackagePath = path.replace(".", "/");

        URL url = Thread.currentThread().getContextClassLoader().getResource(basePackagePath);
        String protocol = url.getProtocol();
        if (protocol.equals("file")) {
            System.out.println("FILE");
        } else if (protocol.equals("jar")) {
            System.out.println("JAR");
        }
    }

}
