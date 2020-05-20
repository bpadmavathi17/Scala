package org.demo.oop.commands
import org.demo.oop.FileSystem.State

class cat(fileName : String) extends  Command {
  override def apply(state: State): State = {
    val wd= state.wd
    val dirEntry = wd.findEntry(fileName)
    if(dirEntry == null || !dirEntry.isFile)
      state.setMessage(fileName + "No such File")
    else{
      state.setMessage(dirEntry.asFile.contents)
    }

  }
}
