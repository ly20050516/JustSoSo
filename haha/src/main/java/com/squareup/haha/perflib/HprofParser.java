/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.squareup.haha.perflib;

import com.squareup.haha.annotations.NonNull;
import com.squareup.haha.perflib.io.FileLog;
import com.squareup.haha.perflib.io.HprofBuffer;
import com.squareup.haha.guava.primitives.UnsignedBytes;
import com.squareup.haha.guava.primitives.UnsignedInts;

import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.squareup.haha.trove.TLongObjectHashMap;

public class HprofParser {

    private static final int STRING_IN_UTF8 = 0x01;

    private static final int LOAD_CLASS = 0x02;

    @SuppressWarnings("UnusedDeclaration")
    private static final int UNLOAD_CLASS = 0x03;

    private static final int STACK_FRAME = 0x04;

    private static final int STACK_TRACE = 0x05;

    @SuppressWarnings("UnusedDeclaration")
    private static final int ALLOC_SITES = 0x06;

    @SuppressWarnings("UnusedDeclaration")
    private static final int HEAP_SUMMARY = 0x07;

    @SuppressWarnings("UnusedDeclaration")
    private static final int START_THREAD = 0x0a;

    @SuppressWarnings("UnusedDeclaration")
    private static final int END_THREAD = 0x0b;

    private static final int HEAP_DUMP = 0x0c;

    private static final int HEAP_DUMP_SEGMENT = 0x1c;

    @SuppressWarnings("UnusedDeclaration")
    private static final int HEAP_DUMP_END = 0x2c;

    @SuppressWarnings("UnusedDeclaration")
    private static final int CPU_SAMPLES = 0x0d;

    @SuppressWarnings("UnusedDeclaration")
    private static final int CONTROL_SETTINGS = 0x0e;

    private static final int ROOT_UNKNOWN = 0xff;

    private static final int ROOT_JNI_GLOBAL = 0x01;

    private static final int ROOT_JNI_LOCAL = 0x02;

    private static final int ROOT_JAVA_FRAME = 0x03;

    private static final int ROOT_NATIVE_STACK = 0x04;

    private static final int ROOT_STICKY_CLASS = 0x05;

    private static final int ROOT_THREAD_BLOCK = 0x06;

    private static final int ROOT_MONITOR_USED = 0x07;

    private static final int ROOT_THREAD_OBJECT = 0x08;

    private static final int ROOT_CLASS_DUMP = 0x20;

    private static final int ROOT_INSTANCE_DUMP = 0x21;

    private static final int ROOT_OBJECT_ARRAY_DUMP = 0x22;

    private static final int ROOT_PRIMITIVE_ARRAY_DUMP = 0x23;

    /**
     * Android format addition
     *
     * Specifies information about which heap certain objects came from. When a sub-tag of this type
     * appears in a HPROF_HEAP_DUMP or HPROF_HEAP_DUMP_SEGMENT record, entries that follow it will
     * be associated with the specified heap.  The HEAP_DUMP_INFO data is reset at the end of the
     * HEAP_DUMP[_SEGMENT].  Multiple HEAP_DUMP_INFO entries may appear in a single
     * HEAP_DUMP[_SEGMENT].
     *
     * Format: u1: Tag value (0xFE) u4: heap ID ID: heap name string ID
     */
    private static final int ROOT_HEAP_DUMP_INFO = 0xfe;

    private static final int ROOT_INTERNED_STRING = 0x89;

    private static final int ROOT_FINALIZING = 0x8a;

    private static final int ROOT_DEBUGGER = 0x8b;

    private static final int ROOT_REFERENCE_CLEANUP = 0x8c;

    private static final int ROOT_VM_INTERNAL = 0x8d;

    private static final int ROOT_JNI_MONITOR = 0x8e;

    private static final int ROOT_UNREACHABLE = 0x90;

    private static final int ROOT_PRIMITIVE_ARRAY_NODATA = 0xc3;

    @NonNull
    private final HprofBuffer mInput;

    int mIdSize;

    Snapshot mSnapshot;

    /*
     * These are only needed while parsing so are not kept as part of the
     * heap data.
     */
    @NonNull
    TLongObjectHashMap<String> mStrings = new TLongObjectHashMap<String>();

