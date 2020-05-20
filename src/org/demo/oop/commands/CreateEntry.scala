package org.demo.oop.commands

import org.demo.oop.FileSystem.State
import org.demo.oop.files.{DirEntry, Directory}

abstract class CreateEntry(name:String) extends Command{


  def checkIllegal(name: String): Boolean = {
    name.contains(".")
  }

  def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry) :Directory= {
    if(path.isEmpty) currentDirectory.addEntry(newEntry)
    else {
      val oldEntry = currentDirectory.findEntry(path.head).asDirectory
      currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry,path.tail, newEntry))
    }
  }

  def doCreateEntry(state:State, name: String): State = {
    val wd = state.wd
    val fullpath = wd.path
    val allDirsInPath = wd.getAllFoldersInPath
    val newEntry : DirEntry = createSpecificEntry(state)
    val newRoot = updateStructure(state.root, allDirsInPath, newEntry)
    val newWd = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWd)

  }

  def createSpecificEntry(state:State) : DirEntry

  override def apply(state: State): State = {
    val wd = state.wd
    if(wd.hasEntry(name)){
      state.setMessage(" Entry "+name+ " already exists")
    }else if(name.contains(Directory.SEPARATOR)){
      state.setMessage(name + " must not contain "+ Directory.SEPARATOR)
    }else if(checkIllegal(name)){
      state.setMessage(name + "is illegal")
    }else{
      doCreateEntry(state, name)
    }
  }


}
