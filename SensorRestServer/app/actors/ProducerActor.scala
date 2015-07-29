package actors

import actors.ProducerActor.SendAcceleration
import akka.actor.{Props, Actor}
import kafka.producer.KeyedMessage
import models.Acceleration

class ProducerActor extends Actor with SystemManagerActor {

  def receive = {
    case SendAcceleration(acceleration, rawJson) =>
      systemManager.producer.send(new KeyedMessage[String, String](systemManager.kafkaTopic, rawJson))
  }
}

object ProducerActor {

  def props():Props = Props(new ProducerActor)

  case class SendAcceleration(acceleration:Acceleration, rawJson:String)

}
