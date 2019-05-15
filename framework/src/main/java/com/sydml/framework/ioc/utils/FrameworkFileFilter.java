package com.sydml.framework.ioc.utils;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * @author Liuym
 * @date 2019/3/10 0010
 */
public class FrameworkFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        return false;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
