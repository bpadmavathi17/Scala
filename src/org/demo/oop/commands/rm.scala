package org.demo.oop.commands
import org.demo.oop.FileSystem.State
import org.demo.oop.files.Directory

class rm(name:String) extends Command {



  override def apply(state: State): State = {
    //1.get working directory
    val wd = state.wd
    //2.get the absolute path
    val absolutePath =
      if(name.startsWith(Directory.SEPARATOR)) name
      else if(wd.isRoot) wd.path + name
      else wd.path + Directory.SEPARATOR + name
    //3. do some checks
    if(Directory.ROOT_PATH.equals(absolutePath))
      state.setMessage("Command not supported yet")
    else
      doRM(state,absolutePath)



  }
  def doRM(state: State, path: String): State = {

    def rmHelper(currentDirectory: Directory, path: List[String]) : Directory = {
      if(path.isEmpty) currentDirectory
      else if(path.tail.isEmpty) currentDirectory.removeEntry(path.head)
      else {
        val nextDirectory = currentDirectory.findEntry(path.head)
        if(!nextDirectory.isDirectory) currentDirectory
        else {
          val newNextDirectory = rmHelper(nextDirectory.asDirectory, path.tail)
          if(newNextDirectory == nextDirectory) currentDirectory
          else currentDirectory.replaceEntry(path.head, newNextDirectory)

        }
      }
    }


    //4. find the entry to remove
    val tokens = path.substring(1).split(Directory.SEPARATOR).toList
    val newRoot: Directory = rmHelper(state.root, tokens)
    if(newRoot == state.root)
      state.setMessage(path + ": No such path")
    else
      State(newRoot, newRoot.findDescendant(state.wd.path.substring(1)))
    //5.update structure
  }
}
