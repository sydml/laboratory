package com.sydml.framework.ioc.utils;

import java.io.File;
import java.io.FileFilter;

/**
 * @author Liuym
 * @date 2019/3/10 0010
 */
public final class FileFilterUtil {

    public static FileFilter getClassFileFilter(){
        return new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        };
    }

}
