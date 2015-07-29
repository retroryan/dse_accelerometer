package actors

import java.util.Properties

import akka.actor._
import com.typesafe.config.Config
import kafka.producer.{Producer, ProducerConfig}
import models.Acceleration

object SystemManager extends ExtensionKey[SystemManager]

class SystemManager(system: ExtendedActorSystem) extends Extension {

  val producerActor: ActorRef = system.actorOf(Props(classOf[ProducerActor]))

  val systemConfig = system.settings.config
  val kafkaHost = systemConfig.getString("pipeline.kafkaHost")
  val kafkaTopic = systemConfig.getString("pipeline.kafkaTopic")

  val props = new Properties()
  props.put("metadata.broker.list", kafkaHost)
  props.put("serializer.class", "kafka.serializer.StringEncoder")

  val producerConfig = new ProducerConfig(props)
  val producer = new Producer[String, String](producerConfig)

}

trait SystemManagerActor { this: Actor =>
  val systemManager: SystemManager = SystemManager(context.system)
}