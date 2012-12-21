package com.mycompany;

import com.nativelibs4java.opencl.JavaCL;

/**
 * Test class to demonstrate OutOfHostMemoryException
 * 
 * @author muki
 * 
 */
public class OutOfHostMemoryTestApp {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // OutOfHostMemory or OutOfResources Exception
        for (int i = 0; i < 100; i++) {
            JavaCL.createBestContext();
        }
    }

}
