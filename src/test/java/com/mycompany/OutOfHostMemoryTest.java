package com.mycompany;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import com.nativelibs4java.opencl.JavaCL;

public class OutOfHostMemoryTest {

    private final int runs = 100;

    @Test
    public void testCreateBestContext() {
        for (int i = 0; i < runs; i++) {
            JavaCL.createBestContext();
        }
    }

    @Test
    @Ignore("Already fails on CreateBestContext")
    public void testJavaCLTutorial1() throws IOException {
        for (int i = 0; i < runs; i++) {
            JavaCLTutorial1.main(new String[] {});
        }
    }

    @Test
    @Ignore("Already fails on JavaCLTutorial1")
    public void testJavaCLTutorial2() throws IOException {
        for (int i = 0; i < runs; i++) {
            JavaCLTutorial2.main(new String[] {});
        }
    }

    @Test
    @Ignore("Already fails on JavaCLTutorial1")
    public void testJavaCLTutorial3() throws IOException {
        for (int i = 0; i < runs; i++) {
            JavaCLTutorial3.main(new String[] {});
        }
    }

}
