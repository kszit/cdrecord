package com.kszit.CDReport.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 配置文件服务类
 *
 * @author Administrator
 */
public class ApplicationConfigReader {

    private static final String BUNDLE_NAME = "application";
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
        }
        return '!' + key + '!';
    }
}
