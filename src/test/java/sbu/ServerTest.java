package sbu;

import sbu.serverscript.*;
import sbu.common.*;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;


/**
  a J Unit test class for testing server side
**/
public class ServerTest{
  public  Profile A = null;
  public  Profile B = null;
  public  Profile C = null;
  public  Mail m1 = null;
  public  Mail m2 = null;
  public  Mail m3 = null;
  public Map<String,Object> toSend = null;
  public Map<String,Object> recieved = null;

  public static Map<String,Profile> profileBKP;
  public static Set<Mail> MailBKP;


  /**
    this method will clone all databases for prevent change by api Call
    it save close in mailBKP and profileBKP
  **/
  @BeforeClass
  public static void copyDB(){
    DBManager.getInstance().initializeServer();
    profileBKP = new ConcurrentHashMap<>();
    profileBKP.putAll(ServerEXE.profiles);
    MailBKP = new ConcurrentSkipListSet<>();
    MailBKP.addAll(ServerEXE.mails);
  }


  /**
  this will revert DB to main state
  **/
  @AfterClass
  public static void cleanDB(){
    ServerEXE.profiles = profileBKP;
    ServerEXE.mails = MailBKP;
    DBManager.getInstance().updateDataBase();
  }

  /**
    this will be run before each test
    it will create suitable mail and profiles to make test easy for run
  **/
  @Before
  public void before(){

    toSend = new HashMap<>();

    A = new Profile("ali");
    A.setGender(Gender.MAN);
    A.setPhoneNumber("0912");
    A.setName("Ali alavi");
    A.setBirthYear("1999");
    A.setPassword("password");
    B = new Profile("bali");
    C = new Profile("cali");

    ServerEXE.profiles = new ConcurrentHashMap<>();
    ServerEXE.profiles.put("ali",A);
    ServerEXE.profiles.put("bali",B);
    ServerEXE.profiles.put("cali",C);


    m1 = new Mail("ali", "bali", "m1 subject", "m1 message");
    m2 = new Mail("bali", "cali", "m2 subject", "m2 message");
    m3 = new Mail("bali", "cali", "m3 subject", "m3 message");
    ServerEXE.mails = new ConcurrentSkipListSet<>();

  }


  /**
    it clear mails and profiles after each tests
  **/
  @After
  public void after(){
    ServerEXE.mails = null;
    ServerEXE.profiles = null;
  }

  /**
    it test login api in server which is username and password are true
  **/
  @Test
  public void loginTest1(){
    Profile A2 = new Profile("ali");
    toSend.put("username","ali");
    toSend.put("password","password");
    recieved = API.login(toSend);
    Profile A3 = (Profile) recieved.get("answer");
    assertTrue( (boolean) recieved.get("exists"));
    assertNotNull(A3);
    assertEquals(A2, A3);
  }


  /**
    it test login api in server which username is not exists
  **/
  @Test
  public void loginTest2(){
    Profile z = new Profile("zali");
    toSend.put("username","zali");
    toSend.put("password","password");
    recieved = API.login(toSend);
    Profile z2 = (Profile) recieved.get("answer");
    assertTrue( ! (boolean) recieved.get("exists"));
    assertNull(z2);
  }


  /**
    it test login api which username is ok but wrong password
  **/
  @Test
  public void loginTest3(){
    Profile A2 = new Profile("ali");
    toSend.put("username","ali");
    toSend.put("password","wrong password");
    recieved = API.login(toSend);
    Profile A3 = (Profile) recieved.get("answer");
    assertTrue( (boolean) recieved.get("exists"));
    assertNull(A3);
  }


  /**
    a method for checking usernameecheck which username actually exists
  **/
  @Test
  public void usernameCheckTest1(){
    String username2test = "ali";
    toSend.put("username",username2test);
    recieved = API.isUserNameExists(toSend);
    Boolean b = (Boolean) recieved.get("answer");
    assertNotNull(b);
    assertTrue( b );
  }

  /**
    second test of usernameCheck api that username is not exists
  **/
  @Test
  public void usernameCheckTest2(){
    String username2test = "zali";
    toSend.put("username",username2test);
    recieved = API.isUserNameExists(toSend);
    Boolean b = (Boolean) recieved.get("answer");
    assertNotNull(b);
    assertTrue( !b );
  }

