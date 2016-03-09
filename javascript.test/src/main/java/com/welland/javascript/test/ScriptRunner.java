package com.welland.javascript.test;

import javax.script.CompiledScript;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

/**
 * Hello world!
 *
 */
public class ScriptRunner 
{
    private CompiledScript script;

    public ScriptRunner(CompiledScript script){
        this.script = script;
    }

    public Object evaluate(ResultUtil utils, Object input) throws ScriptException {
        Object scriptInput = input;
        if (input == null) {
            scriptInput = "";
        }
        SimpleBindings bindings = new SimpleBindings();
        bindings.put("input", scriptInput);
        bindings.put("utils", utils);
        Object result = script.eval(bindings);
        Object assignedResult = utils.getResult();
        if (assignedResult != null) {
            result = assignedResult;
        }
        if (result == null) {
            //The input value was not null, so don't let the post processing script
            //produce a null value.
            result = "";
        }
        bindings.clear();
        return result;
    }
    
}
