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
//            serverSocket.setSoTimeout(30000);

            LogManager.getLogger().info("Server is started on port " + port);


            Socket socket = serverSocket.accept();
            LogManager.getLogger().info("Client connected " + socket.getRemoteSocketAddress());

            Request request = new Request();
            Response response = new Response();

            SocketProcessor socketProcessor = new SocketProcessor(socket);


            /* start hello */
            socketProcessor.socketWriter(response.processingResponse("","LS|DA~YYMMDD~|TI~HHMMSS~|"));
            socketProcessor.socketWriter(response.processingResponse("","LA|DA~YYMMDD~|TI~HHMMSS~|"));



            String clientRequest = "";
            String matchedResponse;
            do {
                clientRequest = socketProcessor.socketListener();

                matchedResponse = request.processingRequest(clientRequest, stubMaps);
                if (! matchedResponse.equals(Constants.REQUEST_NOT_MATCHED)) {
                    if (! matchedResponse.equals(Constants.REQUEST_IGNORED)) {
                        socketProcessor.socketWriter(
                                response.processingResponse(
                                        clientRequest,
                                        matchedResponse
                                )
                        );
                    } else {
                        LogManager.getLogger().info("<< [IGNORED] " + clientRequest);
                    }
                }
                else {
                    LogManager.getLogger().info("Unrecognized request: " + clientRequest);
                }

            } while (! request.equals(Constants.CONNECTION_CLOSED));


            socket.close();
            serverSocket.close();
            LogManager.getLogger().warn("Server stopped");

        } catch (IOException e) {
            LogManager.getLogger().error("Unexpected server error");
            LogManager.getLogger().error(e.getMessage());
            e.printStackTrace();
        }

    }




}