  /**
    check signup api in server
    afeter singin up, it check that the username should be exists
  **/
  @Test
  public void signupTest(){

    toSend.put("username","pali");
    recieved = API.isUserNameExists(toSend);
    boolean b = (boolean) recieved.get("answer");
    assertTrue( !b );

    toSend = new HashMap<>();

    Profile p = new Profile("pali");
    p.setPassword("pali password");
    toSend.put("profile",p);
    recieved = API.signUp(toSend);
    assertTrue( (boolean) recieved.get("answer"));

    toSend = new HashMap<>();

    toSend.put("username","pali");
    recieved = API.isUserNameExists(toSend);
    b = (boolean) recieved.get("answer");
    assertTrue( b );
  }

  /**
    it check profile upadte api
  **/
  @Test
  public void updateProfileTest() {
    Profile newA = new Profile("ali");
    toSend.put("command",Command.UPDATE_PROFILE);
    toSend.put("profile",newA);
    recieved = API.updateProfile(toSend);
    boolean status =  (boolean) recieved.get("answer");
    assertTrue(status);
    assertNull(ServerEXE.profiles.get("ali").getName());
  }

  /**
    it check readmail api
  **/
  @Test
  public void readMailTest() {
    ServerEXE.mails.add(m1);
    toSend.put("command",Command.READ_MAIL);
    toSend.put("mail",m1);
    recieved = API.readMail(toSend);
    boolean status =  (boolean) recieved.get("answer");
    assertTrue(status);
    assertTrue(ServerEXE.mails.contains(m1));
    assertTrue(m1.isUnRead() == false);
    Mail other = ServerEXE.mails.stream().filter(a-> a.equals(m1)).findFirst().get();
    assertTrue(!other.isUnRead());
  }


  /**
    it check trashmail api
  **/
  @Test
  public void trashMailTest() {
    ServerEXE.mails.add(m2);
    toSend.put("command",Command.TRASH_MAIL);
    toSend.put("mail",m2);
    recieved = API.trashMail(toSend);
    boolean status =  (boolean) recieved.get("answer");
    assertTrue(status);
    assertTrue(ServerEXE.mails.contains(m2));
    assertTrue(m2.isTrashed());
    Mail other = ServerEXE.mails.stream().filter(a-> a.equals(m2)).findFirst().get();
    assertTrue(other.isTrashed());
  }


  /**
    it check logout api
  **/
  @Test
  public void logoutTest(){
    toSend.put("command",Command.LOGOUT);
    recieved = API.logout(toSend);
    assertTrue( (boolean) recieved.get("answer"));
  }


  /**
    it check send mail api
  **/
  @Test
  public void sendMailTest(){
    toSend.put("command",Command.SEND_MAIL);
    toSend.put("mail",m1);
    recieved = API.sendMail(toSend);
    assertTrue( (boolean) recieved.get("answer"));
  }

  /**
    this check send mail api too
  **/
  @Test
  public void sendMailTest2(){
    toSend.put("command",Command.SEND_MAIL);
    toSend.put("mail",m1);
    recieved = API.sendMail(toSend);
    assertTrue( (boolean) recieved.get("answer"));
    toSend.put("mail",m2);
    recieved = API.sendMail(toSend);
    assertTrue( (boolean) recieved.get("answer"));
    toSend.put("command",Command.SEND_MAIL);
    toSend.put("mail",m3);
    recieved = API.sendMail(toSend);
    assertTrue( (boolean) recieved.get("answer"));
  }

  /**
    this check check mail api
    it send mail first
    and the n check if it is in both inboxes of reciever and send box of sender
  **/
  @Test
  public void checkMailTest(){
    toSend.put("command",Command.SEND_MAIL);
    toSend.put("mail",m1);
    recieved = API.sendMail(toSend);

    toSend = new HashMap<>();
    toSend.put("command",Command.CHECK_MAIL);
    toSend.put("profile",A);
    recieved = API.checkMail(toSend);
    List<Mail> sent = (List<Mail>) recieved.get("sent");
    assertEquals(m1,sent.get(0));
    assertEquals(1,sent.size());

    toSend = new HashMap<>();
    toSend.put("command",Command.CHECK_MAIL);
    toSend.put("profile",B);
    recieved = API.checkMail(toSend);
    List<Mail> inbox = (List<Mail>) recieved.get("inbox");
    assertEquals(m1,inbox.get(0));
    assertEquals(1,inbox.size());

  }

}
