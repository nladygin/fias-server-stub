package com.hrsinternational.fiasserverstub;

import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Response {

    private Socket socket;
    private StubRecord record;

    private static String START_SIGN = "\u0002";
    private static String END_SIGN = "\u0003";




    public Response(Socket socket, StubRecord record) {
        this.socket = socket;
        this.record = record;
    }



    public void makeResponse() throws IOException {

        String response = record.getResponse();
        if (response.contains("~YYMMDD~")) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMdd");
            LocalDate now = LocalDate.now();
            response = response.replaceAll("~YYMMDD~", dtf.format(now));
        }

        if (response.contains("~HHMMSS~")) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmss");
            LocalDateTime now = LocalDateTime.now();
            response = response.replaceAll("~HHMMSS~", dtf.format(now));
        }

        sendResponse(response);
    }



    public void sendResponse(String response) throws IOException {
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println(START_SIGN + response + END_SIGN);
        LogManager.getLogger().info("<< " + response);
    }



    public static void sendReadySignal(Socket socket) throws IOException {
        DateTimeFormatter dateFormater = DateTimeFormatter.ofPattern("yyMMdd");
            LocalDate date = LocalDate.now();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
            LocalDateTime time = LocalDateTime.now();

        String START_RESPONSE = "LS|DA" + dateFormater.format(date) + "|TI" + timeFormatter.format(time) + "|";

        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println(START_SIGN + START_RESPONSE + END_SIGN);
        LogManager.getLogger().info("<< " + START_RESPONSE);
    }
}
