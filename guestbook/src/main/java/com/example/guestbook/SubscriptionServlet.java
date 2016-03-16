package com.example.guestbook;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.signature.QueryStringSigningStrategy;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by Salman on 3/9/2016.
 */
public class SubscriptionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OAuthConsumer consumer = new DefaultOAuthConsumer(AppDirectApiConstants.OUATH_KEY,AppDirectApiConstants.OUATH_SECRET);
        consumer.setSigningStrategy( new QueryStringSigningStrategy());
        String event = req.getParameter("eventUrl");
        try {
            String signedUrl = consumer.sign(event);
            Map<String,String> subscriptionData = SubscriptionEventParser.parseXml(EventType.orderEvent , signedUrl);
            PlayerUtil util = new PlayerUtil();
            // create user and get accountIdentifier
            if(subscriptionData == null) {
                StringBuffer sb= EventCommunicationHelper.getFailureResult(AppDirectApiErrorCode.INVALID_RESPONSE, "could not register user");
                resp.setContentType("text/xml");
                PrintWriter out = resp.getWriter();
                out.println(sb.toString());
            } else {
                String identifier =  util.createPlayer(subscriptionData.get("email"), subscriptionData.get("firstName"), subscriptionData.get("lastName"),
                        subscriptionData.get("uuid"), subscriptionData.get("returnUrl"));
                if(identifier == null) {
                    StringBuffer sb= EventCommunicationHelper.getFailureResult(AppDirectApiErrorCode.USER_ALREADY_EXISTS, "user already registered");
                    resp.setContentType("text/xml");
                    PrintWriter out = resp.getWriter();
                    out.println(sb.toString());
                } else {
                    resp.setContentType("text/xml");
                    StringBuffer sb=new StringBuffer();
                    resp.sendRedirect(subscriptionData.get("returnUrl")+"&success=true&accountIdentifier="+identifier);
                }
            }


        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    }
}
