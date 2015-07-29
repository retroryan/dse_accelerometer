package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Acceleration(userid:String, timestamp: Long, x: Double, y: Double, z: Double, notes:String)

object Acceleration {
  implicit val locationReads: Reads[Acceleration] = (
    (JsPath \ "userid").read[String] and
    (JsPath \ "timestamp").read[Long] and
      (JsPath \ "x").read[Double] and
      (JsPath \ "y").read[Double] and
      (JsPath \ "z").read[Double] and
      (JsPath \ "notes").read[String]
    )(Acceleration.apply _)
}

