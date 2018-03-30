package com.yunjae.jsoup.epost

import java.net.URLEncoder

import scala.collection.JavaConverters._
import org.jsoup._

import scala.util.{Failure, Success}

/**
  * Created by USER on 2018-03-23.
  */
object SearchZipCodeMain extends App {

  val selectUserZip = new SelectUserZip()

  // Plain Query Run
  /*val userZip: Vector[User] = selectUserZip.getQueryUserZip()
  userZip.take(10).foreach(println)*/

  // TableQuery Query Run
  selectUserZip.getUserZip().foreach {user =>
    getEpostZioNo(user)
  }


  /**
    * (User)주소 테스트 정보를 가져와 epost의 우편번호 Http Document parse 후 데이블 업데이트
    * @param user
    */
  def getEpostZioNo(user: User): Unit = {
    // 검색할 주소 인코딩
    var addrEnc = URLEncoder.encode(user.addr, "UTF-8")
    // epos http get 으로 document 객체 가져오기
    val doc = Jsoup.connect(s"https://www.epost.go.kr/search.RetrieveIntegrationNewZipCdList.comm?keyword=$addrEnc").get()
    // 우편번호값 파싱해서 Elements 객체 가져오기
    val elems = doc.select("#body_layout > div > div.menu_wrap > div.tab_contents > div > div > div > div > table > tbody > tr.title2 > th").asScala

    // Update
    elems.take(1).foreach {zip =>
      val newUser: User = user.copy(newZipNo = Option(zip.text))
      selectUserZip.updateUserZip(newUser)
    }
  }


}



