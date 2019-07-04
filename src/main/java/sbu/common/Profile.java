package sbu.common;

import java.io.*;
import java.util.*;

/**
  profile of every user keep all of his/her info
  eveything is customizable except username (thats final)
  profile also use as a token for authenticating and getting mail from Server
**/
public class Profile implements Serializable {

    private final String username;
    private String password;
    private String name;
    private String birthYear;
    private String phoneNumber;
    private Gender gender;
    public final static String POST_FIX = "@gmail.com";

    /**
      constructor: get username and set it (for ever)
      other field can be set later
      is clean username before saving (remove eveything after @)
    **/
    public Profile(String username){
      this.username = usernameCleaner(username);
    }

    /**
      an static method for getting username from whole mail
      for example it chang : rsharifnasab@gmail.com -> rsharifnasab
    **/
    public static String usernameCleaner(String oldUsername){
      String[] splited = oldUsername.split("@");
      return splited[0];
    }

    /**
      override hashcode of profile,
      it use username beacuse its final
    **/
    @Override
    public int hashCode() {
        return username.hashCode();
    }

    /**
      check equality of profiles by cheking username
      note that if profile change, its still return eqaul because  same username
    **/
    @Override
    public boolean equals(Object obj) {
      if(obj == null) return false;
      try{
        return this.username.equals(((Profile)obj).getUserName());
      }
      catch(Exception e){
        return false;
      }
    }

    /**
      override toString method
      it uses mostly for debug (printing a profile)
    **/
    @Override
    public String toString() {
        return "[" + username + ": " + name + " " + birthYear + "]";
    }


    public String getUserName() {
        return username;
    }

    /**
      get full username of user (with @gmail.com)
    **/
    public String getFullUserName() {
        return username+POST_FIX;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public void setGender(Gender gender){
      this.gender = gender;
    }

    public Gender getGender(){
      return gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
      set birthyear from a string and set it
      it also check validness of birthyear
    **/
    public Boolean setBirthYear(String birthYear){
      if( ! isValidBirthYear(birthYear) ) return false;
      this.birthYear = birthYear;
      return true;
    }

    public int getBirthYear(){
      return Integer.parseInt(birthYear);
    }

    /**
      calculate ago of user very exactly :)
    **/
    public int getAge(){
      return 2019 - getBirthYear();
    }
    /**
      check if a birthyear (in string format) valid or Not
      first of all it checks it is valid integer
      then it checks year range (it should be from 1800 to 2019)
    **/
    public static boolean isValidBirthYear(String yearStr){
      try{
        int yearInt = Integer.parseInt(yearStr);
        if (yearInt > 2019 || yearInt < 1800 ) return false;
        return true;
      }
      catch(RuntimeException e){
          return false;
      }
    }

    /**
      authenticate another username and password with this profile (NOTE THAT ITS NOT STATIC)
      if username and passwrod math return this profile (we now this profile is a kind of token)
      if they aret match in return NULL
      be carefull of null pointer exceptions
    **/
    public Profile authenticate(String username,String password){
      if(this.username.equals(username) && this.password.equals(password)) return this;
      return null;
    }

}
