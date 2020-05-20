package org.demo.oop.commands
import org.demo.oop.FileSystem.State
import org.demo.oop.files.DirEntry

class ls extends Command {

  def createOutput(contents: List[DirEntry]) : String = {
    if(contents.isEmpty) ""
    else {
      val entry = contents.head
      entry.name + "[" + entry.getType + "]" + "\n" + createOutput(contents.tail)
    }
  }

  override def apply(state: State): State = {
    val contents = state.wd.contents
    val output = createOutput(contents)
    state.setMessage(output)
  }

}
