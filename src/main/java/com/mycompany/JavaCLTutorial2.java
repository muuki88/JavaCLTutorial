package com.mycompany;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.bridj.Pointer.allocateFloats;

import java.io.IOException;
import java.nio.ByteOrder;

import org.bridj.Pointer;

import com.nativelibs4java.opencl.CLBuffer;
import com.nativelibs4java.opencl.CLContext;
import com.nativelibs4java.opencl.CLEvent;
import com.nativelibs4java.opencl.CLMem.Usage;
import com.nativelibs4java.opencl.CLQueue;
import com.nativelibs4java.opencl.JavaCL;

public class JavaCLTutorial2 {
    public static void main(String[] args) throws IOException {
        CLContext context = JavaCL.createBestContext();
        CLQueue queue = context.createDefaultQueue();
        ByteOrder byteOrder = context.getByteOrder();

        int n = 1024;
        Pointer<Float> aPtr = allocateFloats(n).order(byteOrder), bPtr = allocateFloats(n).order(byteOrder);

        for (int i = 0; i < n; i++) {
            aPtr.set(i, (float) cos(i));
            bPtr.set(i, (float) sin(i));
        }

        // Create OpenCL input buffers (using the native memory pointers aPtr
        // and bPtr) :
        CLBuffer<Float> a = context.createFloatBuffer(Usage.Input, aPtr), b = context.createFloatBuffer(Usage.Input, bPtr);

        // Create an OpenCL output buffer :
        CLBuffer<Float> out = context.createFloatBuffer(Usage.Output, n);

        TutorialKernels kernels = new TutorialKernels(context);
        int[] globalSizes = new int[] { n };
        CLEvent addEvt = kernels.add_floats(queue, a, b, out, n, globalSizes, null);

        // blocks until add_floats finished
        Pointer<Float> outPtr = out.read(queue, addEvt);

        // Release everything
        context.release();
        queue.release();
        aPtr.release();
        bPtr.release();
        a.release();
        out.release();
        addEvt.release();
        outPtr.release();

    }
}
