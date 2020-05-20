package org.demo.oop.commands
import org.demo.oop.FileSystem.State
import org.demo.oop.files.{Directory,File}


import scala.annotation.tailrec

class echo(args: Array[String]) extends Command{

  def createContent(args: Array[String], topIndex: Int) : String = {
    @tailrec
    def createContentHelper(currentIndex:Int, accumulator:String) : String = {
    if(currentIndex >= topIndex) accumulator
    else createContentHelper(currentIndex + 1, accumulator+ " " + args(currentIndex))
    }
    createContentHelper(0,"")
  }

  def getRootAfterEcho(currentDirectory : Directory, path:List[String], contents : String, appendMode:Boolean): Directory = {

    if(path.isEmpty) currentDirectory
    else if(path.tail.isEmpty) {
      val dirEntry = currentDirectory.findEntry(path.head)
      if(dirEntry == null)
        currentDirectory.addEntry(new File(currentDirectory.path, path.head, contents))
      else if(dirEntry.isDirectory) currentDirectory
      else
        if(appendMode)
          currentDirectory.replaceEntry(path.head, dirEntry.asFile.appendContents(contents))
        else
        currentDirectory.replaceEntry(path.head, dirEntry.asFile.setContents(contents))
   }else{
      val nextDirectory = currentDirectory.findEntry(path.head).asDirectory
      val newNextDirectory = getRootAfterEcho(nextDirectory, path.tail, contents,appendMode)
      if(newNextDirectory == nextDirectory) currentDirectory
      else currentDirectory.replaceEntry(path.head, newNextDirectory)
    }
  }

  def doEcho(state: State, content: String, filename: String, appendMode: Boolean) = {

    if(filename.contains(Directory.SEPARATOR)) state.setMessage("Echo : filename" + filename +" must not contain seperator")
    else{
      val newRoot : Directory = getRootAfterEcho(state.root, state.wd.getAllFoldersInPath :+ filename,content,appendMode)
      if(newRoot == state.root)
        state.setMessage(filename + "No such file")
      else
        State(newRoot, newRoot.findDescendant(state.wd.getAllFoldersInPath))


    }


  }

  override def apply(state: State): State = {
    if(args.isEmpty) state
    else if(args.length == 1) state.setMessage(args(0))
    else{
      val operator = args(args.length - 2)
      val filename = args(args.length - 1)
      val content = createContent(args, args.length - 2)
      if(">>".equals(operator))
        doEcho(state, content, filename, true)
      else if(">".equals(operator))
        doEcho(state,content,filename,false)
      else
        state.setMessage(createContent(args, args.length))


    }
  }
}
