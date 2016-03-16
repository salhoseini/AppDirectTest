package com.example.guestbook;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;

/**
 * Created by Salman on 3/12/2016.
 */
public class SubscriptionEventParser {

    public static Map<String,String> parseXml(EventType type ,String uri) throws Exception {
        Map<String,String> result = new HashMap<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            SubscriptionParseHandler handler = new SubscriptionParseHandler(result);
            saxParser.parse(uri,handler);
            result = handler.getElementInfo();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
