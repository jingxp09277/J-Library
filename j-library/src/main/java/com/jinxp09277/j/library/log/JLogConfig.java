package com.jinxp09277.j.library.log;

/**
 * Created by LWB on 2020/10/27
 */
public abstract class JLogConfig {
    static int MAX_LEN = 512;
    static JStackTraceFormatter J_STACK_TRACE_FORMATTER = new JStackTraceFormatter();
    static JThreadFormatter J_THREAD_FORMATTER = new JThreadFormatter();

    public JsonParser injectJsonParser() {
        return null;
    }

    public String getGlobalTag() {
        return "JLog";
    }

    public boolean enable() {
        return true;
    }

    public boolean includeTread() {
        return false;
    }

    public int stackTraceDepth() {
        return 5;
    }

    public JLogPrinter[] printers(){
        return null;
    }

    public interface JsonParser {
        String toJson(Object src);
    }

}
