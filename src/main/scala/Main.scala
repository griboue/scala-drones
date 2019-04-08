import scala.io.Source
import org.apache.kafka.clients.producer._
import java.util.Properties
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

import drone.Drone


object Main extends App {

  var NUMBER_OF_DRONES = 5

  // initialization
  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  val producer = new KafkaProducer[String, String](props)
  val TOPIC="dronesinfos"


  val drone = new Drone(106);

  // Creating JSON
  val drone_json: JsValue = Json.parse(s"""
  {
    "id_drone" : ${drone.id_drone},
    "battery" : ${drone.battery},
    "altitude" : ${drone.altitude},
    "temperature" : ${drone.temperature},
    "speed": ${drone.speed},
    "disk_space": ${drone.disk_space},
    "location" : {
      "lat" : ${drone.lat},
      "long" : ${drone.long}
    }
  }
  """)

  NUMBER_OF_DRONES += 1

  // Sending data
  val record = new ProducerRecord(TOPIC, drone.id_drone.toString, drone.getJsonString())
  producer.send(record)


  // we need to close the producer to close the connection / avoid memory usage
  producer.close()
  println("End of the program!")

}

/*def startDrones(numerOfDrones: Int) = {
  // finish that function:
  // create N drones and send their first signals (0 , 0 , 0)
}*/
