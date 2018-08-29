package controllers


import form.{AssignmentForm, SignInForm, UserForm}
import javax.inject.Inject
import models.{AssignmentRepo, CacheRepo, UserRepo, NewAssignmentRepo}
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class HomeController @Inject()(cc: ControllerComponents,
                               userForm: UserForm,
                               assignmentForm: AssignmentForm,
                               cacheRepo: CacheRepo,
                               signInForm:SignInForm,
                               assignmentRep: NewAssignmentRepo) extends AbstractController(cc) {

  def index(): Action[AnyContent]  = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def signInIndex(): Action[AnyContent]  = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.signIn())
  }


  def signUpIndex(): Action[AnyContent]  = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.signUp())
  }

  def forgotPassword(): Action[AnyContent]  = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.forgotPassword())
  }

  def profile(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    Future.successful(Ok(views.html.AddAssignment()))
  }

  def addAssignmentIndex(): Action[AnyContent]  = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.AddAssignment())
  }


  def viewAssignment: Action[AnyContent] = Action.async { implicit request =>
    assignmentRep.getAll.map { data =>
      Ok(views.html.ViewAssignment(data))
    }
  }

  def viewUser(): Action[AnyContent]  = Action.async { implicit request: Request[AnyContent] =>
    cacheRepo.getAll.map { data =>
      Ok(views.html.viewUser(data))
    }
  }


  def signUp: Action[AnyContent] = Action.async { implicit request =>

    userForm.userForm.bindFromRequest.fold(
      formWithError => {
        Future.successful(Ok(s"${formWithError.errors}"))
      },
      data => {
        cacheRepo.get(data.userName).flatMap { optionalUser =>
          optionalUser.fold {
            val dbPayload: UserRepo = UserRepo(0, data.fName, data.mName, data.lName, data.userName, data.password.password, data.mobile, data.age, data.gender, data.Hobbies)
            cacheRepo.store(dbPayload).map { _ =>
              Redirect(routes.HomeController.profile())

            }
          } { _ =>
            Future.successful(Ok("User already exist"))
          }
        }
      }
    )

  }


  def signIn: Action[AnyContent] = Action.async { implicit request =>
    signInForm.signInForm.bindFromRequest.fold(
      formWithError => {
        Future.successful(BadRequest(s"${formWithError.errors}"))
      },
      data => {
        cacheRepo.get(data.userName).map{
          optionalUser=>
            optionalUser.fold{
              NotFound("user does not exits")
            } { loginuser => {
              if (loginuser.password == data.password) {
                Redirect(routes.HomeController.profile())
              }
              else {
                Ok("invalid username or passsword")
              }

            }
            }
        }
      }
    )
  }



  def addAssignment: Action[AnyContent] = Action.async { implicit request =>

    assignmentForm.assignmentForm.bindFromRequest.fold(
      formWithError => {
        Future.successful(Ok(s"${formWithError.errors}"))
      },
      data => {
        assignmentRep.get(data.title).flatMap { optionalUser =>
          optionalUser.fold {
            val dbPayload: AssignmentRepo = AssignmentRepo(0, data.title, data.Details)
            assignmentRep.store(dbPayload).map { _ =>
              Redirect(routes.HomeController.profile())

            }
          } { _ =>
            Future.successful(Ok("User already exist"))
          }
        }
      }
    )

  }
}