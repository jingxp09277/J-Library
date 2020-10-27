package com.jinxp09277.j.library.log;

/**
 * Created by LWB on 2020/10/27
 */
public interface JLogFormatter<T> {
    String format(T data);
}
