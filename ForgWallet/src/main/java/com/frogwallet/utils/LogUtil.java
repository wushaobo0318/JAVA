package com.frogwallet.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

    public static void trace(Class fileClass, String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(fileClass);
        logger.trace(message, args);
    }

    public static void debug(Class fileClass, String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(fileClass);
        logger.debug(message, args);
    }

    public static void info(Class fileClass, String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(fileClass);
        logger.info(message, args);
    }

    public static void error(Class fileClass, String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(fileClass);
        logger.error(message, args);
    }
}
