package com.example.guestbook;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Random;

/**
 * Created by Salman on 3/12/2016.
 */
@Entity
public class Player {

    @Id
    public String playerEmail;

    public String playerFirstName;
    public String playerLastName;
    public String playerUuId;
    public String playerReturnUrl;
    public String playerIdentifier;
    public Integer ticketNumber;

    public Player() {};

    public Player (String email , String firstName, String lastName, String uuid, String returnUrl,String identifier) {
        playerEmail = email;
        playerFirstName = firstName;
        playerLastName = lastName;
        playerUuId = uuid;
        playerReturnUrl = returnUrl;
        playerIdentifier = identifier;
        ticketNumber = 0;
    }

    private Integer generateTicket() {
        Random r = new Random();
        int Low = 1;
        int High = 100;
        int result = r.nextInt(High-Low) + Low;
        return result;
    }

    public String getPlayerEmail() {
        return playerEmail;
    }

    public void setPlayerEmail(String playerEmail) {
        this.playerEmail = playerEmail;
    }

    public String getPlayerFirstName() {
        return playerFirstName;
    }

    public void setPlayerFirstName(String playerFirstName) {
        this.playerFirstName = playerFirstName;
    }

    public String getPlayerLastName() {
        return playerLastName;
    }

    public void setPlayerLastName(String playerLastName) {
        this.playerLastName = playerLastName;
    }

    public String getPlayerUuId() {
        return playerUuId;
    }

    public void setPlayerUuId(String playerUuId) {
        this.playerUuId = playerUuId;
    }

    public String getPlayerReturnUrl() {
        return playerReturnUrl;
    }

    public void setPlayerReturnUrl(String playerReturnUrl) {
        this.playerReturnUrl = playerReturnUrl;
    }

    public String getPlayerIdentifier() {
        return playerIdentifier;
    }

    public void setPlayerIdentifier(String playerIdentifier) {
        this.playerIdentifier = playerIdentifier;
    }

    public Integer getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(Integer ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public void putTicketNumber() {
        this.ticketNumber = generateTicket();
    }
}
