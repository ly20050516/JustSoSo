package com.ly.justsoso.sample.ui.detail;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.ly.justsoso.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ly on 2017/7/19.
 */

public class SimpleMemoryView extends AbstractDetailView {

    public static final String TAG = "SimpleMemoryView";
    private TextView mTextView;
    public SimpleMemoryView(Context context) {
        super(context);
    }

    @Override
    protected void inflat(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sample_simple_memory_view,this,true);

    }

    @Override
    protected void init(Context context) {

        Debug.startMethodTracing("JustSoSo");
        mTextView = (TextView) findViewById(R.id.simple_memory_text_view_pss);
        StringBuilder stringBuilder = new StringBuilder();

        /**
         * Debug direct api
         */
        stringBuilder.append("Debug direct api" + "\n");
        String sizeStr = Formatter.formatFileSize(getContext(), Debug.getPss() * 1024);
        stringBuilder.append("Pss : " + sizeStr + "\n");

        sizeStr = Formatter.formatFileSize(getContext(),Debug.getNativeHeapAllocatedSize());
        stringBuilder.append("NativeHeapAllocatedSize : " + sizeStr + "\n");

        sizeStr = Formatter.formatFileSize(getContext(),Debug.getNativeHeapFreeSize());
        stringBuilder.append("NativeHeapFreeSize : " + sizeStr + "\n");

        sizeStr = Formatter.formatFileSize(getContext(),Debug.getNativeHeapSize());
        stringBuilder.append("NativeHeapSize : " + sizeStr + "\n");

        sizeStr = Formatter.formatFileSize(getContext(),Debug.getGlobalAllocSize());
        stringBuilder.append("GlobalAllocSize : " + sizeStr + "\n");

        sizeStr = Formatter.formatFileSize(getContext(),Debug.getGlobalFreedSize());
        stringBuilder.append("GlobalFreedSize : " + sizeStr + "\n");

        sizeStr = Formatter.formatFileSize(getContext(),Debug.getThreadAllocSize());
        stringBuilder.append("ThreadAllocSize : " + sizeStr + "\n");

        sizeStr = Formatter.formatFileSize(getContext(),Debug.getGlobalExternalAllocSize());
        stringBuilder.append("GlobalExternalAllocSize : " + sizeStr + "\n");

        sizeStr = Formatter.formatFileSize(getContext(),Debug.getGlobalExternalFreedSize());
        stringBuilder.append("GlobalExternalFreedSize : " + sizeStr + "\n");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            stringBuilder.append("\nDebug run time stats\n");
            HashMap<String,String> runtimeStat = (HashMap<String, String>) Debug.getRuntimeStats();
            for(Map.Entry<String,String> entry : runtimeStat.entrySet()) {
                Log.d(TAG, "init runtime stat : key = " + entry.getKey() + ";value = " + entry.getValue());
                stringBuilder.append(entry.getKey() + " : " + entry.getValue() + "\n");
            }
        }


        Debug.MemoryInfo debugMemoryInfo = new Debug.MemoryInfo();
        Debug.getMemoryInfo(debugMemoryInfo);

        /**
         * total
         */
        stringBuilder.append("\nDebug Memory info\n");
        sizeStr = Formatter.formatFileSize(getContext(), debugMemoryInfo.getTotalPss() * 1024);
        stringBuilder.append("debugMemoryInfo.getTotalPss() = " + sizeStr + "\n");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            sizeStr = Formatter.formatFileSize(getContext(), debugMemoryInfo.getTotalPrivateClean() * 1024);
            stringBuilder.append("debugMemoryInfo.getTotalPrivateClean = " + sizeStr + "\n");

            sizeStr = Formatter.formatFileSize(getContext(), debugMemoryInfo.getTotalPrivateDirty() * 1024);
            stringBuilder.append("debugMemoryInfo.getTotalPrivateDirty = " + sizeStr + "\n");

            sizeStr = Formatter.formatFileSize(getContext(), debugMemoryInfo.getTotalSharedClean() * 1024);
            stringBuilder.append("debugMemoryInfo.getTotalSharedClean = " + sizeStr + "\n");

            sizeStr = Formatter.formatFileSize(getContext(), debugMemoryInfo.getTotalSharedDirty() * 1024);
            stringBuilder.append("debugMemoryInfo.getTotalSharedDirty = " + sizeStr + "\n");

            sizeStr = Formatter.formatFileSize(getContext(), debugMemoryInfo.getTotalSwappablePss() * 1024);
            stringBuilder.append("debugMemoryInfo.getTotalSwappablePss = " + sizeStr + "\n");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                stringBuilder.append("\nDebug memory stats stats\n");
                HashMap<String, String> runtimeStat = (HashMap<String, String>) debugMemoryInfo.getMemoryStats();
                for (Map.Entry<String, String> entry : runtimeStat.entrySet()) {
                    Log.d(TAG, "init memory stat : key = " + entry.getKey() + ";value = " + entry.getValue());
                    stringBuilder.append(entry.getKey() + " : " + entry.getValue() + "\n");
                }
            }
        }

        /**
         * dalvik
         */
        sizeStr = Formatter.formatFileSize(getContext(), debugMemoryInfo.dalvikPss * 1024);
        stringBuilder.append("debugMemoryInfo.dalvikPss = " + sizeStr + "\n");

        sizeStr = Formatter.formatFileSize(getContext(), debugMemoryInfo.dalvikPrivateDirty * 1024);
        stringBuilder.append("debugMemoryInfo.dalvikPrivateDirty = " + sizeStr + "\n");

        sizeStr = Formatter.formatFileSize(getContext(), debugMemoryInfo.dalvikSharedDirty * 1024);
        stringBuilder.append("debugMemoryInfo.dalvikSharedDirty = " + sizeStr + "\n");

        /**
         * native
         */
        sizeStr = Formatter.formatFileSize(getContext(), debugMemoryInfo.nativePss * 1024);
        stringBuilder.append("debugMemoryInfo.nativePss = " + sizeStr + "\n");

        sizeStr = Formatter.formatFileSize(getContext(), debugMemoryInfo.nativePrivateDirty * 1024);
        stringBuilder.append("debugMemoryInfo.nativePrivateDirty = " + sizeStr + "\n");

        sizeStr = Formatter.formatFileSize(getContext(), debugMemoryInfo.nativeSharedDirty * 1024);
        stringBuilder.append("debugMemoryInfo.nativeSharedDirty = " + sizeStr + "\n");

        /**
         * other
         */
        sizeStr = Formatter.formatFileSize(getContext(), debugMemoryInfo.otherPss * 1024);
        stringBuilder.append("debugMemoryInfo.otherPss = " + sizeStr + "\n");

        sizeStr = Formatter.formatFileSize(getContext(), debugMemoryInfo.otherPrivateDirty * 1024);
        stringBuilder.append("debugMemoryInfo.otherPrivateDirty = " + sizeStr + "\n");

        sizeStr = Formatter.formatFileSize(getContext(), debugMemoryInfo.otherSharedDirty * 1024);
        stringBuilder.append("debugMemoryInfo.otherSharedDirty = " + sizeStr + "\n");


        /**
         * Object counts
         */
        stringBuilder.append("\nObject Counts\n");
        stringBuilder.append("LoadedClassCount : " + Debug.getLoadedClassCount() + "\n");


        /**
         * AMS memory info
         */
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager am = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        am.getMemoryInfo(memoryInfo);

        stringBuilder.append("\nAMS memory info\n");

        sizeStr = Formatter.formatFileSize(getContext(),memoryInfo.totalMem);
        stringBuilder.append("memoryInfo.totalMem : " + sizeStr + "\n");
        sizeStr = Formatter.formatFileSize(getContext(),memoryInfo.availMem);
        stringBuilder.append("memoryInfo.availMem : " + sizeStr + "\n");
        sizeStr = Formatter.formatFileSize(getContext(),memoryInfo.threshold);
        stringBuilder.append("memoryInfo.threshold : " + sizeStr + "\n");
        stringBuilder.append("memoryInfo.lowMemory : " + memoryInfo.lowMemory + "\n");

        mTextView.setText(stringBuilder.toString());


        Debug.stopMethodTracing();


    }

    private void dumpHProfData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "hprof");
                    if(!dir.exists()) {
                        dir.mkdir();
                    }
                    File file = new File(dir,"dump.hprof");
                    if(file.exists()){
                        file.delete();
                    }
                    file.createNewFile();
                    long time1 = System.currentTimeMillis();
                    Debug.dumpHprofData(file.getAbsolutePath());
                    Log.d(TAG, "init: dump hprofdata time = " + (System.currentTimeMillis() - time1));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
    private void dumpService(final String name) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "service");
                    if(!dir.exists()) {
                        dir.mkdir();
                    }
                    File file = new File(dir,name);
                    if(file.exists()){
                        file.delete();
                    }
                    file.createNewFile();
                    FileOutputStream fos = new FileOutputStream(file);
                    long time1 = System.currentTimeMillis();
                    Debug.dumpService(name,fos.getFD(),null);
                    fos.getFD().sync();
                    fos.close();
                    Log.d(TAG, "dumpService: name = " + name + ";cost = " + (System.currentTimeMillis() - time1));
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
