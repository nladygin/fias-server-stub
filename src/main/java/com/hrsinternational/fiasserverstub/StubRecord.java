package com.hrsinternational.fiasserverstub;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StubRecord {

//    private String request;
//    private Pattern requestRegEx;
    private Pattern request;
    private String response;




    public StubRecord(String request, String response) throws PatternSyntaxException {
        this.request = Pattern.compile(request, Pattern.CASE_INSENSITIVE);
        this.response = response;
    }





/*
    public StubRecord(String request, String response) throws PatternSyntaxException {
        this.request = request;
        this.response = response;
        this.requestRegEx = Pattern.compile(request, Pattern.CASE_INSENSITIVE);
    }
*/




/*
    public boolean loadRecord(String request, String response) {
        try {
            this.request = request;
            this.response = response;
            this.requestRegEx = Pattern.compile(request, Pattern.CASE_INSENSITIVE);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
*/





    public Pattern getRequest() {
        return request;
    }

    public String getResponse() {
        return response;
    }

}
