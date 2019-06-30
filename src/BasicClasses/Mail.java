package BasicClasses;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.io.*;
import java.util.*;

import java.time.*;


public class Mail implements Serializable , Comparable {

    private final String sender;
    private final String reciever;
    private final String message;
    private final String subject;
    private final Long createdTime;
    public Object attach = null;

  public Mail(String sender,String reciever,String subject, String message){
    this.sender = sender;
    this.reciever = reciever;
    this.subject = subject;
    this.message = message;
    createdTime = Time.getMilli();
  }

    @Override
    public int hashCode() {
        return createdTime.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      if(obj == null) return false;
      if (! (obj instanceof Mail) ) return false;

      Mail other = (Mail) obj;

      try{
        String thisToCheck = this.createdTime + this.sender + this.reciever + this.subject + this.message;
        String otherToCheck = other.createdTime + other.sender + other.reciever + other.subject + other.message;
        return thisToCheck.equals(otherToCheck);
      }
      catch(Exception e){
        return false;
      }
    }

    public HBox getMailTile() { //TODO

        HBox mailTile = new HBox();
        Label senderLabel = new Label(this.sender);
        Label recieverLabel = new Label(this.reciever);
        Label subjectLabel = new Label(this.subject);

        senderLabel.setFont( new Font( 10) );
        recieverLabel.setFont (new Font(10) );
        subjectLabel.setFont( new Font(10) );

        mailTile.getChildren().addAll(senderLabel,recieverLabel,subjectLabel);
        mailTile.setSpacing(30);
        mailTile.setAlignment(Pos.CENTER);

        return mailTile;
    }

  @Override
  public int compareTo(Object o) {
    if (o instanceof Mail)
      return createdTime.compareTo( ((Mail)o).createdTime);
    return -1;
  }


    @Override
    public String toString() {
        return "[ from" + sender + ", to: " + reciever + ", subject : " + subject + ", message : " + message + "]";
    }

    public String getSender() {
        return sender;
    }

    public String getReciever(){
      return reciever;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage(){
      return subject;
    }

}
