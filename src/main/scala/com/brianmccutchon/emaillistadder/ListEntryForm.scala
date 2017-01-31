package com.brianmccutchon.emaillistadder

import swing._
import swing.event._
import EmailSaver.{ saveEmail, emailLooksValid }

object ListEntryForm extends SimpleSwingApplication {

  /******************* Model **************************************************/

  var model = new ListEntryFormModel

  /******************* View ***************************************************/

  def newField = new TextField {
    columns = 15
  }

  val errText = "Looks like you have a typo. Double check!"
  val noErrText = " "

  val nameField = newField
  val surnameField = newField
  val emailField = newField
  val errDisplay = new Label(noErrText)
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

  /******************* Controller *********************************************/

  reactions += {
    case ButtonClicked(`submitButton`) => {
      updateModel()
      if (model.dispErr || (emailLooksValid _).tupled(getFieldValues())) {
        doSubmit()
      } else {
        model = model.copy(dispErr = true)
      }
      updateView()
    }
  }

  def updateModel(): Unit = {
    val (first, last, email) = getFieldValues()
    model = new ListEntryFormModel(first, last, email, model.dispErr)
  }

  def updateView(): Unit = {
    errDisplay.text = if (model.dispErr) errText else noErrText
    nameField.text = model.first
    surnameField.text = model.last
    emailField.text = model.email
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
    model = new ListEntryFormModel
  }

}
