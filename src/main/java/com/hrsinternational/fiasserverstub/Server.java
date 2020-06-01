package com.hrsinternational.fiasserverstub;

import org.apache.logging.log4j.LogManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private Integer port;
    private StubMaps stubMaps;



    public Server(Integer port, StubMaps stubMaps) {
        this.port = port;
        this.stubMaps = stubMaps;
    }





    public void start() {


        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(30000);

            LogManager.getLogger().info("Server is listening on port " + port);


            Socket socket = serverSocket.accept();
            LogManager.getLogger().info("Client connected " + socket.getRemoteSocketAddress());

            Request request = new Request();
            Response response = new Response();

            SocketProcessor socketProcessor = new SocketProcessor(socket);


            /* start hello */
            socketProcessor.socketWriter(response.processingResponse("LS|DA~YYMMDD~|TI~HHMMSS~|"));
            socketProcessor.socketWriter(response.processingResponse("LA|DA~YYMMDD~|TI~HHMMSS~|"));



            String clientRequest = "";
            String matchedResponse;
            do {
                clientRequest = socketProcessor.socketListener();

                matchedResponse = request.processingRequest(clientRequest, stubMaps);
                if (! matchedResponse.equals("NOT_MATCHED")) {
                    socketProcessor.socketWriter(response.processingResponse(matchedResponse));
                } else if (matchedResponse.equals("IGNORE")) {
                    LogManager.getLogger().info("<< [IGNORED] " + clientRequest);
                } else {
                        LogManager.getLogger().info("Unrecognized request: " + clientRequest);
                }

            } while (! request.equals("EXIT"));



            socket.close();
            serverSocket.close();
            LogManager.getLogger().warn("Server stopped");




/*
            final byte STX = 0x02;
            final byte ETX = 0x03;
            final byte[] buffer = new byte[2000];
            int recordLengthLimit = 2000;

            InputStream inputStream = socket.getInputStream();
            int lastRedValue;
            int redDataLength = 0;
            boolean dataStarted = false;
            do {
                lastRedValue = inputStream.read();
                if (lastRedValue == -1) {
                    socket.close();
                    serverSocket.close();
                    LogManager.getLogger().warn("Server stopped");
                    System.exit(0);
                } else if (lastRedValue == STX) {
                    dataStarted = true;
                } else if (lastRedValue == ETX) {
                    byte[] result = new byte[redDataLength];
                    System.arraycopy(buffer, 0, result, 0, redDataLength);
                    LogManager.getLogger().info(">> " + new String(result));
                    redDataLength = 0;
                    dataStarted = false;
                } else if (dataStarted) {
                    buffer[redDataLength++] = (byte) lastRedValue;
                }
                if (redDataLength > recordLengthLimit) {
                    LogManager.getLogger().warn("Long request");
                }
            } while (true);
*/



/*
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            DataInputStream input = new DataInputStream(socket.getInputStream());
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            Request request = new Request(input);
            Response response = new Response(output);

            response.processingResponse(new StubRecord("","LS|DA~YYMMDD~|TI~HHMMSS~|"));
            response.processingResponse(new StubRecord("","LA|DA~YYMMDD~|TI~HHMMSS~|"));

            String line;
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }

            while (true) {
                StubRecord record = request.processingRequest(stubMaps);

                if (record != null) {
                    response.processingResponse(record);
                } else {
                    LogManager.getLogger().warn("Unrecognized request: " + request);
                }

            }
*/

        } catch (IOException e) {
            LogManager.getLogger().error("Server error");
            LogManager.getLogger().error(e.getMessage());
            e.printStackTrace();
        }

    }















}
