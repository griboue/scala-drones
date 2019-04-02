import scala.io.Source
import org.apache.kafka.clients.producer._
import java.util.Properties
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._


object Main extends App {

  var NUMBER_OF_DRONES = 5

  // initialization
  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  val producer = new KafkaProducer[String, String](props)
  val TOPIC="dronesinfos"


  // Creating JSON
  val drone_json: JsValue = Json.parse(s"""
  {
    "id_drone" : ${NUMBER_OF_DRONES},
    "battery" : 0,
    "altitude" : 0,
    "temperature" : 0,
    "speed": 0,
    "disk_space": 0,
    "location" : {
      "lat" : 0,
      "long" : 0
    }
  }
  """)

  NUMBER_OF_DRONES += 1



  // Converting JSON to String
  val drone_string = Json.stringify(drone_json)
  val id_drone = (drone_json \ "id_drone").get
  val id_drone_string = Json.stringify(id_drone)

  // Sending data
  val record = new ProducerRecord(TOPIC, id_drone_string, drone_string)
  producer.send(record)



  // we need to close the producer to close the connection / avoid memory usage
  producer.close()
  println("End of the program!")
}

def startDrones(numerOfDrones: Int) = {
  // finish that function:
  // create N drones and send their first signals (0 , 0 , 0)
}
