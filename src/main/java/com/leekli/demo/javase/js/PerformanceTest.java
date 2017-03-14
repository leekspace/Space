package com.leekli.demo.javase.js;

import java.util.concurrent.Executors;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class PerformanceTest {

    public static ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
    public static  Bindings bindings = null;
    public static String scriptStr = null;
    static{
        bindings = engine.createBindings();
        bindings.put("S3", new S3());
        // 注册上下文
        engine.setBindings(bindings, ScriptContext.GLOBAL_SCOPE);
        // 加载脚本
        scriptStr = "function onEvent(a1, a2){"
                + "     S3.count(a1,a2);"
                + "  } "
                + "  function innerFunc() {"
                + "   }";
    }
    public static void main(String[] args) throws Exception {
        
        invocableTest();
 
        
    }


    public static void  invocableTest() throws ScriptException, NoSuchMethodException{
 
        engine.eval(scriptStr);
        final  Invocable inv = (Invocable) engine;
        
        Long startAt = System.currentTimeMillis();
        for(int i=0;i<100000;i++){
            final int a1  = i;
            final int a2 = a1+1;
            Executors.newSingleThreadExecutor().submit( new Runnable() {
                @Override
                public void run() {
                   
                    try {
                        Object a = inv.invokeFunction("onEvent", a1, a2);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    } 
                }
            });
               
        }
        System.out.println(System.currentTimeMillis() - startAt);
        
        
    }
    public static void  compileTest() throws ScriptException{
         
        // 加载脚本
        Object func = engine.eval(scriptStr);
        
        // 预编译调用函数
        Compilable c = (Compilable)engine;
        CompiledScript script = c.compile("onEvent(name, src)");
        
        Bindings thisScope = engine.getBindings(ScriptContext.ENGINE_SCOPE);
        thisScope.put("name", "sessionAdd");
        thisScope.put("src", "...");
        
        
        Long startAt = System.currentTimeMillis();
        for(int i=0;i<100000;i++){
            script.eval(thisScope);    
        }
        System.out.println(System.currentTimeMillis() - startAt);
        
    }
}

