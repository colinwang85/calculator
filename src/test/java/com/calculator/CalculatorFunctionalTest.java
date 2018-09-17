package com.calculator;

import com.calculator.control.Calculator;
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
    public void prepare(){
        FILEDIR = FILEDIR.replace('.',File.separatorChar)+File.separator;
    }
    @Test
    public void testSimple() {
        testSingleOne("test_simple");
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
            if (filename.endsWith("txt")) {
                String executeName = filename.substring(0, filename.length() - 4);
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
                throw new FileNotFoundException(outFileName);
            }
            PrintStream newOut = new PrintStream(new File(outFileName));
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

    private boolean isResult(String lineInfo) {
        if (lineInfo.startsWith("stack")) {
            return true;
        }
        return false;
    }
}
