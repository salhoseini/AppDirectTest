package com.example.guestbook;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Salman on 3/12/2016.
 */
public class SubscriptionParseHandler extends DefaultHandler{
    private boolean inCreator = false;
    private boolean inEmail = false;
    private boolean inFirstName = false;
    private boolean inLastName = false;
    private boolean inOpenId = false;
    private boolean inUuId = false;
    private boolean inReturnUrl = false;

    private Map<String,String> elementInfo;

    public SubscriptionParseHandler(Map<String,String> resultMap) {
        elementInfo = resultMap;
    }

    @Override
    public void startElement(String uri, String localName,String qName,
                             Attributes attributes) throws SAXException {
        if(qName.equalsIgnoreCase("creator")) {
            inCreator = true;
        } else if(qName.equalsIgnoreCase("email")) {
            inEmail = true;
        } else if(qName.equalsIgnoreCase("firstName")) {
            inFirstName = true;
        } else if(qName.equalsIgnoreCase("lastName")) {
            inLastName = true;
        } else if(qName.equalsIgnoreCase("openId")) {
            inOpenId = true;
        } else if(qName.equalsIgnoreCase("uuid")) {
            inUuId = true;
        } else if(qName.equalsIgnoreCase("returnUrl")) {
            inReturnUrl = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        String value = new String(ch,start,length);
        if(inCreator) {
            if(inEmail) {
                elementInfo.put("email" , value);
                inEmail = false;
            } else if(inFirstName) {
                elementInfo.put("firstName" , value);
                inFirstName = false;
            } else if(inLastName) {
                elementInfo.put("lastName" , value);
                inLastName = false;
            } else if(inOpenId) {
                elementInfo.put("openId" , value);
                inOpenId = false;
            } else if(inUuId) {
                elementInfo.put("uuid" , value);
                inUuId = false;
            }
        }
        if(inReturnUrl) {
            elementInfo.put("returnUrl" , value);
            inReturnUrl = false;
        }
    }

    public Map<String,String> getElementInfo () {
        return elementInfo;
    }
}
