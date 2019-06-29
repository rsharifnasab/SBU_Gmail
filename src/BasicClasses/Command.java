package BasicClasses;

public enum Command {
  /**
    username : String , password:string - > (answer : Profile) command : Command , exists : Boolean
  **/
  LOGIN,


  /**
    command : Command , username : string -> answer : Boolean command:Command
  **/
  USERNAME_UNIQUE,

  /**
    command : Command , profile : Profile - > command Command , boolean answer
  **/
  SIGNUP,
  LOGOUT,
  SEND_MAIL,
  CHECK_MAIL,
  UPDATE_PROFILE
}
