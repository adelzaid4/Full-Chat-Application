/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.scene.paint.Color;

/**
 *
 * @author adelz
 */
public class Message implements Serializable{
    private User sender;
    private User reciever;
    private LocalDateTime date;
    private String body;
    private Style style;
    private boolean me;

    public boolean isMe() {
        return me;
    }

    public void setMe(boolean me) {
        this.me = me;
    }
    public User getReciever() {
        return reciever;
    }

    public void setReciever(User reciever) {
        this.reciever = reciever;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }


    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    
    
}
