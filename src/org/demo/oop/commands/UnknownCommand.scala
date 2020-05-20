package org.demo.oop.commands

import org.demo.oop.FileSystem.State

class UnknownCommand extends  Command {
  override def apply(state: State) : State =
    state.setMessage("Command not found")
}
