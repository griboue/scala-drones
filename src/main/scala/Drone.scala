import scala.io.Source
import play.api.libs.json._
import play.api.libs.json.Reads._


package drone
{

class Drone(val id: Int) {
   var id_drone: Int = id

   // battery in percentage
   var battery: Int  = 0

   // altitude in meters
   var altitude: Int  = 0

   // temperature in degrees Celsius
   var temperature: Int  = 0

   // Speed in km/h
   var speed: Int  = 0

   // Remaining disk space in MB
   var disk_space: Int  = 0

   // time
   var time: Int  = 0

   // Default latitude and longitude is Paris
   var lat: Double  = 48.8534
   var long: Double  = 2.3488

   def minimumInfos() {
      println ("drone id: " + id_drone);
      println ("Point x lat : " + lat);
      println ("Point y long : " + long);
   }


   def getJsonString(): String = {
        // Creating JSON
      val drone_json: JsValue = Json.parse(s"""
      {
        "id_drone" : ${id_drone},
        "battery" : ${battery},
        "altitude" : ${altitude},
        "temperature" : ${temperature},
        "speed": ${speed},
        "disk_space": ${disk_space},
        "location" : {
          "lat" : ${lat},
          "long" : ${long}
        }
      }
      """)

      Json.stringify(drone_json)

   }

}


}


