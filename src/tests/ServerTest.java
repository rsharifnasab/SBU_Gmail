package tests;

import ServerScript.*;
import ServerScript.DB.*;
import BasicClasses.*;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;

public class ServerTest{
  public static Profile A = null;
  public static Profile B = null;
  public static Profile C = null;
  public static Mail m1 = null;
  public static Mail m2 = null;
  public static Mail m3 = null;
  public Map<String,Object> toSend = null;
  public Map<String,Object> recieved = null;

  @BeforeClass
  public static void beforeclass(){

  }
  @AfterClass
  public static void afterclass(){

  }

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

  @After
  public void after(){
    ServerEXE.mails = null;
    ServerEXE.profiles = null;
  }

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

  @Test
  public void usernameCheckTest1(){
    String username2test = "ali";
    toSend.put("username",username2test);
    recieved = API.isUserNameExists(toSend);
    Boolean b = (Boolean) recieved.get("answer");
    assertNotNull(b);
    assertTrue( b );
  }

  @Test
  public void usernameCheckTest2(){
    String username2test = "zali";
    toSend.put("username",username2test);
    recieved = API.isUserNameExists(toSend);
    Boolean b = (Boolean) recieved.get("answer");
    assertNotNull(b);
    assertTrue( !b );
  }

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

  @Test
  public void changeMailTest() {
    m1.read();
    toSend.put("command",Command.CHANGE_MAIL);
    toSend.put("mail",m1);
    recieved = API.changeMail(toSend);
    boolean status =  (boolean) recieved.get("answer");
    assertTrue(status);
    assertTrue(ServerEXE.mails.contains(m1));
    assertTrue(!m1.isUnRead());
    Mail other = ServerEXE.mails.stream().filter(a-> a.equals(m1)).findFirst().get();
    assertTrue(!other.isUnRead());
  }

  @Test
  public void logoutTest(){
    toSend.put("command",Command.LOGOUT);
    recieved = API.logout(toSend);
    assertTrue( (boolean) recieved.get("answer"));
  }

  @Test
  public void sendMailTest(){
    toSend.put("command",Command.SEND_MAIL);
    toSend.put("mail",m1);
    recieved = API.sendMail(toSend);
    assertTrue( (boolean) recieved.get("answer"));
  }

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
