package models

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import scala.concurrent.Future

case class AssignmentRepo(id: Long, title: String, Details: String)


class NewAssignmentRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends Impl1 with AssignmentTable

trait Impl1 {
  self: AssignmentTable =>

  import profile.api._

  def store(assignmentRepo: AssignmentRepo): Future[Long] = {
    db.run {
      userAssignmentQuery returning userAssignmentQuery.map(_.id) += assignmentRepo
    }
  }

  def get(title: String): Future[Option[AssignmentRepo]] = {
    db.run {
      userAssignmentQuery.filter(_.title === title.toLowerCase()).result.headOption
    }
  }

  def getAll: Future[Seq[AssignmentRepo]] = {
    db.run {
      userAssignmentQuery.result
    }
  }

}

trait AssignmentTable extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  val userAssignmentQuery: TableQuery[UserAssignment] = TableQuery[UserAssignment]

  private[models] class UserAssignment(tag: Tag) extends Table[AssignmentRepo](tag, "assignmentTable") {

    def * : ProvenShape[AssignmentRepo] = (id, title, Details) <> (AssignmentRepo.tupled, AssignmentRepo.unapply)

    def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def title: Rep[String] = column[String]("title")

    def Details: Rep[String] = column[String]("Details")


  }

}