    @NonNull
    TLongObjectHashMap<String> mClassNames = new TLongObjectHashMap<String>();

    public HprofParser(@NonNull HprofBuffer buffer) {
        mInput = buffer;
    }

    @NonNull
    public final Snapshot parse() {
        Snapshot snapshot = new Snapshot(mInput);
        mSnapshot = snapshot;

        try {
            try {
                readNullTerminatedString();  // Version, ignored for now.

                mIdSize = mInput.readInt();
                FileLog.d("parse id size 4",mIdSize);
                mSnapshot.setIdSize(mIdSize);

                long t = mInput.readLong();  // Timestamp, ignored for now.
                FileLog.d("parse time stamp 8",t);

                while (mInput.hasRemaining()) {
                    int tag = readUnsignedByte();
                    FileLog.d("parse while tag 1",tag);
                    int ts = mInput.readInt(); // Ignored: timestamp
                    FileLog.d("parse while timestamp 4",ts);
                    long length = readUnsignedInt();
                    FileLog.d("parse while length 4",length);

                    switch (tag) {
                        case STRING_IN_UTF8:
                            // String length is limited by Int.MAX_VALUE anyway.
                            loadString((int) length - mIdSize);
                            break;

                        case LOAD_CLASS:
                            loadClass();
                            break;

                        case STACK_FRAME:
                            loadStackFrame();
                            break;

                        case STACK_TRACE:
                            loadStackTrace();
                            break;

                        case HEAP_DUMP:
                            loadHeapDump(length);
                            mSnapshot.setToDefaultHeap();
                            break;

                        case HEAP_DUMP_SEGMENT:
                            loadHeapDump(length);
                            mSnapshot.setToDefaultHeap();
                            break;

                        default:
                            skipFully(length);
                    }

                }
            } catch (EOFException eof) {
                //  this is fine
            }
            mSnapshot.resolveClasses();
            mSnapshot.resolveReferences();
            // TODO: enable this after the dominators computation is also optimized.
            // mSnapshot.computeRetainedSizes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mClassNames.clear();
        mStrings.clear();
        return snapshot;
    }

    @NonNull
    private String readNullTerminatedString() throws IOException {
        StringBuilder s = new StringBuilder();
        for (byte c = mInput.readByte(); c != 0; c = mInput.readByte()) {
            s.append((char) c);
        }
        FileLog.d("readNullTerminatedString",s.toString());
        return s.toString();
    }

    private long readId() throws IOException {
        // As long as we don't interpret IDs, reading signed values here is fine.
        switch (mIdSize) {
            case 1:
                return mInput.readByte();
            case 2:
                return mInput.readShort();
            case 4:
                return mInput.readInt();
            case 8:
                return mInput.readLong();
        }

        throw new IllegalArgumentException("ID Length must be 1, 2, 4, or 8");
    }

    @NonNull
    private String readUTF8(int length) throws IOException {
        byte[] b = new byte[length];

        mInput.read(b);

        return new String(b, "utf-8");
    }

    private int readUnsignedByte() throws IOException {
        return UnsignedBytes.toInt(mInput.readByte());
    }

    private int readUnsignedShort() throws IOException {
        return mInput.readShort() & 0xffff;
    }

    private long readUnsignedInt() throws IOException {
        return UnsignedInts.toLong(mInput.readInt());
    }

    private void loadString(int length) throws IOException {
        long id = readId();
        FileLog.d("load string id 4",id);
        String string = readUTF8(length);
        FileLog.d("load string string",string);
        mStrings.put(id, string);
    }

    private void loadClass() throws IOException {
        int csn = mInput.readInt();  // Ignored: Class serial number.
        FileLog.d("load class Class serial number 4",csn);
        long id = readId();
        FileLog.d("load class Class id 4",id);
        int stsn = mInput.readInt(); // Ignored: Stack trace serial number.
        FileLog.d("load class Stack trace serial number 4",stsn);
        long classStringId = readId();
        FileLog.d("load class Class string id 4",classStringId);
        String name = mStrings.get(classStringId);
//        FileLog.d("load class Class name",name);
        mClassNames.put(id, name);
    }

    private void loadStackFrame() throws IOException {
        long id = readId();
        FileLog.d("load stack frame 4",id);
        long methodNameId = readId();
        FileLog.d("load stack frame method name id 4",methodNameId);
        String methodName = mStrings.get(methodNameId);
//        FileLog.d("load stack frame method name" + methodName);
        long methodSignatureId = readId();
        FileLog.d("load stack frame method Signature Id 4",methodSignatureId);
        String methodSignature = mStrings.get(methodSignatureId);
//        FileLog.d("load stack frame method Signature",methodSignature);
        long sourceFileId = readId();
        FileLog.d("load stack frame source File Id 4",sourceFileId);
        String sourceFile = mStrings.get(sourceFileId);
        int serial = mInput.readInt();
        FileLog.d("load stack frame serial 4",serial);
        int lineNumber = mInput.readInt();
        FileLog.d("load stack frame lineNumber 4",lineNumber);
        StackFrame frame = new StackFrame(id, methodName, methodSignature,
                sourceFile, serial, lineNumber);

        mSnapshot.addStackFrame(frame);
    }

    private void loadStackTrace() throws IOException {
        int serialNumber = mInput.readInt();
        FileLog.d("load Stack Trace serialNumber 4",serialNumber);
        int threadSerialNumber = mInput.readInt();
        FileLog.d("load Stack Trace threadSerialNumber 4",threadSerialNumber);

        final int numFrames = mInput.readInt();
        FileLog.d("load Stack Trace numFrames 4",numFrames);

        StackFrame[] frames = new StackFrame[numFrames];

        for (int i = 0; i < numFrames; i++) {
            long stackFrameId = readId();
            FileLog.d("load Stack Trace stack Frame Id 4",stackFrameId);
            frames[i] = mSnapshot.getStackFrame(stackFrameId);
        }

        StackTrace trace = new StackTrace(serialNumber, threadSerialNumber, frames);

        mSnapshot.addStackTrace(trace);
    }

    private void loadHeapDump(long length) throws IOException {
        while (length > 0) {
            int tag = readUnsignedByte();
            FileLog.d("load Heap Dump tag 1",tag);

            length--;

            switch (tag) {
                case ROOT_UNKNOWN:
                    length -= loadBasicObj(RootType.UNKNOWN);
                    break;

                case ROOT_JNI_GLOBAL:
                    length -= loadBasicObj(RootType.NATIVE_STATIC);
                    long jniGlobalId = readId();   //  ignored
                    FileLog.d("load Heap Dump jni global id 4",jniGlobalId);
                    length -= mIdSize;
                    break;

                case ROOT_JNI_LOCAL:
                    length -= loadJniLocal();
                    break;

                case ROOT_JAVA_FRAME:
                    length -= loadJavaFrame();
                    break;

                case ROOT_NATIVE_STACK:
                    length -= loadNativeStack();
                    break;

                case ROOT_STICKY_CLASS:
                    length -= loadBasicObj(RootType.SYSTEM_CLASS);
                    break;

                case ROOT_THREAD_BLOCK:
                    length -= loadThreadBlock();
                    break;

                case ROOT_MONITOR_USED:
                    length -= loadBasicObj(RootType.BUSY_MONITOR);
                    break;

                case ROOT_THREAD_OBJECT:
                    length -= loadThreadObject();
                    break;

                case ROOT_CLASS_DUMP:
                    length -= loadClassDump();
                    break;

                case ROOT_INSTANCE_DUMP:
                    length -= loadInstanceDump();
                    break;

                case ROOT_OBJECT_ARRAY_DUMP:
                    length -= loadObjectArrayDump();
                    break;

                case ROOT_PRIMITIVE_ARRAY_DUMP:
                    length -= loadPrimitiveArrayDump();
                    break;

                case ROOT_PRIMITIVE_ARRAY_NODATA:
                    System.err.println("+--- PRIMITIVE ARRAY NODATA DUMP");
                    length -= loadPrimitiveArrayDump();

                    throw new IllegalArgumentException(
                            "Don't know how to load a nodata array");

                case ROOT_HEAP_DUMP_INFO:
                    int heapId = mInput.readInt();
                    FileLog.d("load Heap Dump heap Id 4",heapId);

                    long heapNameId = readId();
                    FileLog.d("load Heap Dump heap Name Id 4",heapNameId);

                    String heapName = mStrings.get(heapNameId);

                    mSnapshot.setHeapTo(heapId, heapName);
                    length -= 4 + mIdSize;
                    break;

                case ROOT_INTERNED_STRING:
                    length -= loadBasicObj(RootType.INTERNED_STRING);
                    break;

                case ROOT_FINALIZING:
                    length -= loadBasicObj(RootType.FINALIZING);
                    break;

                case ROOT_DEBUGGER:
                    length -= loadBasicObj(RootType.DEBUGGER);
                    break;

                case ROOT_REFERENCE_CLEANUP:
                    length -= loadBasicObj(RootType.REFERENCE_CLEANUP);
                    break;

                case ROOT_VM_INTERNAL:
                    length -= loadBasicObj(RootType.VM_INTERNAL);
                    break;

                case ROOT_JNI_MONITOR:
                    length -= loadJniMonitor();
                    break;

                case ROOT_UNREACHABLE:
                    length -= loadBasicObj(RootType.UNREACHABLE);
                    break;

                default:
                    throw new IllegalArgumentException(
                            "loadHeapDump loop with unknown tag " + tag
                                    + " with " + mInput.remaining()
                                    + " bytes possibly remaining");
            }
        }
    }

    private int loadJniLocal() throws IOException {
        long id = readId();
        FileLog.d("load Jni Local Id 4",id);
        int threadSerialNumber = mInput.readInt();
        FileLog.d("load Jni Local threadSerialNumber 4",threadSerialNumber);

        int stackFrameNumber = mInput.readInt();
        FileLog.d("load Jni Local stackFrameNumber 4",stackFrameNumber);

        ThreadObj thread = mSnapshot.getThread(threadSerialNumber);
        StackTrace trace = mSnapshot.getStackTraceAtDepth(thread.mStackTrace, stackFrameNumber);
        RootObj root = new RootObj(RootType.NATIVE_LOCAL, id, threadSerialNumber, trace);

        mSnapshot.addRoot(root);

        return mIdSize + 4 + 4;
    }

    private int loadJavaFrame() throws IOException {
        long id = readId();
        FileLog.d("loadJavaFrame id 4",id);

        int threadSerialNumber = mInput.readInt();
        FileLog.d("loadJavaFrame threadSerialNumber 4",threadSerialNumber);

        int stackFrameNumber = mInput.readInt();
        FileLog.d("loadJavaFrame stackFrameNumber 4",stackFrameNumber);

        ThreadObj thread = mSnapshot.getThread(threadSerialNumber);
        StackTrace trace = mSnapshot.getStackTraceAtDepth(thread.mStackTrace, stackFrameNumber);
        RootObj root = new RootObj(RootType.JAVA_LOCAL, id, threadSerialNumber, trace);

        mSnapshot.addRoot(root);

        return mIdSize + 4 + 4;
    }

    private int loadNativeStack() throws IOException {
        long id = readId();
        FileLog.d("loadNativeStack id 4",id);

        int threadSerialNumber = mInput.readInt();
        FileLog.d("loadNativeStack threadSerialNumber 4",threadSerialNumber);

        ThreadObj thread = mSnapshot.getThread(threadSerialNumber);
        StackTrace trace = mSnapshot.getStackTrace(thread.mStackTrace);
        RootObj root = new RootObj(RootType.NATIVE_STACK, id, threadSerialNumber, trace);

        mSnapshot.addRoot(root);

        return mIdSize + 4;
    }

    private int loadBasicObj(RootType type) throws IOException {
        long id = readId();
        FileLog.d("loadBasicObj id 4",id);

        RootObj root = new RootObj(type, id);

        mSnapshot.addRoot(root);

        return mIdSize;
    }

    private int loadThreadBlock() throws IOException {
        long id = readId();
        FileLog.d("loadThreadBlock id 4",id);

        int threadSerialNumber = mInput.readInt();
        FileLog.d("loadThreadBlock threadSerialNumber 4",threadSerialNumber);

        ThreadObj thread = mSnapshot.getThread(threadSerialNumber);
        StackTrace stack = mSnapshot.getStackTrace(thread.mStackTrace);
        RootObj root = new RootObj(RootType.THREAD_BLOCK, id, threadSerialNumber, stack);

        mSnapshot.addRoot(root);

        return mIdSize + 4;
    }

    private int loadThreadObject() throws IOException {
        long id = readId();
        FileLog.d("loadThreadObject id 4",id);

        int threadSerialNumber = mInput.readInt();
        FileLog.d("loadThreadObject threadSerialNumber 4",threadSerialNumber);

        int stackSerialNumber = mInput.readInt();
        FileLog.d("loadThreadObject stackSerialNumber 4",stackSerialNumber);

        ThreadObj thread = new ThreadObj(id, stackSerialNumber);

        mSnapshot.addThread(thread, threadSerialNumber);

        return mIdSize + 4 + 4;
    }

    private int loadClassDump() throws IOException {
        final long id = readId();
        FileLog.d("loadClassDump id 4",id);

        int stackSerialNumber = mInput.readInt();
        FileLog.d("loadClassDump stackSerialNumber 4",stackSerialNumber);

        StackTrace stack = mSnapshot.getStackTrace(stackSerialNumber);
        final long superClassId = readId();
        FileLog.d("loadClassDump superClassId 4",superClassId);

        final long classLoaderId = readId();
        FileLog.d("loadClassDump classLoaderId 4",classLoaderId);

        readId(); // Ignored: Signeres ID.
        FileLog.d("loadClassDump Signeres id 4");

        readId(); // Ignored: Protection domain ID.
        FileLog.d("loadClassDump Protection domain ID 4");

        readId(); // RESERVED.
        FileLog.d("loadClassDump RESERVED 4");

        readId(); // RESERVED.
        FileLog.d("loadClassDump RESERVED 4");


        int instanceSize = mInput.readInt();
        FileLog.d("loadClassDump instanceSize 4",instanceSize);


        int bytesRead = (7 * mIdSize) + 4 + 4;

        //  Skip over the constant pool
        int numEntries = readUnsignedShort();
        FileLog.d("loadClassDump Skip over the constant pool numEntries 2",numEntries);


        bytesRead += 2;

        for (int i = 0; i < numEntries; i++) {
            readUnsignedShort();
            FileLog.d("loadClassDump readUnsignedShort 2");

            bytesRead += 2 + skipValue();
        }

        final ClassObj theClass = new ClassObj(id, stack, mClassNames.get(id), mInput.position());
        theClass.setSuperClassId(superClassId);
        theClass.setClassLoaderId(classLoaderId);

        //  Skip over static fields
        numEntries = readUnsignedShort();
        FileLog.d("loadClassDump Skip over static fields numEntries 2",numEntries);

        bytesRead += 2;

        Field[] staticFields = new Field[numEntries];

        for (int i = 0; i < numEntries; i++) {
            long staticFieldsId = readId();
            FileLog.d("loadClassDump staticFieldsId 4",staticFieldsId);

            String name = mStrings.get(staticFieldsId);
            byte b = mInput.readByte();
            FileLog.d("loadClassDump static fileds type 1",b);
            Type type = Type.getType(b);

            staticFields[i] = new Field(type, name);
            skipFully(mSnapshot.getTypeSize(type));

            bytesRead += mIdSize + 1 + mSnapshot.getTypeSize(type);
        }

        theClass.setStaticFields(staticFields);

        //  Instance fields
        numEntries = readUnsignedShort();
        FileLog.d("loadClassDump Instance fields numEntries 2",numEntries);

        bytesRead += 2;

        Field[] fields = new Field[numEntries];

        for (int i = 0; i < numEntries; i++) {
            long instanceId = readId();
            FileLog.d("loadClassDump Instance id 2",instanceId);
            String name = mStrings.get(instanceId);

            int instanceType = readUnsignedByte();
            FileLog.d("loadClassDump instanceType 1",instanceType);

            Type type = Type.getType(instanceType);

            fields[i] = new Field(type, name);

            bytesRead += mIdSize + 1;
        }

        theClass.setFields(fields);
        theClass.setInstanceSize(instanceSize);

        mSnapshot.addClass(id, theClass);

        return bytesRead;
    }

    private int loadInstanceDump() throws IOException {
        long id = readId();
        FileLog.d("loadInstanceDump id 4",id);

        int stackId = mInput.readInt();
        FileLog.d("loadInstanceDump stackId 4",stackId);

        StackTrace stack = mSnapshot.getStackTrace(stackId);
        long classId = readId();
        FileLog.d("loadInstanceDump classId 4",classId);

        int remaining = mInput.readInt();
        FileLog.d("loadInstanceDump remaining 4",remaining);


        long position = mInput.position();
        ClassInstance instance = new ClassInstance(id, stack, position);
        instance.setClassId(classId);
        mSnapshot.addInstance(id, instance);

        skipFully(remaining);
        return mIdSize + 4 + mIdSize + 4 + remaining;
    }

    private int loadObjectArrayDump() throws IOException {
        final long id = readId();
        FileLog.d("loadObjectArrayDump id 4",id);

        int stackId = mInput.readInt();
        FileLog.d("loadObjectArrayDump stackId 4",stackId);

        StackTrace stack = mSnapshot.getStackTrace(stackId);
        int numElements = mInput.readInt();
        FileLog.d("loadObjectArrayDump numElements 4",numElements);

        long classId = readId();
        FileLog.d("loadObjectArrayDump classId 4",classId);

        ArrayInstance array =
                new ArrayInstance(id, stack, Type.OBJECT, numElements, mInput.position());
        array.setClassId(classId);
        mSnapshot.addInstance(id, array);

        int remaining = numElements * mIdSize;
        skipFully(remaining);
        return mIdSize + 4 + 4 + mIdSize + remaining;
    }

    private int loadPrimitiveArrayDump() throws IOException {
        long id = readId();
        FileLog.d("loadPrimitiveArrayDump id 4",id);

        int stackId = mInput.readInt();
        FileLog.d("loadPrimitiveArrayDump stackId 4",stackId);

        StackTrace stack = mSnapshot.getStackTrace(stackId);
        int numElements = mInput.readInt();
        FileLog.d("loadPrimitiveArrayDump numElements 4",numElements);

        int primitiveType = readUnsignedByte();
        FileLog.d("loadPrimitiveArrayDump primitiveType 1",primitiveType);

        Type type = Type.getType(primitiveType);
        int size = mSnapshot.getTypeSize(type);
        ArrayInstance array = new ArrayInstance(id, stack, type, numElements, mInput.position());
        mSnapshot.addInstance(id, array);

        int remaining = numElements * size;
        skipFully(remaining);
        return mIdSize + 4 + 4 + 1 + remaining;
    }

    private int loadJniMonitor() throws IOException {
        long id = readId();
        FileLog.d("loadJniMonitor id 4",id);

        int threadSerialNumber = mInput.readInt();
        FileLog.d("loadJniMonitor threadSerialNumber 4",threadSerialNumber);

        int stackDepth = mInput.readInt();
        FileLog.d("loadJniMonitor stackDepth 4",stackDepth);

        ThreadObj thread = mSnapshot.getThread(threadSerialNumber);
        StackTrace trace = mSnapshot.getStackTraceAtDepth(thread.mStackTrace, stackDepth);
        RootObj root = new RootObj(RootType.NATIVE_MONITOR, id, threadSerialNumber, trace);

        mSnapshot.addRoot(root);

        return mIdSize + 4 + 4;
    }

    private int skipValue() throws IOException {
        int typeByte = readUnsignedByte();
        Type type = Type.getType(typeByte);
        FileLog.d("skipValue typeByte 1",typeByte);

        int size = mSnapshot.getTypeSize(type);

        skipFully(size);

        return size + 1;
    }

    private void skipFully(long numBytes) throws IOException {
        FileLog.d("skip fully",numBytes);
        mInput.setPosition(mInput.position() + numBytes);
    }
}
