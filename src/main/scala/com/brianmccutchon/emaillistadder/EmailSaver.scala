package com.brianmccutchon.emaillistadder

import java.io.FileWriter
import resource._

object EmailSaver {

  def appendToFile(fname: String)(op: FileWriter => Unit) {
    for {
      fw <- managed(new FileWriter(fname, true))
    } op(fw)
  }

  def saveEmail(name: String, email: String): Unit = {
    appendToFile("/Users/brianmc7/Desktop/emails.txt") { fw =>
      fw.write(f"$name%s <$email%s>\n")
    }
  }

}
