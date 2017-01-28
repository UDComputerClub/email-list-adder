package com.brianmccutchon.emaillistadder

import swing._
import swing.event._
import EmailSaver._

object Main extends SimpleSwingApplication {

  def newField = new TextField {
    columns = 15
  }

  var errorIsDisplayed = false
  val errText = "Looks like you have a typo. Double check!"
  val noErrText = " "

  val nameField = newField
  val surnameField = newField
  val emailField = newField
  val errDisplay = new Label {
    //override def text() = if (errorIsDisplayed) errText else ""
    text = noErrText
  }
  val submitButton = new Button("Submit")

  val fields = new GridPanel(3, 2) {
    contents ++= Seq(new Label("First:"), nameField,
                     new Label("Last:"), surnameField,
                     new Label("Email:"), emailField)
  }

  val body = new BoxPanel(Orientation.Vertical) {
    contents ++= Seq(fields, errDisplay, submitButton)
  }

  def top = new MainFrame {
    title = "Join the mailing list!"
    contents = body
  }

  listenTo(submitButton)

  reactions += {
    case ButtonClicked(`submitButton`) => {
      val (first, last, email) = getFieldValues()
      if (errorIsDisplayed || emailLooksValid(first, last, email)) {
        doSubmit()
      } else {
        displayError()
      }
    }
  }

  def displayError(): Unit = {
    errorIsDisplayed = true
    errDisplay.text = errText
  }

  def hideError(): Unit = {
    errorIsDisplayed = false
    errDisplay.text = noErrText
  }

  def getFieldValues() = {
    val first = nameField.text.trim
    val last = surnameField.text.trim
    val email = emailField.text.trim
    (first, last, email)
  }

  def doSubmit(): Unit = {
    val (first, last, email) = getFieldValues()
    saveEmail(s"$first $last", email)
    nameField.text = ""
    surnameField.text = ""
    emailField.text = ""
    hideError()
  }

}
