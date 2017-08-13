package com.squareup.haha.perflib.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by ly on 2017/8/13.
 */

public class FileLog {

    private static boolean DEBUG = true;
    private FileWriter mFileWriter;
    private BufferedWriter mBufferWriter;

    private static int mSequence = 0;

    private static FileLog mFileLog;

    private FileLog() {

        File dir = new File("/sdcard/JustSoSoDebug/");
        if(!dir.exists()) {
            dir.mkdirs();
        }

        File logFile = new File(dir,"hprof.log");

        try {
            mFileWriter = new FileWriter(logFile);
            mBufferWriter = new BufferedWriter(mFileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static FileLog getFileLog() {
        if(mFileLog == null) {
            mFileLog = new FileLog();
        }

        return mFileLog;
    }

    public static void d(String s) {
        if(!DEBUG) {
            return;
        }
        try {
            getFileLog().mBufferWriter.write(mSequence + " " + s + "\n");
            mSequence++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void d(String desc,String s) {
        if(!DEBUG) {
            return;
        }
        try {
            getFileLog().mBufferWriter.write(mSequence + " " + desc + ":" + s + "\n");
            mSequence++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void d(String desc,int i) {
        if(!DEBUG) {
            return;
        }
        try {
            getFileLog().mBufferWriter.write(mSequence + " " + desc + ":" + i + "\n");
            mSequence++;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void d(String desc,long i) {
        if(!DEBUG) {
            return;
        }
        try {
            getFileLog().mBufferWriter.write(mSequence + " " + desc + ":" + i + "\n");
            mSequence++;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
