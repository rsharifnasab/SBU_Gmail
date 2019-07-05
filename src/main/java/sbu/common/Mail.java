package sbu.common;

import java.io.*;
import java.util.*;

import java.time.*;

/**
Mail class is the mail that send and recieve to and from Server
it save sender and reciever by its username (string)
is saves date created in long for equals method
subject and message are absoloutely String :)
it also save read unread state and trashed state with boolan
**/
public class Mail implements Serializable , Comparable {

  private final String sender;
  private final String reciever;
  private final String message;
  private final String subject;
  private final Long createdTime;
  private final String timeString;
  public Object attach = null;
  public boolean trashed = false;
  public boolean unread = true;

  /**
  defualt and only constructor,
  it gets final variable: sender, reciever, message and subject
  it also calculate createation time for future use
  **/
  public Mail(String sender,String reciever,String subject, String message){
    this.sender = sender;
    this.reciever = reciever;
    this.subject = subject;
    this.message = message;
    createdTime = Time.getMilli();
    timeString = Time.getTime();
  }

  /**
  override hashcode method with returning username hash code (we know its unique :) )
  **/
  @Override
  public int hashCode() {
    return createdTime.hashCode();
  }

  /**
  chech equality of two Mail
  also checking if it is not instance of mail
  it use creatation time and all other final fields to make sure two mail are equal or not
  **/
  @Override
  public boolean equals(Object obj) {
    if(obj == null) return false;
    if (! (obj instanceof Mail) ) return false;

    Mail other = (Mail) obj;

    return this.createdTime.equals(other.createdTime);
  }


  /**
  comparing two mail for display them in correct way
  it first compare by read and unread status and then compare with cretation time
  **/
  @Override
  public int compareTo(Object o) { // i dont even use this :)
    if (o instanceof Mail == false) return -1;
    Mail other = (Mail) o;
    if(this.isUnRead() &&  !other.isUnRead()) return -1;
    if(!this.isUnRead() &&  other.isUnRead()) return +1;
    return createdTime.compareTo( other.createdTime );
  }

  /**
    override to string method: is is used for showing mail is ListView
    if mail is unread it add * before sender
  **/
  @Override
  public String toString() {
    String unreadStatus = unread?"* ":"  ";
    return unreadStatus + "from " + sender + "\n subject : " + subject ;
  }

  /**
    make mail as read by making unread boolan as false
  **/
  public void read(){
    unread = false;
  }

  /**
    get unread status (boolean)
  **/
  public boolean isUnRead(){
    return unread;
  }

  /**
    get trashed status (boolan)
  **/
  public boolean isTrashed(){
    return trashed;
  }

  /**
    change trash status of mail
    if it is in trash send it out and if it is in mail box, trash it
  **/
  public void trash(){
    trashed = !trashed;
  }

  /**
    getter for sender field
  **/
  public String getSender() {
    return sender;
  }

  /**
    getter for reciever field
  **/
  public String getReciever(){
    return reciever;
  }

  /**
    getter for subject field
  **/
  public String getSubject() {
    return subject;
  }

  /**
    getter for message field
  **/
  public String getMessage(){
    return message;
  }

  /**
    get mail cretaiton time in string format
  **/
  public String getTimeString(){
    return timeString;
  }


  /**
    get mail cretaiton time in long inteer format
    it calculted milliseconds from 1970/1/1 to now
  **/
  public long getTimeLong(){
    return createdTime;
  }

}
