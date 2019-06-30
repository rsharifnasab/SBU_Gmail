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
    command : Command , profile : Profile - > command Command , answer : Boolean
  **/
  SIGNUP,


  LOGOUT,
  SEND_MAIL,
  CHECK_MAIL,

  /**
    command : Command , profile : Profile - > command : Command , answer : Boolean

  **/
  UPDATE_PROFILE
}
