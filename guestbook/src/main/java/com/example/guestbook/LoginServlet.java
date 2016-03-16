package com.example.guestbook;

import org.openid4java.association.AssociationException;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageException;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Salman on 3/8/2016.
 */
public class LoginServlet extends HttpServlet {


    private ServletContext context;
    private ConsumerManager manager;
    private String userEmail = "";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        context = config.getServletContext();
        try {
            this.manager = new ConsumerManager();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Identifier identifier = this.verifyResponse(req);
        if (identifier == null) {
            throw new ServletException("No Open Id identifier provided");
        } else {
            resp.sendRedirect("/guestbook.jsp?useremail=" + userEmail+"&openid="+AppDirectApiConstants.APPDIRECT_ENDPOINT_OPENID+identifier.getIdentifier());
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String identifier = req.getParameter("identifier");
        try {
            this.authRequest(identifier, req, resp);
        } catch (DiscoveryException e) {
            e.printStackTrace();
        } catch (MessageException e) {
            e.printStackTrace();
        } catch (ConsumerException e) {
            e.printStackTrace();
        }
    }

    public void authRequest(String userSuppliedString, HttpServletRequest httpReq, HttpServletResponse httpResp) throws IOException, DiscoveryException, MessageException, ConsumerException {
        if(userSuppliedString == null) {
            userSuppliedString = AppDirectApiConstants.APPDIRECT_ENDPOINT_OPENID;
        }
        String returnToUrl = httpReq.getRequestURL().toString();
        List discoveries = manager.discover(userSuppliedString);
        DiscoveryInformation discovered = manager.associate(discoveries);
        httpReq.getSession().setAttribute("openid-disc", discovered);
        AuthRequest authReq = manager.authenticate(discovered, returnToUrl);
        FetchRequest fetch = FetchRequest.createFetchRequest();
        fetch.addAttribute("email","http://axschema.org/contact/email",true);
        authReq.addExtension(fetch);
        httpResp.sendRedirect(authReq.getDestinationUrl(true));
    }

    private Identifier verifyResponse(HttpServletRequest httpReq) {
        try {
            ParameterList response = new ParameterList(
                    httpReq.getParameterMap());
            DiscoveryInformation discovered = (DiscoveryInformation) httpReq
                    .getSession().getAttribute("openid-disc");
            StringBuffer receivingURL = httpReq.getRequestURL();
            String queryString = httpReq.getQueryString();
            if (queryString != null && queryString.length() > 0)
                receivingURL.append("?").append(httpReq.getQueryString());
            VerificationResult verification = manager.verify(
                    receivingURL.toString(), response, discovered);
            Identifier verified = verification.getVerifiedId();
            if (verified != null) {
                AuthSuccess authSuccess = (AuthSuccess) verification
                        .getAuthResponse();

                if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
                    FetchResponse fetchResp = (FetchResponse) authSuccess
                            .getExtension(AxMessage.OPENID_NS_AX);

                    List emails = fetchResp.getAttributeValues("email");
                    userEmail = (String) emails.get(0);
                }

                return verified; // success
            }
        } catch (AssociationException e) {
            e.printStackTrace();
        } catch (MessageException e) {
            e.printStackTrace();
        } catch (DiscoveryException e) {
            e.printStackTrace();
        }
        return null;
    }
}
