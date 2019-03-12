package com.sydml.framework.ioc.handler;

import com.sydml.common.utils.PropsUtil;
import com.sydml.framework.ioc.config.ConfigConstant;

import java.util.Properties;

/**
 * @author Liuym
 * @date 2019/3/10 0010
 */
public class ConfigHandler {
    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    public static String getJdbcDriver() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
    }

    public static String getJdbcUrl() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }

    public static String getJdbcUsername() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }

     public static String getJdbcPassword() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }

     public static String getJspPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JSP_PATH);
    }

     public static String getAssetPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.ASSET_PATH);
    }

    public static String getBasePackages() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.BASE_PACKAGES);
    }

}
