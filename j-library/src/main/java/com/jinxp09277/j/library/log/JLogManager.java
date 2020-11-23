package com.jinxp09277.j.library.log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LWB on 2020/10/27
 */
public class JLogManager {
    private JLogConfig config;
    private static JLogManager instance;
    private List<JLogPrinter> printers = new ArrayList<>();

    private JLogManager(JLogConfig config, JLogPrinter[] printers) {
        this.config = config;
        this.printers.addAll(Arrays.asList(printers));
    }

    public static JLogManager getInstance() {
        return instance;
    }

    public static void init(@NonNull JLogConfig config, JLogPrinter... printers) {
        instance = new JLogManager(config, printers);
    }

    public JLogConfig getConfig() {
        return config;
    }

    public List<JLogPrinter> getPrinters() {
        return printers;
    }

    public void addPrinter(JLogPrinter printer) {
        printers.add(printer);
    }

    public void removePrinter(JLogPrinter printer) {
        if (printers != null) {
            printers.remove(printer);
        }
    }


}
