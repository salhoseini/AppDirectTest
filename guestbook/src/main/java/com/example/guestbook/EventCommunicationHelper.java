package com.example.guestbook;


/**
 * Created by Salman on 3/15/2016.
 */
public class EventCommunicationHelper {

    public static StringBuffer getSuccessResult(String userIdentifier) {
        StringBuffer sb=new StringBuffer();
        sb.append("<?xml version='1.0' encoding=\"UTF-8\" standalone='yes'?>");
        sb.append("<result>");
        sb.append("<success>");
        sb.append("true");
        sb.append("</success>");
        sb.append("<accountIdentifier>");
        sb.append(userIdentifier);
        sb.append("</accountIdentifier>");
        sb.append("</result>");
        return sb;
    }

    public static StringBuffer getFailureResult( AppDirectApiErrorCode code , String message) {
        StringBuffer sb=new StringBuffer();
        sb.append("<?xml version='1.0' encoding=\"UTF-8\" standalone='yes'?>");
        sb.append("<result>");
        sb.append("<success>");
        sb.append("false");
        sb.append("</success>");
        sb.append("<errorCode>");
        sb.append(code.toString());
        sb.append("</errorCode>");
        sb.append("<message>");
        sb.append(message);
        sb.append("</message>");
        sb.append("</result>");
        return sb;
    }

}
