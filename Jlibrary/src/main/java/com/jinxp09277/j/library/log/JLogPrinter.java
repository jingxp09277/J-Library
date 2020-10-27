package com.jinxp09277.j.library.log;

import androidx.annotation.NonNull;

/**
 * Created by LWB on 2020/10/27
 */
public interface JLogPrinter {
    void print(@NonNull JLogConfig config, int level, String tag, @NonNull String printString);

}
