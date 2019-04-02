import scala.io.Source
import org.apache.kafka.clients.producer._
import java.util.Properties
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._


object Main extends App {
  // initialization
  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  val producer = new KafkaProducer[String, String](props)
  val TOPIC="dronesinfos"


  // Creating JSON
  val json: JsValue = Json.parse("""
  {
    "id_drone" : 1,
    "battery" : 87,
    "altitude" : 40,
    "temperature" : 38,
    "speed": 20,
    "disk_space": 20,
    "location" : {
      "lat" : 51.235685,
      "long" : -1.309197
    }
  }
  """)

  // Converting JSON to String
  val jsonString = Json.stringify(json)

  // Sending data
  val record = new ProducerRecord(TOPIC, "key", jsonString)
  producer.send(record)

  // we need to close the producer to close the connection / avoid memory usage
  producer.close()
  println("End of the program!")
}

