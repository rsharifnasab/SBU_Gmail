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

/**
  command : Command - > command Command , answer : Boolean
**/
  LOGOUT,

/**
  command : Command , mail : Mail - > command Command , answer : Boolean
**/
  SEND_MAIL,

/**
  command : Command , profile : Profile - > inbox : List<Mail> , trash : List<Mail> , sent : List<Mail>
**/
  CHECK_MAIL,

/**
  command : Command , mail : Mail -> command : Command , answer : Boolean
**/
  CHANGE_MAIL,


  /**
    command : Command , profile : Profile - > command : Command , answer : Boolean

  **/
  UPDATE_PROFILE
}
