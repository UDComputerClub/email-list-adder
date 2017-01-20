package com.brianmccutchon.emaillistadder

import java.io.FileWriter

object EmailSaver {

  def appendToFile(fname: String)(op: FileWriter => Unit) {
    val fw = new FileWriter(fname, true)
    try { op(fw) } finally { fw.close() }
  }

  def saveEmail(name: String, email: String): Unit = {
    appendToFile("/Users/brianmc7/Desktop/emails.txt") { fw =>
      fw.write(f"$name%s <$email%s>\n")
    }
  }

}
