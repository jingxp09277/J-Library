package com.jinxp09277.j.library.log;

import androidx.annotation.NonNull;

/**
 * Created by LWB on 2020/10/27
 */
public class JLogManager {
    private JLogConfig config;
    private static JLogManager instance;

    private JLogManager(JLogConfig config) {
        this.config = config;
    }

    public static JLogManager getInstance() {
        return instance;
    }

    public static void init(@NonNull JLogConfig config) {
        instance = new JLogManager(config);
    }

    public JLogConfig getConfig() {
        return config;
    }
}
