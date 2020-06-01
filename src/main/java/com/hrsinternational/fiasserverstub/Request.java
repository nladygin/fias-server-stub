package com.hrsinternational.fiasserverstub;

import org.apache.logging.log4j.LogManager;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Request {



    public String cleanUp(String request) {
        return request.substring(1, request.length()-1);
    }



    public void processingRequest(Socket socket, StubMaps stubMaps) throws IOException {
//        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            String request = cleanUp( new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine() );
/*
        String request = cleanUp(
                new BufferedReader(
                        new InputStreamReader(
                                input,
                                StandardCharsets.UTF_8)
                )
                        .lines()
                        .collect(Collectors.joining())
        );
*/
        LogManager.getLogger().info(">> " + request);

        StubRecord record = matchRequest(request, stubMaps);

        if (record != null) {
            Response response = new Response(socket, record);
                response.makeResponse();
        } else {
            LogManager.getLogger().warn("Unrecognized request: " + request);
        }

    }




    private StubRecord matchRequest(String request, StubMaps stubMaps) {
        Iterator<StubRecord> it = stubMaps.iterator();
        while(it.hasNext()){
            StubRecord stubRecord = it.next();
            if (request.matches(stubRecord.getRequest().pattern())) {
                return stubRecord;
            }
/*
            if (stubRecord
                    .getRequest()
                    .matcher(request)
                    .find()
            ) {
//                LogManager.getLogger().info("Matched: " + request + " ~ " + stubRecord.getResponse());
                return stubRecord;
            }
*/
        }
        return null;
    }


}
