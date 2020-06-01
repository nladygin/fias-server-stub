package com.hrsinternational.fiasserverstub;

import org.apache.logging.log4j.LogManager;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Request {





/*
    public String cleanUp(String request) {
        System.out.println(request);
        return request.substring(1, request.length()-1);
    }




    public StubRecord processingRequest(StubMaps stubMaps) throws IOException {
//        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        String request = input.readLine();
//        String request = input.readUTF();
        LogManager.getLogger().info(">>>> " + request);

        if (request != null) {
            request = cleanUp(request);

            LogManager.getLogger().info(">> " + request);

            return matchRequest(request, stubMaps);
        } else {
            return null;
        }
    }
*/



    public String processingRequest(String request, StubMaps stubMaps) {
        Iterator<StubRecord> it = stubMaps.iterator();
        while(it.hasNext()){
            StubRecord stubRecord = it.next();
            if (request.matches(stubRecord.getRequest().pattern())) {
                return stubRecord.getResponse();
            }
        }
        return "NOT_MATCHED";
    }


}
