package org.demo.oop.files

abstract class DirEntry(val parentPath:String, val name:String) {
  def path :String = {
    val separator =
      if(Directory.ROOT_PATH.equals(parentPath)) ""
      else Directory.SEPARATOR
    parentPath+separator+name
  }

  def asDirectory:Directory

  def getType : String

  def asFile:File

  def isRoot : Boolean = parentPath.isEmpty

  def isDirectory : Boolean

  def isFile : Boolean
}
