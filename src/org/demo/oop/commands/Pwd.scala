package org.demo.oop.commands
import org.demo.oop.FileSystem.State

class Pwd extends Command {

  override def apply(state: State): State =
    state.setMessage(state.wd.path)
}
