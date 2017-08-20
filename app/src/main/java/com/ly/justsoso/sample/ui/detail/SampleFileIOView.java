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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

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
        LayoutInflater.from(context).inflate(R.layout.sample_simple_file_io, this, true);

    }

    @Override
    protected void init(Context context) {

        mTextView = (TextView) findViewById(R.id.simple_file_io_text_view);

        byteStream();
        charStream();
        byteToCharStream();
        pipedStream();
    }



    /**
     * 读写字节流，字节流相关操作
     */
    private void byteStream() {

        writeByteByFileOutPutStream();
        readByteByFileInputStream();

        writeByteByBufferedOutputStream();
        readByteByBufferedInputStream();

        writeByteByDataOutputStream();
        readByteByDataInputStream();
    }

    private void writeByteByFileOutPutStream() {
        File writeFile = new File(FileUtil.getDebugDumpDirectory(), FILE_NAME);
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
        File inFile = new File(FileUtil.getDebugDumpDirectory(), "writeFile.txt");
        Log.d(TAG, "readByteByFileInputStream: file length " + inFile.length());
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(inFile);
            byte[] buffer = new byte[1024];
            StringBuilder stringBuilder = new StringBuilder(1024);
            while (fileInputStream.read(buffer) != -1) {
                stringBuilder.append(new String(buffer, "GBK"));
            }

            Log.d(TAG, "readByteByFileInputStream: read content = " + stringBuilder.toString());
            mTextView.setText(stringBuilder.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.safeClose(fileInputStream);
        }
    }

    private void writeByteByBufferedOutputStream() {
        File writeFile = new File(FileUtil.getDebugDumpDirectory(), FILE_NAME);
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
        File readFile = new File(FileUtil.getDebugDumpDirectory(), FILE_NAME);
        BufferedInputStream bufferedInputStream = null;

        try {

            bufferedInputStream = new BufferedInputStream(new FileInputStream(readFile));
            byte[] buffer = new byte[1024];
            StringBuilder stringBuilder = new StringBuilder(1024);
            while ((bufferedInputStream.read(buffer)) != -1) {
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
        File writeFile = new File(FileUtil.getDebugDumpDirectory(), FILE_NAME);
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
        File readFile = new File(FileUtil.getDebugDumpDirectory(), FILE_NAME);
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
            Log.d(TAG, "readByteByDataInputStream: " + stringBuilder.toString());
            mTextView.setText(stringBuilder.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.safeClose(dataInputStream);
        }
    }


    /**
     * 读写字符流
     */

    private void charStream() {
        writeCharByFileWriter();
        readCharByFileReader();
        writeCharByBufferedWriter();
        readCharByBufferedReader();
    }

    private void writeCharByFileWriter() {

        File writeFile = new File(FileUtil.getDebugDumpDirectory(), FILE_NAME);
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(writeFile);
            fileWriter.write(4222342);/** 能写入什么呢 */
            fileWriter.write("看来可以直接写一个字符串，but,which is the charactor code");
            fileWriter.write(new char[]{12, 435, 8989, 43533, 65535, 6553}); /** 最大能写入的数值是 65535，所以一个 char 最多占两个字节咯*/
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.safeClose(fileWriter);
        }

    }

    private void readCharByFileReader() {

        File readFile = new File(FileUtil.getDebugDumpDirectory(), FILE_NAME);
        FileReader fileReader = null;

        try {
            fileReader = new FileReader(readFile);
            StringBuilder stringBuilder = new StringBuilder();
            int integer = fileReader.read();
            stringBuilder.append(integer);
            char[] buff = new char[512];
            while ((fileReader.read(buff)) != -1) {
                stringBuilder.append(new String(buff));
            }

            Log.d(TAG, "readCharByFileReader: " + stringBuilder.toString());
            mTextView.setText(stringBuilder.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.safeClose(fileReader);
        }

    }

    private void writeCharByBufferedWriter() {
        File writeFile = new File(FileUtil.getDebugDumpDirectory(), FILE_NAME);
        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(writeFile));
            bufferedWriter.write("今天一哥们儿在那里说自己是什么什么集团的接班人，我听到后笑了，从小老师就告诉我，我是共产主义接班人，我有说什么吗? so childish.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.safeClose(bufferedWriter);
        }
    }

    private void readCharByBufferedReader() {
        File readFile = new File(FileUtil.getDebugDumpDirectory(), FILE_NAME);
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(readFile));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            Log.d(TAG, "readCharByBufferedReader: " + stringBuilder.toString());
            mTextView.setText(stringBuilder.toString());
         } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.safeClose(bufferedReader);
        }
    }

    /**
     * 字节流转字符流
     */

    private void byteToCharStream() {
        writeByteByOutputStreamWriter();
        readByteByInputStreamReader();
    }

    private void writeByteByOutputStreamWriter() {

        File writeFile = new File(FileUtil.getDebugDumpDirectory(), FILE_NAME);
        OutputStreamWriter outputStreamWriter = null;

        try {
            outputStreamWriter = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(writeFile)));
            outputStreamWriter.write("将字符流写入，其最终文件落地则为字节流，yes or no? Certainly yes");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.safeClose(outputStreamWriter);
        }
    }

    private void readByteByInputStreamReader() {
        File readFile = new File(FileUtil.getDebugDumpDirectory(), FILE_NAME);
        InputStreamReader inputStreamReader = null;

        try {
            inputStreamReader = new InputStreamReader(new BufferedInputStream(new FileInputStream(readFile)));
            StringBuilder stringBufiler = new StringBuilder();
            char []buff = new char[1024];
            while((inputStreamReader.read(buff)) != -1) {
                stringBufiler.append(new String(buff));/** 将字节流以字符的方式写出 */
            }
            Log.d(TAG, "readByteByInputStreamReader: " + stringBufiler.toString());
            mTextView.setText(stringBufiler.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.safeClose(inputStreamReader);
        }
    }

    private void pipedStream() {

        PipedInputStream pipedInputStream = new PipedInputStream();
        PipedOutputStream pipedOutputStream = new PipedOutputStream();

        try {

            pipedOutputStream.connect(pipedInputStream);
            PipedInputStreamThread pipedInputStreamThread = new PipedInputStreamThread(pipedInputStream);
            PipedOutputSteamThread pipedOutputStreamThread = new PipedOutputSteamThread(pipedOutputStream);

            new Thread(pipedInputStreamThread).start();
            new Thread(pipedOutputStreamThread).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class PipedOutputSteamThread implements Runnable{

        PipedOutputStream pipedOutputStream;
        boolean stopFlag;
        PipedOutputSteamThread(PipedOutputStream outputStream) {
            pipedOutputStream = outputStream;
            stopFlag = false;
        }


        @Override
        public void run() {
            try {
                while (!stopFlag) {

                    Log.d(TAG, "PipedOutputSteamThread wirte : 我是 PipedOutputStream,我正在写入一个消息");
                    pipedOutputStream.write("我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息我是 PipedOutputStream,我正在写入一个消息".getBytes());
                    pipedOutputStream.flush();

                    Thread.yield();
                    Thread.sleep(5000);
                }
            }catch (Exception e) {
                e.printStackTrace();
            } finally {
                stopFlag = true;
                IoUtils.safeClose(pipedOutputStream);
            }
        }
    }

    class PipedInputStreamThread implements Runnable {

        PipedInputStream pipedInputStream;
        boolean stopFlag;

        PipedInputStreamThread(PipedInputStream inputStream) {
            pipedInputStream = inputStream;
            stopFlag = false;
        }

        @Override
        public void run() {
            try{
                StringBuilder stringBuilder = new StringBuilder();
                byte []buff = new byte[1024];
                int len = 0;
                while(!stopFlag) {
                    while(pipedInputStream.available() > 0) {
                        len = pipedInputStream.read(buff);
                        stringBuilder.append(new String(buff,0,len));
                    }
                    Log.d(TAG, "PipedInputStreamThread read : " + stringBuilder.toString());
                    stringBuilder.delete(0,stringBuilder.length());

                    Thread.yield();
                    Thread.sleep(1000);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                stopFlag = true;
                IoUtils.safeClose(pipedInputStream);
            }
        }
    }


}
