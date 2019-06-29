package BasicClasses;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.io.*;
import java.util.*;


public class Profile implements Serializable {

    private String userName, password;
    private String name;
    private String imageAddress;
    private int birthYear;


    @Override
    public int hashCode() {
        return userName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      if(obj == null) return false;
      try{
        return this.userName.equals(((Profile)obj).getUserName());
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
        return "[" + userName + ": " + name + " " + date + "]";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public int getAge(){
      return 2019 - birthYear;
    }
    public int isLegalAge(){
      return getAge() > 17;
    }
}
