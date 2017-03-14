package com.py4jdemo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.support.ManagedArray;
import org.springframework.stereotype.Component;
@Component(value="endpoint")
public class EndpointImpl implements Endpoint{

    
    private ByteBuffer buffer  = ByteBuffer.allocate(2400000);


    @Override
    public void putImage(byte[] bytes) {
        buffer.clear();
        buffer.put(bytes);
        buffer.flip();
    }

    
    @Override
    public byte[] getImage() {
        try {
            buffer.rewind();
            byte[] dst = new byte[buffer.limit()];
            buffer.get(dst);
            InputStream in = new ByteArrayInputStream(dst);
            in.read(dst);
            in.close();
            return dst;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}

