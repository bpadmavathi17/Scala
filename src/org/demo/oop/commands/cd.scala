package org.demo.oop.commands
import org.demo.oop.FileSystem.State
import org.demo.oop.files.{DirEntry, Directory}

import scala.annotation.tailrec

class cd(dir:String) extends Command {

  def doFindEntry(root: Directory, path: String): DirEntry = {
    @tailrec
    def findEntryHelper(currentDirectory: Directory, path: List[String]): DirEntry = {
      if(path.isEmpty || path.head.isEmpty) currentDirectory
      else if (path.tail.isEmpty) currentDirectory.findEntry(path.head)
      else {
        val nextDir = currentDirectory.findEntry(path.head)
        if(nextDir == null || !nextDir.isDirectory) null
        else findEntryHelper(nextDir.asDirectory, path.tail)
      }
    }
    @tailrec
    def collapseRelativeTokens(path:List[String], result : List[String]):List[String]= {
      if(path.isEmpty) result
      else if("." equals(path.head)) collapseRelativeTokens(path.tail, result)
      else if(".." equals(path.head)) {
        if(result.isEmpty) null
        else collapseRelativeTokens(path.tail, result.init)
      }else collapseRelativeTokens(path.tail, result :+ path.head)
    }
    val tokens: List[String] = path.substring(1).split(Directory.SEPARATOR).toList
    val newTokens = collapseRelativeTokens(tokens,List())
    if(newTokens == null) null
    else findEntryHelper(root,newTokens)

  }

  override def apply(state: State): State = {
    val root = state.root
    val wd = state.wd
    val absolutepath =
      if (dir.startsWith(Directory.SEPARATOR)) dir
      else if (wd.isRoot) wd.path+dir
      else wd.path + Directory.SEPARATOR + dir

    val destinationDirectory = doFindEntry(root, absolutepath)
    if(destinationDirectory == null || !destinationDirectory.isDirectory)
      state.setMessage(dir +": no such directory")
    else
      State(root, destinationDirectory.asDirectory)
  }

}
