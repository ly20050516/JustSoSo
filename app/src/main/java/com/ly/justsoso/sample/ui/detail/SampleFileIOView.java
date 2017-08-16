package com.ly.justsoso.sample.ui.detail;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.ly.framework.utilities.FileUtil;
import com.ly.justsoso.R;

import org.greenrobot.essentials.io.IoUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by ly on 2017/7/19.
 */

public class SampleFileIOView extends AbstractDetailView {

    public static final String TAG = "SampleFileIOView";
    private TextView mTextView;
    public static final String FILE_NAME = "fileio.txt";
    public SampleFileIOView(Context context) {
        super(context);
    }

    @Override
    protected void inflat(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sample_flow_layout_view,this,true);

    }

    @Override
    protected void init(Context context) {

        mTextView = (TextView) findViewById(R.id.simple_file_io_text_view);

        writeByteByFileOutPutStream();
        readByteByFileInputStream();

        writeByteByBufferedOutputStream();
        readByteByBufferedInputStream();

        writeByteByDataOutputStream();
        readByteByDataInputStream();

    }

    /**
     * 写字节流
     */
    private void writeByteByFileOutPutStream() {
        File writeFile = new File(FileUtil.getDebugDumpDirectory(),FILE_NAME);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(writeFile);
            fileOutputStream.write("I am a 学生呀".getBytes());
            fileOutputStream.write("I am a 假学学生呀".getBytes());
            fileOutputStream.write("I am a 真学学生呀".getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.safeClose(fileOutputStream);
        }

        Log.d(TAG, "writeByteByFileOutPutStream: file length = " + writeFile.length());
    }

    private void readByteByFileInputStream() {
        File inFile = new File(FileUtil.getDebugDumpDirectory(),"writeFile.txt");
        Log.d(TAG, "readByteByFileInputStream: file length " + inFile.length());
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(inFile);
            byte[] buffer = new byte[1024];
            StringBuilder stringBuilder = new StringBuilder(1024);
            while(fileInputStream.read(buffer) != -1) {
                stringBuilder.append(new String(buffer,"GBK"));
            }

            Log.d(TAG, "readByteByFileInputStream: read content = " + stringBuilder.toString());
            mTextView.setText(stringBuilder.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IoUtils.safeClose(fileInputStream);
        }
    }

    private void writeByteByBufferedOutputStream() {
        File writeFile = new File(FileUtil.getDebugDumpDirectory(),FILE_NAME);
        BufferedOutputStream bufferOutputStream = null;

        try {

            bufferOutputStream = new BufferedOutputStream(new FileOutputStream(writeFile));
            bufferOutputStream.write("Java Io 带缓冲的输出流练习".getBytes());
            bufferOutputStream.write("Java Io 带缓冲的输出流练习，很基础".getBytes());
            bufferOutputStream.write("Java Io 带缓冲的输出流练习，真的很基础".getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.safeClose(bufferOutputStream);
        }

        Log.d(TAG, "writeByteByBufferedOutputStream: file length = " + writeFile.length());
    }

    private void readByteByBufferedInputStream() {
        File readFile = new File(FileUtil.getDebugDumpDirectory(),FILE_NAME);
        BufferedInputStream bufferedInputStream = null;

        try {

            bufferedInputStream = new BufferedInputStream(new FileInputStream(readFile));
            byte[] buffer = new byte[1024];
            StringBuilder stringBuilder = new StringBuilder(1024);
            while((bufferedInputStream.read(buffer)) != -1) {
                stringBuilder.append(new String(buffer));
            }
            Log.d(TAG, "readByteByBufferedInputStream: " + stringBuilder.toString());
            mTextView.setText(stringBuilder.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.safeClose(bufferedInputStream);
        }

    }

    private void writeByteByDataOutputStream() {
        File writeFile = new File(FileUtil.getDebugDumpDirectory(),FILE_NAME);
        DataOutputStream dataOutputStream = null;

        try {
            dataOutputStream = new DataOutputStream(new FileOutputStream(writeFile));
            dataOutputStream.write(1024);
            dataOutputStream.writeBoolean(true);
            dataOutputStream.writeBytes("这是在写入 One string bytes");
            dataOutputStream.writeChar(98);
            dataOutputStream.writeChars("写入字符串，ok ？");
            double val = 1.2909888080807324998;
            dataOutputStream.writeDouble(val);
            float fval = 1.2998948394f;
            dataOutputStream.writeFloat(fval);
            dataOutputStream.writeInt(655359898);
            dataOutputStream.writeLong(8724782748327473432L);
            dataOutputStream.writeShort(439080987);
            dataOutputStream.writeUTF("以 utf - 8 的方式写入，oh yeah");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.safeClose(dataOutputStream);
        }
    }

    private void readByteByDataInputStream() {
        File readFile = new File(FileUtil.getDebugDumpDirectory(),FILE_NAME);
        DataInputStream dataInputStream = null;

        try {
            dataInputStream = new DataInputStream(new FileInputStream(readFile));
            StringBuilder stringBuilder = new StringBuilder();
            byte b = dataInputStream.readByte();
            stringBuilder.append("byte = " + b);
            boolean bool = dataInputStream.readBoolean();
            stringBuilder.append("boolean = " + bool);
            byte[] bytes = new byte[1024];
            dataInputStream.read(bytes);
            stringBuilder.append("string = " + new String(bytes));
            char c = dataInputStream.readChar();
            stringBuilder.append("char = " + c);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.safeClose(dataInputStream);
        }
    }

}
