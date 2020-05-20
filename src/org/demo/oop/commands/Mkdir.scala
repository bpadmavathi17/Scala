package org.demo.oop.commands

import org.demo.oop.FileSystem.State
import org.demo.oop.files.{DirEntry, Directory}

class Mkdir(name:String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State): DirEntry = {
    Directory.empty(state.wd.path, name)
  }
}
