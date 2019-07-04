package sbu.clientAPP;

import sbu.common.*;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.application.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

/**
	it is mail application of clientside
	it extends application because it is a javafx program
	it has exe in name because the executable class that user should run
**/
public class ClientEXE extends Application {

	/**
		program current stage
	**/
	public static Stage pStage;

	/**
		current user profile,
		it is null until user wignup or login
	**/
	public static Profile profile;

	/**
		a mail template used for composing mail in case of reply or forward
	**/
	public static Mail composeTemplete = null;

	/**
		a string to keep which text is searching
	**/
	public static String searchText = "";

	/**
		it saves where aer we in diferent mail folders
	**/
	public static MailFolder mailFolder = MailFolder.INBOX;

	/**
		different lists of mail
		note that outbox is always empty
	**/
	public static List<Mail> outbox = new CopyOnWriteArrayList<>();
	public static List<Mail> inbox = new CopyOnWriteArrayList<>();
	public static List<Mail> sent = new CopyOnWriteArrayList<>();
	public static List<Mail> trash = new CopyOnWriteArrayList<>();

	/**
		it return list of mail that we have to show to user
		it uses mailfolder to specify which mail folder we should return
		it doesnt sort itself, sort will be implemented in server side api
	**/
	public static List<Mail> getMailsToShow(){
		switch(mailFolder){
			case INBOX:
				return inbox;
			case SENT:
				return sent;
			case OUTBOX:
				return outbox;
			case TRASH:
				return trash;
			case SEARCH:
				return creteSearchedList();
		}
		return new CopyOnWriteArrayList<Mail>();
	}

	public static List<Mail> creteSearchedList(){
		List<Mail> result = new CopyOnWriteArrayList<>();
		getAllMail()
			.stream()
			.filter( a->
			    a.getSender().contains(searchText) ||
					a.getReciever().contains(searchText) ||
					a.getSubject().contains(searchText) ||
					a.getMessage().contains(searchText)
			)

			.collect(Collectors.toCollection( () -> result));
		return result;
	}

	/**
		a mthod that concat all lists of mail
	**/
	public static List<Mail> getAllMail(){
		List<Mail> all = new CopyOnWriteArrayList<>();
		all.addAll(inbox);
		all.addAll(sent);
		all.addAll(trash);
		all.addAll(outbox);
		return all;
	}

	/**
		dont waste your time understanding this Method
		this method will give a mail String and search in all mails and return the Mail object that its toString equals the unput string
		@deprecated
	**/
	public static Mail findMailByString(String str){
		try{
			Mail mail = getAllMail()
			.stream()
			.filter(a -> ( a.toString().substring(3).equals(str.substring(3))) )
			.findAny()
			.get();
			return mail;
		} catch(NullPointerException e){
			System.out.println("email not found fatal error");
			return null; // alan masalan error handle shod!
		}
	}

	/**
		find mail by index
	**/
	public static Mail findMailByIndex(int index){
			Mail mail = getMailsToShow().get(index);
			return mail;
	}

	/**
		it return current user profile
		that encapsulation btw
	**/
	public static Profile getProfile(){
		return profile;
	}

	/**
		it updates all mail boxes from server
	**/
	public static void updateMailFromServer(){
		inbox = new CopyOnWriteArrayList<>(API.getInbox());
		sent = new CopyOnWriteArrayList<>(API.getSent());
		trash = new CopyOnWriteArrayList<>(API.getTrash());
	}

	/**
		it give a profile and update it in local profile
		#encapsulation
	**/
	public static void setProfile(Profile profile){
		ClientEXE.profile = profile;
	}

	/**
		main method of client program that is run first of All
	**/
	public static void main(String[] args) {
		launch( args );
	}

	/**
		it load the first page fxml in first part of program
	**/
	@Override
	@SuppressWarnings("deprecation")
	public void start(Stage primaryStage) { // Avvalesh, MainMenu ro load mikonim!

		pStage = primaryStage;
		Parent root = null;
		try {

			root = FXMLLoader.load(getClass().getClassLoader().getResource( "FirstPage.fxml" ) );
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(13);
		}
		pStage.setTitle( "SBU GMAIL" );
		pStage.setScene( new Scene( root, 1280, 720 ) );
		pStage.show();
	}

}
