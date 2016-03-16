package com.example.guestbook;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.signature.QueryStringSigningStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by Salman on 3/12/2016.
 */
public class FinishSubscriptionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        OAuthConsumer consumer = new DefaultOAuthConsumer(AppDirectApiConstants.OUATH_KEY,AppDirectApiConstants.OUATH_SECRET);
        consumer.setSigningStrategy( new QueryStringSigningStrategy());
        String event = req.getParameter("eventUrl"); // signed version
        PlayerUtil util = new PlayerUtil();
        try {
            String signedUrl = consumer.sign(event); // must verify signature
            Map<String,String> subscriptionData = SubscriptionEventParser.parseXml(EventType.cancelEvent , signedUrl); // currently not working

            resp.setContentType("text/xml");
            if(subscriptionData == null) {
                StringBuffer sb= EventCommunicationHelper.getFailureResult(AppDirectApiErrorCode.INVALID_RESPONSE, "Could not delete user from system");
                PrintWriter out = resp.getWriter();
                out.println(sb.toString());
            } else if (subscriptionData.get("email") == null) { // could not read email from event data
                StringBuffer sb= EventCommunicationHelper.getFailureResult(AppDirectApiErrorCode.INVALID_RESPONSE, "Could not delete user from system");
                resp.setContentType("text/xml");
                PrintWriter out = resp.getWriter();
                out.println(sb.toString());
            }
            else {
                String userIdentifier = util.deletePlayer(subscriptionData.get("email"));
                if(userIdentifier == null) {
                    StringBuffer sb= EventCommunicationHelper.getFailureResult(AppDirectApiErrorCode.USER_NOT_FOUND, "Could not delete user from system");
                    PrintWriter out = resp.getWriter();
                    out.println(sb.toString());

                } else {
                    StringBuffer sb= EventCommunicationHelper.getSuccessResult(userIdentifier);
                    PrintWriter out = resp.getWriter();
                    out.println(sb.toString());
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
