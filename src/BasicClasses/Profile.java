package BasicClasses;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.image.*;

import java.io.*;
import java.util.*;


public class Profile implements Serializable {

    private final String username;
    private String password;
    private String name;
    private Image image;
    private String birthYear;
    private String phoneNumber;
    private Gender gender;


    public Profile(String username){
      this.username = username;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

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

    public HBox getProfileTile() {
        HBox proTile = new HBox();
        Label proName=new Label(this.getUserName());
        proName.setFont( new Font( 10) );

        proTile.getChildren().addAll(proName);
        proTile.setSpacing(30);

        proTile.setAlignment(Pos.CENTER);
        return proTile;
    }


    @Override
    public String toString() {
        return "[" + username + ": " + name + " " + birthYear + "]";
    }

    public String getUserName() {
        return username;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Boolean setBirthYear(String birthYear){
      if( ! isValidBirthYear(birthYear) ) return false;
      this.birthYear = birthYear;
      return true;
    }

    public int getBirthYear(){
      return Integer.parseInt(birthYear);
    }

    public int getAge(){
      return 2019 - getBirthYear();
    }

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

    public Profile authenticate(String username,String password){
      if(this.username.equals(username) && this.password.equals(password)) return this;
      return null;
    }

}
