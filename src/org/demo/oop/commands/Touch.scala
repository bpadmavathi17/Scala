package org.demo.oop.commands
import org.demo.oop.FileSystem.State
import org.demo.oop.files.{DirEntry,File}

class Touch(name:String) extends CreateEntry(name) {

  override def createSpecificEntry(state: State): DirEntry =
    File.empty(state.wd.path, name)

}
