package com.hrsinternational.fiasserverstub;

import org.apache.logging.log4j.LogManager;

import javax.xml.soap.SOAPElementFactory;
import javax.xml.transform.Source;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Server {

    private Integer port;
    private StubMaps stubMaps;



    public Server(Integer port, StubMaps stubMaps) {
        this.port = port;
        this.stubMaps = stubMaps;
    }





    public void start() {

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            LogManager.getLogger().info("Server is listening on port " + port);

//            boolean connected = false;

            Socket socket = serverSocket.accept();

//            if (! connected) {
//                Response.sendReadySignal(socket);
//                connected = true;
//            }



//            while (true) {

do {
    Request request = new Request();
    request.processingRequest(socket, stubMaps);
} while (true);

/*
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                String text;

                do {
                    text = reader.readLine();
                    System.out.println(text);
                    String reverseText = new StringBuilder(text).reverse().toString();
                    writer.println("Server: " + reverseText);

                } while (!text.equals("bye"));

                socket.close();
*/

//            }

        } catch (IOException e) {
            LogManager.getLogger().error("Server error");
            LogManager.getLogger().error(e.getMessage());
            e.printStackTrace();
        }

    }















}
