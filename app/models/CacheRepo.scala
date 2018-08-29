package models

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import scala.concurrent.Future

case class UserRepo(id: Long, fName :String,mName: String,lName :String,userName :String,password :String,mobile :String,age:Int,gender:String,Hobbies:String)


class CacheRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends Impl with UserTable

trait Impl{
  self: UserTable =>

  import profile.api._

  def store(userRepo: UserRepo): Future[Long] = {
    db.run{
      userProfileQuery returning userProfileQuery.map(_.id) += userRepo
    }
  }

  def get(userName:String):Future[Option[UserRepo]] = {
    db.run{
      userProfileQuery.filter(_.userName === userName.toLowerCase()).result.headOption
    }
  }

  def getAll: Future[Seq[UserRepo]] = {
    db.run {
      userProfileQuery.result
    }
  }
}

trait UserTable extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  val userProfileQuery: TableQuery[UserProfile] = TableQuery[UserProfile]

  private[models] class UserProfile(tag: Tag) extends Table[UserRepo](tag,"userprofile"){

    def * : ProvenShape[UserRepo] =(id , fName, mName, lName, userName, password, mobile, age, gender, Hobbies) <> (UserRepo.tupled, UserRepo.unapply)

    def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def fName:Rep[String] = column[String]("fName")

    def mName:Rep[String] = column[String]("mName")

    def lName:Rep[String] = column[String]("lName")

    def userName:Rep[String] = column[String]("userName")

    def password:Rep[String] = column[String]("password")

    def mobile:Rep[String] = column[String]("mobile")

    def age:Rep[Int] = column[Int]("age")

    def gender:Rep[String] = column[String]("gender")

    def Hobbies:Rep[String] = column[String]("Hobbies")

  }
}