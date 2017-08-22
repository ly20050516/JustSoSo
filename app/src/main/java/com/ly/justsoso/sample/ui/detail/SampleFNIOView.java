package com.ly.justsoso.sample.ui.detail;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.ly.framework.utilities.FileUtil;
import com.ly.justsoso.R;

import org.greenrobot.essentials.io.IoUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by ly on 2017/7/19.
 */

public class SampleFNIOView extends AbstractDetailView {

    public static final String TAG = "SampleFNIOView";
    private TextView mTextView;
    public SampleFNIOView(Context context) {
        super(context);
    }

    @Override
    protected void inflat(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sample_file_nio_layout_view,this,true);

    }

    @Override
    protected void init(Context context) {

        mTextView = (TextView) findViewById(R.id.simple_file_nio_text_view);
    }

    private void readByteByByteBufferAndRandomAccess() {

        RandomAccessFile randomAccessFile = null;
        File readFile = new File(FileUtil.getDebugDumpDirectory(),"random.txt");
        try {
            randomAccessFile = new RandomAccessFile(readFile,"rw");
            FileChannel fileChannel = randomAccessFile.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            int byteReads;
            while((byteReads = fileChannel.read(byteBuffer)) != -1) {
                Log.d(TAG, "readByteByByteBufferAndRandomAccess: byteReads = " + byteReads);
                byteBuffer.flip(); // position = 0
                while(byteBuffer.hasRemaining()) {
                    Log.d(TAG, "readByteByByteBufferAndRandomAccess: " + (char)byteBuffer.get());
                }
                byteBuffer.clear(); // position = 0,limit = capacity
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.safeClose(randomAccessFile);
        }

    }

    private void socketClientByNio() {

        SocketChannel socketChannel = null;

        try {
            socketChannel = SocketChannel.open();

            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("192.168.1.3",8080));

            ByteBuffer byteBuffer = ByteBuffer.allocate(2048);

            if(socketChannel.finishConnect()) {
                String info = "这就是基础不过关的情况下，还要在这里 ***，***，I am so embarrassed";
                byteBuffer.put(info.getBytes());
                byteBuffer.flip();
                while(byteBuffer.hasRemaining()) {
                    socketChannel.write(byteBuffer);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socketChannel != null) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void socketServer() {
        ServerSocket serverSocket = null;
        InputStream inputStream = null;

        try {
            serverSocket = new ServerSocket(8080);
            byte []buffer = new byte[1024];
            int receiveMsg = 0;

            Socket socket = serverSocket.accept();
            Log.d(TAG, "socketServer: " + socket.getRemoteSocketAddress().toString());
            inputStream = socket.getInputStream();

            while((receiveMsg = inputStream.read(buffer)) != -1) {
                byte []temp = new byte[receiveMsg];
                System.arraycopy(buffer,0,temp,0,receiveMsg);
                Log.d(TAG, "socketServer: receive " + new String(temp));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static final int BUFF_SIZE = 1024;
    public static final int PORT = 8080;
    public static final int TIME_OUT = 3000;
    private void socketServerByNio() {
        selector();
    }

    private void selector() {
        Selector selector  = null;
        ServerSocketChannel serverSocketChannel = null;

        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while(true) {
                if((selector.select(TIME_OUT)) == 0) {
                    Log.d(TAG, "selector: ==");
                    continue;
                }

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while(iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if(selectionKey.isAcceptable()) {
                        handleAccept(selectionKey);
                    }
                    if(selectionKey.isReadable()) {
                        handleRead(selectionKey);
                    }
                    if(selectionKey.isWritable() && selectionKey.isValid()) {
                        handleWrite(selectionKey);
                    }
                    if(selectionKey.isConnectable()) {
                        Log.d(TAG, "selector: isConnectable = true");
                    }

                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(serverSocketChannel != null) {
                try {
                    serverSocketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleWrite(SelectionKey selectionKey) {
        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
        byteBuffer.flip();
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        while(byteBuffer.hasRemaining()) {
            try {
                socketChannel.write(byteBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        byteBuffer.compact();
    }

    private void handleRead(SelectionKey selectionKey) {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
        try {
            int byteReads = 0;
            while((byteReads = socketChannel.read(byteBuffer)) > 0) {
                Log.d(TAG, "handleRead: byteReads = " + byteReads);
                byteBuffer.flip();
                while(byteBuffer.hasRemaining()) {
                    Log.d(TAG, "handleRead: " + (char)byteBuffer.get());
                }
                byteBuffer.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(socketChannel!= null) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleAccept(SelectionKey selectionKey) {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selectionKey.selector(),SelectionKey.OP_READ,ByteBuffer.allocate(BUFF_SIZE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
