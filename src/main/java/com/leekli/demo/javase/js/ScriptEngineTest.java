package com.leekli.demo.javase.js;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import com.google.common.collect.Lists;

public class ScriptEngineTest {
    public static void main(String[] args) throws Exception {
        //test3();
        List<String> list = Lists.newArrayList("2", "aaaa");
        
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine2 = manager.getEngineByName("javascript");
        Bindings bindings = engine2.createBindings();
        engine2.eval(new FileReader("d:\\test\\s2.txt"));
        //engine2.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
        bindings.put("a", "a");
        Invocable inv = (Invocable) engine2;
        Object a = inv.invokeFunction("deviceOnline", "TOKEN:XXXX", list);

    }

    public static String test3() throws Exception{
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine2 = manager.getEngineByName("javascript");
        Reader reader = new FileReader("d:\\test\\s2.txt"); 
        //engine2.eval(new FileReader("d:\\test\\s2.txt")); 
        if (engine2 instanceof Compilable){
            Compilable compEngine = (Compilable)engine2;   
            try{
                CompiledScript script = compEngine.compile(reader);
                script.eval();
                
                
            }catch (ScriptException e){
                e.printStackTrace();
            }
        }else{
            System.out.println("不支持编译");
        }
 
        
        
        
        
        return "";
        
    }

    public static String test2() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine2 = manager.getEngineByName("js");
        engine2.eval(new FileReader("d:\\test\\script.txt"));
        Invocable inv = (Invocable) engine2;
        Object a = inv.invokeFunction("getDate");
        return "a";

    }

    public static void test1() throws Exception {

        ScriptEngineManager manager = new ScriptEngineManager();

        ScriptEngine engine = manager.getEngineByName("javascript");
        Compilable compilable = (Compilable) engine;
        List<String> list = Lists.newArrayList("2", "aaaa");

        Reader reader = new FileReader("d:\\test\\script.txt");
        // Bindings bindings = engine.createBindings();
        CompiledScript JSFunction = compilable.compile(reader);
        // bindings.put("c", 1);
        // bindings.put("d", 2);

        ScriptContext context = new SimpleScriptContext();
        context.setReader(reader);
        context.setAttribute("a", 123, ScriptContext.ENGINE_SCOPE);
        context.setAttribute("b", 100, ScriptContext.ENGINE_SCOPE);

        Object result = JSFunction.eval(context);
        System.out.println(result);

        ScriptEngine engine2 = manager.getEngineByName("javascript");
        engine2.eval(new FileReader("d:\\test\\s2.txt"));
        Invocable inv = (Invocable) engine2;
        Object a = inv.invokeFunction("deviceOnline", "TOKEN:XXXX", list);
    }

}
