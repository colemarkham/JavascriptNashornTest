/**
 * 
 */
package com.welland.javascript.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

/**
 * @author cole
 *
 */
@RunWith(Parameterized.class)
public class TestSplitScript{
    
    private static final String SCRIPT =  
                        "var index = 1;\n" +
                        "var values = input.split(\"*\");\n" +
                        "if (values.length > index) {\n" +
                        "   utils.setResult(values[1]);\n" +
                        "} else { \n" +
                        "   utils.setResult(\"\");" +
                        "}";;
    private static final int NUMBER_OF_TEST_RUNS = 10; 
    private static final int NUMBER_OF_STRINGS_PER_TEST_RUNS = 1000;    
    
    private static CompiledScript compiledScript = null;
    
    @Parameter
    public List<String> input;
    
    @Parameterized.Parameters
    public static List<Object[]> data() {
        List<Object[]> params = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_TEST_RUNS; i++) {
            List<String> input = new ArrayList<>();
            for (int j = 0; j < NUMBER_OF_STRINGS_PER_TEST_RUNS * i; j++) {
                input.add(generateInputString());
            }           
            params.add(new Object[] { input });
        }
        return params;
    }
    
    private static String generateInputString(){
        int length = ThreadLocalRandom.current().nextInt(4, 9);
        return RandomStringUtils.random(length, true, true) + "*" + RandomStringUtils.random(length, true, true);
    }   

    @BeforeClass
    public static void setUpBeforeClass() throws Exception{
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        Compilable compilingEngine = (Compilable) engine;

        compiledScript = compilingEngine.compile(SCRIPT);    
    }

    /**
     * Test method for {@link com.welland.javascript.test.ScriptRunner#evaluate(com.welland.javascript.test.ResultUtil, java.lang.Object)}.
     * @throws ScriptException 
     */
    @Test
    public void testSplitScript() throws ScriptException{
        ScriptRunner scriptRule = new ScriptRunner(compiledScript);
        ResultUtil utils = new ResultUtil();
        for (String string : input) {
            scriptRule.evaluate(utils, string);
            // Simulate real-world app that does something with the result
            utils.getResult();
        }
    }
    
    @Rule
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void succeeded(long nanos, Description description) {
            System.out.println(TimeUnit.MILLISECONDS.convert(nanos, TimeUnit.NANOSECONDS) + "ms " + description.getMethodName());
        }

    };  

}
