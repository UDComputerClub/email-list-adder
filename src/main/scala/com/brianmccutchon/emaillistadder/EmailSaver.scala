package com.brianmccutchon.emaillistadder

import scalaz.Memo.mutableHashMapMemo
import java.io.FileWriter
import resource._
import org.apache.commons.validator.routines.EmailValidator

object EmailSaver {

  /**
   * If an email address is within an edit distance of this size from a likely
   * email address, it will be considered to probably be a typo.
   */
  val TYPO_TOLERANCE = 3

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
    val udEmailSuffix = "@udallas.edu"
    val lastStripped = last.replaceAll("\\s", "")
    val validUdUser = s"${first(0)}$lastStripped".toLowerCase
    val validUdEmails = Vector(s"$validUdUser$udEmailSuffix") ++
      (1 to 9 map { i => f"$validUdUser$i%d$udEmailSuffix" })
    EmailValidator.getInstance().isValid(email) &&
      (validUdEmails.contains(email) ||
        !validUdEmails.exists { editDist(_, email) <= TYPO_TOLERANCE })
  }

  /**
   * Computes the Levenshtein Distance between two strings. This is the minimum
   * number of single-character edits (insertions, deletions, and substitutions)
   * needed to get from string a to string b.
   */
  val editDist: ((String, String)) => Int = mutableHashMapMemo { case (a, b) =>
    if (a == "" || b == "")
      a.length max b.length
    else editDist(a.tail, b) + 1 min
         editDist(a, b.tail) + 1 min
         editDist(a.tail, b.tail) + (if (a(0) == b(0)) 0 else 1)
  }

}
