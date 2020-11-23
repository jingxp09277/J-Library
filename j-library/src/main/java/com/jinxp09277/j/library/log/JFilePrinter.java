package com.jinxp09277.j.library.log;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by LWB on 2020/10/27
 */
public class JFilePrinter implements JLogPrinter {
    private Context mContext;
    private Uri uri;
    private String TAG = "JFilePrinter";
    private final int MODE_APP_DIR_CREATE_FILE = 1;
    private final int MODE_URI_CREATE_FILE = 2;
    private int printerMode = MODE_APP_DIR_CREATE_FILE;

    public JFilePrinter(@NonNull Context mContext) {
        this(mContext, null);
    }

    public JFilePrinter(@NonNull Context mContext, Uri uri) {
        this.mContext = mContext;
        this.uri = uri;
        if (uri == null) {
            printerMode = MODE_APP_DIR_CREATE_FILE;
        } else {
            printerMode = MODE_URI_CREATE_FILE;
        }
    }

    @Override
    public void print(@NonNull JLogConfig config, int level, String tag, @NonNull String printString) {
        Log.d("test", "print: " + printString);
        SimpleDateFormat sdf = new SimpleDateFormat("\nMM-dd HH:mm:ss ", Locale.CHINA);
        String time = sdf.format(System.currentTimeMillis());
        String printStringWithTime = time + tag + ": " + printString;

        switch (printerMode) {
            case MODE_APP_DIR_CREATE_FILE:
                writeLogWithAppDir(printStringWithTime);
                break;
            case MODE_URI_CREATE_FILE:
                writeLogWithURI(printStringWithTime);
                break;
            default:
                break;
        }
    }

    private void writeLogWithURI(String data) {
        try {
            OutputStream outputStream = mContext.getContentResolver().openOutputStream(uri, "wa");
            if (outputStream != null) {
                outputStream.write(data.getBytes());
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeLogWithAppDir(String data) {
        File filedir = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            filedir = mContext.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        }

        String strFilePath = filedir.getAbsolutePath() + "/JLog.txt";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d(TAG, "writeLog: Create the file:" + strFilePath);
                if (file.getParentFile().mkdir()) {
                    Log.d(TAG, "writeLog: mkdir success.");
                } else {
                    Log.d(TAG, "writeLog: mkdir failure.");
                }
                if (file.createNewFile()) {
                    Log.d(TAG, "writeLog: createNewFile success.");
                } else {
                    Log.d(TAG, "writeLog: createNewFile failure.");
                }
            }

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            FileWriter fileWriter = new FileWriter(fileOutputStream.getFD());
            fileWriter.write(data);
            fileWriter.close();
            fileOutputStream.getFD().sync();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
