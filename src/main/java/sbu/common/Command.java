package sbu.common;
/**
  an enum to go from client to server to specify what should server do
  and also the outgoing map has which fields
**/
public enum Command {
  /**
    command : Command , username : String , password:string - > (answer : Profile) command : Command , exists : Boolean
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
  READ_MAIL,

  /**
    command : Command , mail : Mail -> command : Command , answer : Boolean
  **/
  TRASH_MAIL,

  

  /**
    command : Command , profile : Profile - > command : Command , answer : Boolean

  **/
  UPDATE_PROFILE
}
