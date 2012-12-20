package com.mycompany;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

public class OutOfHostMemoryTest {

    private final int runs = 20;

    @Test
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
