package controllers

import java.util.concurrent.atomic.AtomicLong

import actors.{ProducerActor, SystemManager}
import models.Acceleration
import play.api.libs.concurrent.Akka
import play.api.libs.json._
import play.api.mvc._
import play.api.Play.current

class Application extends Controller {

  val counter = new AtomicLong()

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def acceleration = Action(BodyParsers.parse.json) { request =>
    val accelerationResult = request.body.validate[Acceleration]
    accelerationResult.fold(
      errors => {
        BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toFlatJson(errors)))
      },
      acceleration => {
        if ( (counter.getAndIncrement() % 1000) == 0)
          println(s"Received acceleration: $acceleration current counter = $counter")

        SystemManager(Akka.system).producerActor ! ProducerActor.SendAcceleration(acceleration, request.body.toString())
        Ok(Json.obj("status" -> "OK", "message" -> s"acceleration: $acceleration sent to producer."))
      }
    )
  }
}