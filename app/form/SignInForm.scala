package form

import play.api.data.Form
import play.api.data.Forms.{mapping, text}

case class Sign(userName :String,password: String)


class SignInForm {

  val signInForm = Form(
    mapping(
      "userName" -> text,
      "password" -> text


    )(Sign.apply)(Sign.unapply)
  )

}
