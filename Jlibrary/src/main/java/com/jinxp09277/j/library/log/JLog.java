package com.jinxp09277.j.library.log;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

/**
 * Created by LWB on 2020/10/27
 * Tips:
 * 1.打印堆栈信息
 * 2.File输出
 * 3.模拟控制台
 */
public class JLog {
    private static final String J_LOG_PACKAGE;

    static {
        String className = JLog.class.getName();
        J_LOG_PACKAGE = className.substring(0, className.lastIndexOf('.') + 1);
    }

    //不带tag ,global
    public static void v(Object... contents) {
        log(JLogType.V, contents);
    }

    public static void vt(String tag, Object... contents) {
        log(JLogType.V, tag, contents);
    }

    public static void d(Object... contents) {
        log(JLogType.D, contents);
    }

    public static void dt(String tag, Object... contents) {
        log(JLogType.D, tag, contents);
    }

    public static void i(Object... contents) {
        log(JLogType.I, contents);
    }

    public static void it(String tag, Object... contents) {
        log(JLogType.I, tag, contents);
    }

    public static void w(Object... contents) {
        log(JLogType.W, contents);
    }

    public static void wt(String tag, Object... contents) {
        log(JLogType.W, tag, contents);
    }

    public static void e(Object... contents) {
        log(JLogType.E, contents);
    }

    public static void et(String tag, Object... contents) {
        log(JLogType.E, tag, contents);
    }

    public static void a(Object... contents) {
        log(JLogType.A, contents);
    }

    public static void at(String tag, Object... contents) {
        log(JLogType.A, tag, contents);
    }


    public static void log(@JLogType.TYPE int type, Object... contents) {
        log(type, JLogManager.getInstance().getConfig().getGlobalTag(), contents);
    }

    public static void log(@JLogType.TYPE int type, @NonNull String tag, Object... contents) {
        log(JLogManager.getInstance().getConfig(), type, tag, contents);
    }

    public static void log(@NonNull JLogConfig config, @JLogType.TYPE int type, @NonNull String tag, Object... contents) {
        if (!config.enable()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (config.includeTread()) {
            String threadInfo = JLogConfig.J_THREAD_FORMATTER.format(Thread.currentThread());
            sb.append(threadInfo).append("\n");
        }
        if (config.stackTraceDepth() > 0) {
            String stackTrace = JLogConfig.J_STACK_TRACE_FORMATTER.format(JStackTraceUtil.getCroppedRealStackTrack(new Throwable().getStackTrace(), J_LOG_PACKAGE, config.stackTraceDepth()));
            sb.append(stackTrace).append("\n");
        }
        String body = parseBody(contents, config);
        sb.append(body);
        List<JLogPrinter> printers = config.printers() != null ? Arrays.asList(config.printers()) : JLogManager.getInstance().getPrinters();
        if (printers == null) {
            return;
        }
        //打印log
        for (JLogPrinter printer : printers) {
            printer.print(config, type, tag, sb.toString());

        }
    }

    private static String parseBody(@NonNull Object[] contents, @NonNull JLogConfig config) {
        if (config.injectJsonParser() != null) {
            return config.injectJsonParser().toJson(contents);
        }
        StringBuilder sb = new StringBuilder();
        for (Object o : contents) {
            sb.append(o.toString()).append(";");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);

        }
        return sb.toString();
    }
}
