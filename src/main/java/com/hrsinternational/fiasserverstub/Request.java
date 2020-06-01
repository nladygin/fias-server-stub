package com.hrsinternational.fiasserverstub;

import java.util.Iterator;

public class Request {



    public String processingRequest(String request, StubMaps stubMaps) {
        Iterator<StubRecord> it = stubMaps.iterator();
        while(it.hasNext()){
            StubRecord stubRecord = it.next();
            if (request.matches(stubRecord.getRequest().pattern())) {
                if (stubRecord.getResponse().equals("~IGNORE~")) {
                    return Constants.REQUEST_IGNORED;
                } else {
                    return stubRecord.getResponse();
                }
            }
        }
        return Constants.REQUEST_NOT_MATCHED;
    }


}
