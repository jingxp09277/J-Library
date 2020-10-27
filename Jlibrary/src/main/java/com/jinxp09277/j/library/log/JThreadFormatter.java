package com.jinxp09277.j.library.log;

/**
 * Created by LWB on 2020/10/27
 */
public class JThreadFormatter implements JLogFormatter<Thread> {
    @Override
    public String format(Thread data) {
        return "Thread:" + data.getName();
    }
}
