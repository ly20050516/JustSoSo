package com.ly.justsoso.sample.ui.detail;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.ly.framework.ui.layout.FlowLayout;

/**
 * Created by ly on 2017/7/19.
 */

public class SampleFlowLayout extends FlowLayout {

    public static final String TAG = "SampleFlowLayout";
    public SampleFlowLayout(Context context) {
        super(context);

        init();
    }

    private void init() {

        String []target = new String[]{

                "http","https","安全","插件化","多线程","视频","音频","加密","解密","图像","颜色","设计","产品","网络"
        };

        FlowLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);

        for(int i = 0; i < target.length;i++) {
            TextView textView = new TextView(getContext());
            textView.setText(target[i]);
            addView(textView,layoutParams);
            Log.d(TAG, "init: target " + target[i]);
        }
    }
}
