package com.leekli.demo.javase.js;

//引入System有所有静态成员 ，下面可以直接使用out
import static java.lang.System.out;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JavaScriptEngineDemo {
    public static void main(String[] args) {
        ScriptEngine engine = getEngine();
        evalScript(engine);
        callScript(engine);
        callScriptFile(engine);
        
        
    }
    
    
    
    //执行脚本文件中的函数
    public static void callScriptFile(ScriptEngine engine){
        try {
            Reader reader = new FileReader("src\\main\\java\\com\\leekli\\demo\\javase\\js\\script.txt");
            engine.eval(reader);
            //全局变量
            engine.put("global_1", "100");
            engine.put("global_2", "200");
            Invocable inv = (Invocable) engine;
            Object a = inv.invokeFunction("showHello");
            System.out.println((String)a);
            
            System.out.println("=======返回java类型===============");
            Object array = inv.invokeFunction("getArrays");
            List<String> list = (List<String>)array;
            System.out.println(list);
            
            System.out.println("===========函数传参数===========");
            Object token = inv.invokeFunction("getToken","TOKEN:xxxxx",100);
            System.out.println(token);
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
    
    //和脚本语言进行交互
    public static void callScript(ScriptEngine engine){
        engine.put("name", "abcdefg");//向脚本传递变量name
        try {
            //使用变量name
            engine.eval("var output =''; for(i = 0;i<=name.length;i++) { output = name.charAt(i) + output };");
            
            //得到脚本中的变量output的值
            String name = (String)engine.get("output");
            out.printf("被翻转后的字符串：%s", name);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
    
    
    
    //执行简单脚本
    public static void evalScript(ScriptEngine engine){
        try {
            //直接返回最后的结果，这里返回8，这种返回方式是隐式的
            Double hours = (Double) engine.eval("var date = new Date();" +"date.getHours();8;");
            System.out.println("Hour:"+hours);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
    //得到脚本引擎
    public static ScriptEngine getEngine(){
        
        ScriptEngineManager manager = new ScriptEngineManager();
       
        //支持的所有脚本工厂
        List<ScriptEngineFactory> factories = manager.getEngineFactories();
        for (ScriptEngineFactory factory: factories){
            out.println("EngineName:"+factory.getEngineName());
            out.println("LanguageName:"+factory.getLanguageName());
            out.println("MimeTypes:"+factory.getMimeTypes());
            out.println("Names:"+factory.getNames());   //脚本的别名
        }
        
        //根据扩展名得到脚本引擎
        ScriptEngine engine = manager.getEngineByExtension("js");
        //根据名称得到脚本引擎
        ScriptEngine engine2 = manager.getEngineByName("javascript");
        //根据Mime类型得到脚本引擎
        ScriptEngine engine3 = manager.getEngineByMimeType("text/javascript");
        
        return engine3;
        
    }
}


