package com.brianmccutchon.emaillistadder

import java.io.FileWriter
import resource._
import org.apache.commons.validator.routines.EmailValidator

object EmailSaver {

  def appendToFile(fname: String)(op: FileWriter => Unit) {
    for {
      fw <- managed(new FileWriter(fname, true))
    } op(fw)
  }

  def saveEmail(name: String, email: String): Unit = {
    appendToFile("/Users/brianmc7/Desktop/emails.txt") { fw =>
      fw.write(s"$name <$email>\n")
    }
  }

  def emailLooksValid(first: String, last: String, email: String) = {
    val udEmail = ".*@udallas.edu"
    val lastStripped = last.replaceAll("\\s", "")
    val validUdEmail = s"${first(0)}$lastStripped\\d?@udallas\\.edu".toLowerCase
    EmailValidator.getInstance().isValid(email) &&
      (!(email matches udEmail) || (email matches validUdEmail))
  }

}
