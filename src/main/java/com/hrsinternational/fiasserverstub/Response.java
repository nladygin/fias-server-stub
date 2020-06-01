package com.hrsinternational.fiasserverstub;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Response {



    public String processingResponse(String rawResponse) {
        String response = rawResponse;
        if (response.contains("~IGNORE~")) return "IGNORE";

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

        return response;
    }





}
