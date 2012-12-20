package com.mycompany;

import java.io.IOException;

import org.bridj.Pointer;

import com.nativelibs4java.opencl.CLBuffer;
import com.nativelibs4java.opencl.CLContext;
import com.nativelibs4java.opencl.CLEvent;
import com.nativelibs4java.opencl.CLMem.Usage;
import com.nativelibs4java.opencl.CLQueue;
import com.nativelibs4java.opencl.JavaCL;

public class JavaCLTutorial3 {
    public static void main(String[] args) throws IOException {
        CLContext context = JavaCL.createBestContext();
        CLQueue queue = context.createDefaultQueue();

        int n = 1024;
        
        // Create OpenCL input and output buffers
        CLBuffer<Float> 
            a = context.createFloatBuffer(Usage.InputOutput, n), // a and b and read AND written to
            b = context.createFloatBuffer(Usage.InputOutput, n),
            out = context.createFloatBuffer(Usage.Output, n);

        TutorialKernels kernels = new TutorialKernels(context);
        int[] globalSizes = new int[] { n };
        CLEvent fillEvt = kernels.fill_in_values(queue, a, b, n, globalSizes, null);
        CLEvent addEvt = kernels.add_floats(queue, a, b, out, n, globalSizes, null, fillEvt);
        
        // blocks until add_floats finished
        Pointer<Float> outPtr = out.read(queue, addEvt);

        // Release everything
        context.release();
        queue.release();
        a.release();
        b.release();
        out.release();
        fillEvt.release();
        addEvt.release();
        outPtr.release();
        
    }
}
