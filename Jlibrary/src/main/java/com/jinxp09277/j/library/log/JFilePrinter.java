package com.jinxp09277.j.library.log;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by LWB on 2020/10/27
 */
public class JFilePrinter implements JLogPrinter {
    private Context mContext;
    private Uri uri;
    private String TAG = "JFilePrinter";

    public JFilePrinter(@NonNull Context mContext, Uri uri) {
        this.mContext = mContext;
        this.uri = uri;
    }

    @Override
    public void print(@NonNull JLogConfig config, int level, String tag, @NonNull String printString) {
        Log.d("test", "print: " + printString);
        writeLog(tag + ": " + printString);
    }

    private void writeLog(String data) {
        try {
            OutputStream outputStream = mContext.getContentResolver().openOutputStream(uri,"wa");
            if (outputStream != null) {
                outputStream.write(data.getBytes());
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*private void writeLog(String data)  {
        File filedir = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            filedir = mContext.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        }
            Environment.getExternalStoragePublicDirectory()

        String strFilePath = filedir.getAbsolutePath() + "/JLog.txt";
        Log.d(TAG, "writeLog: "+ filedir + " "+ strFilePath);
        try {
            File file = new File(strFilePath);
            if(!file.exists()){
               Log.d(TAG, "writeLog: Create the file:"+ strFilePath);
                if(file.getParentFile().mkdir()){
                    Log.d(TAG, "writeLog: mkdir success.");
                }else{
                    Log.d(TAG, "writeLog: mkdir failure.");
                }
                if(file.createNewFile()){
                    Log.d(TAG, "writeLog: createNewFile success.");
                }else{
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
    }*/
}
