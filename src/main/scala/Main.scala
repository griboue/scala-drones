import scala.io.Source
import org.apache.kafka.clients.producer._
import java.util.Properties
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import java.util.Timer // in order to send regular drones updates

import drone.Drone


object Main extends App {



  // Kafka initialization
  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  val producer = new KafkaProducer[String, String](props)
  val TOPIC="dronesinfos"

  // create 3 drones (just for the simulation)
  val drone0 = new Drone(0);
  val drone1 = new Drone(1);
  val drone2 = new Drone(2);

  val drones: List[Drone] = List(drone0, drone1, drone2)


  // Simulation of each drone sending values updates to kafka each 3 seconds
  sendRegularDronesInfos(drones, 3000)




  // Sending data
  //val record = new ProducerRecord(TOPIC, drone.id_drone.toString, drone.getJsonString())
  //producer.send(record)


  // we need to close the producer to close the connection / avoid memory usage



  def sendRegularDronesInfos(drones: List[Drone], period: Int): Unit =  {

    val t = new java.util.Timer()
    val task = new java.util.TimerTask {
      def run() = {
         drones.foreach { drone =>
          println(drone)
          val record = new ProducerRecord(TOPIC, drone.id_drone.toString, drone.getJsonString())
          producer.send(record)
          drone.changeInfos() // simulate drone infos updating
        }
      }
    }
    t.schedule(task, period, period)
    //task.cancel()
  }

}


