package com.example.guestbook;

import com.googlecode.objectify.ObjectifyService;

import java.util.List;

/**
 * Created by Salman on 3/12/2016.
 */
public class PlayerUtil {

    public String createPlayer(String email , String firstName, String lastName, String uuid, String returnUrl) {
        List<Player> players = ObjectifyService.ofy().load().type(Player.class).list();
        for(Player player : players) {
            if(email.contains(player.getPlayerEmail())) {
                return null;
            }
        }

        String identifier = email+"-"+uuid;
        Player newPlayer = new Player(email,firstName,lastName,uuid,returnUrl,identifier);
        newPlayer.putTicketNumber();
        ObjectifyService.ofy().save().entity(newPlayer).now();
        return identifier;
    }

    public String deletePlayer(String email) {
        List<Player> player = ObjectifyService.ofy().load().type(Player.class).filter("playerEmail" , email).list();
        if(player == null) {
            return null;
        } else if(player.size() < 1) {
            return null;
        } else if(player.size() > 1) {
            return null;
        }
        String userIdentifier = player.get(0).getPlayerIdentifier();
        ObjectifyService.ofy().delete().entity(player);
        return userIdentifier;
    }


}
