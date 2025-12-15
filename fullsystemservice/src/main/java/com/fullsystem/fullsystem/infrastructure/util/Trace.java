package com.fullsystem.fullsystem.infrastructure.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for logging and tracing operations.
 * Provides convenient static methods for logging at different levels.
 */
public class Trace {

    private static final Logger logger = LoggerFactory.getLogger(Trace.class);

    private Trace() {
        // Private constructor to prevent instantiation
    }

    /**
     * Logs an info message.
     * 
     * @param message the message to log
     * @param args    optional arguments for message formatting
     */
    public static void info(String message, Object... args) {
        logger.info(message, args);
    }

    /**
     * Logs a debug message.
     * 
     * @param message the message to log
     * @param args    optional arguments for message formatting
     */
    public static void debug(String message, Object... args) {
        logger.debug(message, args);
    }

    /**
     * Logs a warning message.
     * 
     * @param message the message to log
     * @param args    optional arguments for message formatting
     */
    public static void warn(String message, Object... args) {
        logger.warn(message, args);
    }

    /**
     * Logs an error message.
     * 
     * @param message the message to log
     * @param args    optional arguments for message formatting
     */
    public static void error(String message, Object... args) {
        logger.error(message, args);
    }

    /**
     * Logs an error message with exception.
     * 
     * @param message   the message to log
     * @param throwable the exception
     */
    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}
