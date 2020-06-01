package com.hrsinternational.fiasserverstub;

import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketProcessor {

    private final byte STX = 0x02;
    private final byte ETX = 0x03;
    private final byte[] buffer = new byte[2000];
    private int recordLengthLimit = 2000;
    private Socket socket;



    public SocketProcessor(Socket socket) {
        this.socket = socket;
    }



    public String socketListener() throws IOException {
        InputStream inputStream = socket.getInputStream();
        int lastRedValue;
        int redDataLength = 0;
        boolean dataStarted = false;
        do {
            lastRedValue = inputStream.read();
            if (lastRedValue == -1) {
                return Constants.CONNECTION_CLOSED;
            } else if (lastRedValue == STX) {
                dataStarted = true;
            } else if (lastRedValue == ETX) {
                byte[] result = new byte[redDataLength];
                System.arraycopy(buffer, 0, result, 0, redDataLength);
                String request = new String(result);
                LogManager.getLogger().info(">> " + request);
                return request;
            } else if (dataStarted) {
                buffer[redDataLength++] = (byte) lastRedValue;
            }
            if (redDataLength > recordLengthLimit) {
                LogManager.getLogger().warn("Request is longer than 2000 bytes");
            }
        } while (true);
    }




    public void socketWriter(String response) throws IOException {
        byte[] record = response.getBytes();
        byte[] framedData = new byte[record.length + 2];
            framedData[0] = STX;
            System.arraycopy(record, 0, framedData, 1, record.length);
            framedData[framedData.length - 1] = ETX;
                OutputStream out = socket.getOutputStream();
                out.write(framedData);
        LogManager.getLogger().info("<< " + response);
    }




}
