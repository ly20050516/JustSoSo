package com.ly.justsoso.sample.ui.detail;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Binder;
import android.os.Debug;
import android.os.Environment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.ly.justsoso.R;

import java.io.File;
import java.io.FileDescriptor;
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

        mTextView = (TextView) findViewById(R.id.simple_memory_text_view_pss);
        StringBuilder stringBuilder = new StringBuilder();

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
            HashMap<String,String> runtimeStat = (HashMap<String, String>) Debug.getRuntimeStats();
            for(Map.Entry<String,String> entry : runtimeStat.entrySet()) {
                Log.d(TAG, "init runtime stat : key = " + entry.getKey() + ";value = " + entry.getValue());
            }
        }

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
//            Debug.dumpHprofData(file.getAbsolutePath());
            Log.d(TAG, "init: dump hprofdata time = " + (System.currentTimeMillis() - time1));
        } catch (IOException e) {
            e.printStackTrace();
        }



        Debug.dumpService(Context.BATTERY_SERVICE,null,null);
        stringBuilder.append("LoadedClassCount : " + Debug.getLoadedClassCount() + "\n");


        Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
        Debug.getMemoryInfo(memoryInfo);

        sizeStr = Formatter.formatFileSize(getContext(),memoryInfo.getTotalPss() * 1024);
        stringBuilder.append("memoryInfo.getTotalPss : " + sizeStr + "\n");

        ActivityManager.MemoryInfo memoryInfo1 = new ActivityManager.MemoryInfo();
        ActivityManager am = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        am.getMemoryInfo(memoryInfo1);

        sizeStr = Formatter.formatFileSize(getContext(),memoryInfo1.totalMem);
        stringBuilder.append("memoryInfo.totalMem : " + sizeStr + "\n");

        mTextView.setText(stringBuilder.toString());

        new Thread(new Runnable() {
            @Override
            public void run() {
                dumpService(Context.BATTERY_SERVICE);
            }
        }).start();
    }

    private void dumpService(String name) {
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

}
