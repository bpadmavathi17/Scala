package org.demo.oop.FileSystem

import java.util.Scanner

import org.demo.oop.commands.Command
import org.demo.oop.files.Directory

object FileSystem extends App{
  val root = Directory.ROOT
  //foldLeft takes the initial value as parameter and applies a method to the list on which the foldLeft is called
  io.Source.stdin.getLines().foldLeft(State(root, root))((currentState, newLine) => {
    currentState.show
    Command.from(newLine).apply(currentState)
  })
  /*var state = State(root, root)
  val scanner = new Scanner(System.in)
  while(true){
   state.show
    val input = scanner.nextLine()
    state = Command.from(input).apply(state)
  }*/
}
