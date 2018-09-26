package com.calculator;

import com.calculator.control.Calculator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculatorFunctionalTest {
    private static String FILEDIR = "src.test.testDB";
    private static final String TESTFILESUFFIX = ".txt";
    private static final String OUTFILESUFFIX = ".out";
    private static final String EXPECTFILESUFFIX = ".expect";

    @Before
    public void prepare() {
        FILEDIR = FILEDIR.replace('.', File.separatorChar) + File.separator;
    }

    @Test
    public void testSimple() {
        testSingleOne("test_simple");
    }

    @Test
    public void testExample1() {
        testSingleOne("test_example1");
    }

    @Test
    public void testExample2() {
        testSingleOne("test_example2");
    }

    @Test
    public void testExample3() {
        testSingleOne("test_example3");
    }

    @Test
    public void testExample4() {
        testSingleOne("test_example4");
    }

    @Test
    public void testExample5() {
        testSingleOne("test_example5");
    }

    @Test
    public void testExample6() {
        testSingleOne("test_example6");
    }

    @Test
    public void testExample7() {
        testSingleOne("test_example7");
    }

    @Test
    public void testExample8() {
        testSingleOne("test_example8");
    }

//    @After
    public void clean() {
        File baseDir = new File(FILEDIR);
        if (!baseDir.exists() || !baseDir.isDirectory()) {
            return;
        }
        String[] allFiles = baseDir.list();
        for (String filename : allFiles) {
            if (filename.endsWith(OUTFILESUFFIX)) {
                System.out.println("clean " + filename);
                File outFile = new File(FILEDIR + filename);
                outFile.delete();
            }
        }
    }

    //    @Test
    public void testAllInOne() {
        File baseDir = new File(FILEDIR);
        if (!baseDir.exists() || !baseDir.isDirectory()) {
            assertTrue(false);
        }
        String[] allFiles = baseDir.list();
        ArrayList<String> allTestName = new ArrayList<>();
        for (String filename : allFiles) {
            if (filename.endsWith(TESTFILESUFFIX)) {
                String executeName = filename.substring(0, filename.length() - TESTFILESUFFIX.length());
                allTestName.add(executeName);
            }
        }
        for (String testName : allTestName) {
            System.out.println("start " + testName);
            testSingleOne(testName);
        }
    }

    private void testSingleOne(String fileName) {
        Calculator calculator = new Calculator("Test");
        String testFileName = FILEDIR + fileName + TESTFILESUFFIX;
        String outFileName = FILEDIR + fileName + OUTFILESUFFIX;
        PrintStream old = System.out;
        try (BufferedReader testReader = getReader(testFileName);) {
            File outFile = new File(outFileName);
            if (outFile.exists()) {
                outFile.delete();
            }
            if (!outFile.createNewFile()) {
                throw new UnsupportedOperationException(outFileName);
            }
            PrintStream newOut = new PrintStream(outFile);
            System.setOut(newOut);
            String testLineInfo = null;
            while ((testLineInfo = testReader.readLine()) != null) {
                calculator.execute(testLineInfo);
                calculator.display();
            }
        } catch (Exception e) {
            System.setOut(old);
            System.out.println(e.getMessage());
        } finally {
            System.setOut(old);
            assertTrue(diffFileOutAndExpect(fileName));
        }
    }

    private boolean diffFileOutAndExpect(String fileName) {
        String expectfileName = FILEDIR + fileName + EXPECTFILESUFFIX;
        String outfileName = FILEDIR + fileName + OUTFILESUFFIX;
        try (
                BufferedReader expectReader = getReader(expectfileName);
                BufferedReader outReader = getReader(outfileName);
        ) {
            String expectLineInfo = null;
            String outLineInfo = null;
            while ((expectLineInfo = expectReader.readLine()) != null &&
                    (outLineInfo = outReader.readLine()) != null) {
                if (!expectLineInfo.trim().equals(outLineInfo.trim())) {
                    return false;
                }
            }
            if (expectLineInfo != null) {
                return false;
            }
            if (outReader != null && outReader.readLine() != null) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private BufferedReader getReader(String wholeFileName) throws Exception {
        File file = new File(wholeFileName);
        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            throw new FileNotFoundException(wholeFileName);
        }
        FileInputStream inputStream = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader;
    }

}
