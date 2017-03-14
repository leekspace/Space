package com.py4jdemo;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;

public class PythonRunner implements ApplicationListener<ApplicationContextEvent>{

    @Value("${python_home}")
    private String pythonHome;
    
    @Value("${python_project_path}")
    private String pythonProjectPath;
    
    @Value("${python_project_main}")
    private String pythonProjectMain;
    

    @Override
    public void onApplicationEvent(ApplicationContextEvent arg0) {
        
        start();
    }
    
    void start(){
        String args = pythonProjectPath +"\\" +pythonProjectMain;
        ProcessBuilder pb = new ProcessBuilder(pythonHome+"\\python.exe",args);
        File log = new File("log");
        pb.redirectErrorStream(true);
        pb.redirectOutput(Redirect.appendTo(log));
       
        try {
            Process p = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

