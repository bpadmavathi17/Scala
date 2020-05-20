package org.demo.oop.files

import org.demo.oop.FileSystem.FileSystemException

class File(override val parentPath: String, override val name: String, val contents: String) extends DirEntry(parentPath , name) {
  def setContents(contents: String): DirEntry =
    new File(parentPath, name, contents)
  def appendContents(newContents: String): DirEntry =
    setContents( contents + "\n" + newContents)

  override def asDirectory: Directory =
    throw new FileSystemException("File cannot be converted to a directory")

  override def getType: String = "File"

  override def asFile: File = this

  override def isDirectory: Boolean = false

  override def isFile: Boolean = true

}
object File{
  def empty(parentpath:String,name:String) =
    new File(parentpath,name, "")
}

