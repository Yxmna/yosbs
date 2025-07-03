package io.github.yxmna.yosbs.util;

/**
 * Adds a fixed tag (e.g. "[YOSBS] ") in front of every Log4j 2 message.
 */
public final class Logger {

    private final org.apache.logging.log4j.Logger delegate;
    private final String tag;

    public Logger(org.apache.logging.log4j.Logger delegate, String tag) {
        this.delegate = delegate;
        this.tag      = tag;
    }

    public void info (String msg, Object... args) { delegate.info (tag + msg, args); }
    public void warn (String msg, Object... args) { delegate.warn (tag + msg, args); }
    public void error(String msg, Object... args) { delegate.error(tag + msg, args); }
    public void debug(String msg, Object... args) { delegate.debug(tag + msg, args); }
}
