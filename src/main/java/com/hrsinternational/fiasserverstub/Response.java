package com.hrsinternational.fiasserverstub;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Response {



    public String processingResponse(String request, String rawResponse) {
        String response = rawResponse;

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

        if (response.contains("~POSTING_SEQ~")) {
            response = response.replaceAll("~POSTING_SEQ~", getPostingSeq(request));
        }

        return response;
    }


    private String getPostingSeq(String request) {
        return StringUtils.substringBetween(request, "|P#", "|");
    }



}
