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
import com.nativelibs4java.opencl.CLKernel;
import com.nativelibs4java.opencl.CLMem.Usage;
import com.nativelibs4java.opencl.CLProgram;
import com.nativelibs4java.opencl.CLQueue;
import com.nativelibs4java.opencl.JavaCL;
import com.nativelibs4java.util.IOUtils;

public class JavaCLTutorial1 {

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

        // Read the program sources and compile them :
        String src = IOUtils.readText(JavaCLTutorial1.class.getResource("TutorialKernels.cl"));
        CLProgram program = context.createProgram(src);

        // Get and call the kernel :
        CLKernel addFloatsKernel = program.createKernel("add_floats");
        addFloatsKernel.setArgs(a, b, out, n);
        int[] globalSizes = new int[] { n };
        CLEvent addEvt = addFloatsKernel.enqueueNDRange(queue, globalSizes);

        // blocks until add_floats finished
        Pointer<Float> outPtr = out.read(queue, addEvt);

        // Release everything
        context.release();
        queue.release();
        aPtr.release();
        bPtr.release();
        a.release();
        out.release();
        program.release();
        addEvt.release();
        outPtr.release();

    }
}