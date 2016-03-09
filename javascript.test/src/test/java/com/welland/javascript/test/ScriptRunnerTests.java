package com.welland.javascript.test;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestSubstringScript.class, TestSplitScript.class})
public class ScriptRunnerTests{
    @AfterClass
    public static void tearDownAfterClass() throws Exception{
        System.out.println("test complete, stop the process once the flight recorder log has been captured"); 
        while(true) {
            // Wait forever 
        }
    }
}
