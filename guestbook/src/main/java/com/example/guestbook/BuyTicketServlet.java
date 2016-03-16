package com.example.guestbook;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Salman on 3/5/2016.
 */
public class BuyTicketServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("userEmail");
        List<Player> players = ObjectifyService.ofy().load().type(Player.class).list();
        Player target = null;
        for(Player player : players) {
            if(email.contains(player.getPlayerEmail())) {
                email.replaceAll("/", "");
                player.setPlayerEmail(email);
                target = player;
                break;
            }
        }
        if (target == null) {
            throw new IOException("could not find user from db");
        } else {
            target.putTicketNumber();
            ObjectifyService.ofy().save().entity(target).now();
            resp.sendRedirect("/guestbook.jsp?useremail="+email+"&openid="+AppDirectApiConstants.APPDIRECT_ENDPOINT_OPENID+target.getPlayerUuId());
        }

    }
}
