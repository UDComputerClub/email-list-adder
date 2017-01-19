package com.brianmccutchon.emaillistadder

import swing._
import swing.event._

object Main extends SimpleSwingApplication {

  def newField = new TextField {
    columns = 15
  }

  val nameField = newField
  val emailField = newField
  val submitButton = new Button("Submit")

  val fields = new GridPanel(2, 2) {
    contents += new Label("Name:")
    contents += nameField
    contents += new Label("Email:")
    contents += emailField
  }

  val body = new BoxPanel(Orientation.Vertical) {
    contents += fields
    contents += submitButton
  }

  def top = new MainFrame {
    title = "Join the mailing list!"
    contents = body
  }

  listenTo(nameField, emailField, submitButton, body, fields)

  reactions += {
    case ButtonClicked(`submitButton`) | KeyReleased(_, Key.Enter, _, _) =>
      EmailSaver.saveEmail(nameField.text, emailField.text)
      println(nameField.text + " " + emailField.text)
      nameField.text = ""
      emailField.text = ""
  }

}
