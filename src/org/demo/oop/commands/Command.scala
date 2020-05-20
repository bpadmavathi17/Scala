package org.demo.oop.commands

import org.demo.oop.FileSystem.State

trait Command {
  def apply(state:State) : State
}
object Command {
  val MKDIR = "mkdir"
  val LS = "ls"
  val PWD = "pwd"
  val TOUCH = "touch"
  val CD = "cd"
  val RM = "rm"
  val ECHO = "echo"
  val CAT = "cat"
  def emptyCommand : Command = new Command{
    override def apply(state:State):State = state
  }
  def incompleteCommand(name:String) : Command = new Command{
    override def apply(state:State):State = state.setMessage(name + " incomplete Command")
  }
  def from (input : String) : Command = {
    val tokens:Array[String] = input.split(" ")

    //replace else if ladder with pattern matching
    //command can be funtion of type 1 (State => state), then apply method can be removed as function type1 has already implemented the apply method OOB
    if (input.isEmpty || tokens.isEmpty) emptyCommand
    else if (MKDIR.equals(tokens(0))) {
     if (tokens.length < 2) incompleteCommand(MKDIR)
     else new Mkdir(tokens(1))
    }else if(LS.equals(tokens(0))){
      new ls
    }else if(PWD.equals(tokens(0))){
      new Pwd
    }else if(TOUCH.equals(tokens(0))){
      if (tokens.length < 2) incompleteCommand(TOUCH)
      else new Touch(tokens(1))
    }else if(CD.equals(tokens(0))){
      if (tokens.length < 2) incompleteCommand(CD)
      else new cd(tokens(1))
    }else if(RM.equals(tokens(0))){
      if (tokens.length < 2) incompleteCommand(RM)
      else new rm(tokens(1))
    }else if(ECHO.equals(tokens(0))){
      if (tokens.length < 2) incompleteCommand(ECHO)
      else new echo(tokens.tail)
    }else if(CAT.equals(tokens(0))){
      if (tokens.length < 2) incompleteCommand(CAT)
      else new cat(tokens(1))
    }else  new UnknownCommand
  }
}
