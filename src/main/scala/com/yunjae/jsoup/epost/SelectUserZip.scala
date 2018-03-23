package com.yunjae.jsoup.epost


import slick.jdbc.GetResult
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._

/**
  * Created by USER on 2018-03-23.
  */
class SelectUserZip {
  // 데이터 베이스 설정(application.conf)
  val db = Database.forConfig("posmall")
  // Timeout 시간 지정
  val timeout = 20.seconds

  /**
    * TableQuery run
    * @return
    */
  def getUserZip() = {
    val users = TableQuery[UsersTable]

    Await.result(db.run(users.result), timeout)
  }

  /**
    * Plain Query run
    * @return
    */
  def getQueryUserZip(): Vector[User] = {
    //implicit val getUserResult = GetResult(r => User(r.nextInt, r.nextString, r.nextString, Option(r.nextString)))
    implicit val getUserResult = GetResult(r => User(r.<<, r.<<, r.<<, Option(r.<<)))

    val query = db.run(sql"SELECT MEM_NO, ZIP_NO, ADDR1, NEW_ZIP_NO FROM  TMP_USER_CONVER_ZIP".as[User])

    Await.result(query, timeout)
  }

  def updateUserZip(user: User) = {
    val users = TableQuery[UsersTable]
    val q = users.filter(_.memNo === user.memNo).map(_.newZipNo).update(user.newZipNo)

    Await.result(db.run(q), timeout)
  }
}


case class User(memNo: Int, zipNo: String, addr: String, newZipNo: Option[String])

class UsersTable(tag: Tag) extends Table[User](tag, "TMP_USER_CONVER_ZIP") {
  def memNo = column[Int]("MEM_NO", O.PrimaryKey)

  def zipNo = column[String]("ZIP_NO")

  def addr = column[String]("ADDR1")

  def newZipNo = column[Option[String]]("NEW_ZIP_NO")

  def * = (memNo, zipNo, addr, newZipNo) <>(User.tupled, User.unapply)
}
