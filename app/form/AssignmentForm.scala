package form

import play.api.data.Form
import play.api.data.Forms.{mapping, text}

case class Assignment(title: String, Details: String)

class AssignmentForm {

  val assignmentForm = Form(
    mapping(
      "title" -> text,
      "Details" -> text

    )(Assignment.apply)(Assignment.unapply)
  )

}
