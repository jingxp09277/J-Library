package com.jinxp09277.j.library.log;

import android.util.Log;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by LWB on 2020/10/27
 */
public class JLogType {
    @IntDef({V,D,I,W,E,A})
    @Retention(RetentionPolicy.SOURCE)//注解保留时期在源码级别
    public @interface TYPE{}

    public final static int V = Log.VERBOSE;
    public final static int D = Log.DEBUG;
    public final static int I = Log.INFO;
    public final static int W = Log.WARN;
    public final static int E = Log.ERROR;
    public final static int A = Log.ASSERT;
}
