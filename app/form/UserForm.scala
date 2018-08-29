package form

import play.api.data.Form
import play.api.data.Forms.{text, mapping, number}

case class Password(password : String,confirmPassword: String)


case class User(fName :String,mName: String,lName :String,userName :String,password :Password,mobile :String,age:Int,gender:String,Hobbies:String)

class UserForm {

  val userForm = Form(
    mapping(
      "fName" -> text.verifying("First name can not be Empty",_.nonEmpty),
      "mName" -> text.verifying("Middle name can not be Empty",_.nonEmpty),
      "lName" ->  text.verifying("Last name can not be Empty",_.nonEmpty),
      "userName"-> text.verifying("UserName name can not be Empty",_.nonEmpty),
      "passwordGroup" -> mapping(
        "password" -> text.verifying("password must not be empty",_.nonEmpty),
        "confirmPassword" -> text.verifying("Confirm password must not be empty",_.nonEmpty)
      )(Password.apply)(Password.unapply)
        .verifying("password and confrim password must match",passwordGroup =>
          passwordGroup.password == passwordGroup.confirmPassword),
      "mobile"->text.verifying("Mobile name can not be Empty should be 10 digit number",mobile=> mobile.length ==10 ),
      "age"->number.verifying("Age should be greater than 18 and less than 75",age=>age>=18 && age<=75),
      "gender"->text.verifying("Gender name can not be Empty",_.nonEmpty),
      "hobbies" ->text

    )(User.apply)(User.unapply)
  )
}


