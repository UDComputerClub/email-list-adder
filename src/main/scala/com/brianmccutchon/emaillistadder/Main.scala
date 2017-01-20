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
    contents ++= Seq(new Label("Name:"), nameField,
                     new Label("Email:"), emailField)
  }

  val body = new BoxPanel(Orientation.Vertical) {
    contents ++= Seq(fields, submitButton)
  }

  def top = new MainFrame {
    title = "Join the mailing list!"
    contents = body
  }

  listenTo(submitButton)

  reactions += {
    case ButtonClicked(`submitButton`) =>
      EmailSaver.saveEmail(nameField.text, emailField.text)
      nameField.text = ""
      emailField.text = ""
  }

}
