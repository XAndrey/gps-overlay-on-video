package peregin.gpv

import java.io.FileWriter
import peregin.gpv.util.JsonConverter
import scala.io.Source


object Setup {

  def save(setup: Setup): String = JsonConverter.generate(setup)
  def saveFile(file: String, setup: Setup) {
    val fw = new FileWriter(file)
    fw.write(save(setup))
    fw.close()
  }

  def load(json: String): Setup = JsonConverter.parse[Setup](json)
  def loadFile(file: String): Setup = {
    val r = Source.fromFile(file)
    val json = r.mkString
    r.close()
    load(json)
  }

  def empty = new Setup(None, None, None, None, None, None)
}

case class Setup(var videoPath: Option[String],
                 var gpsPath: Option[String],
                 var outputPath: Option[String],
                 var shiftTimestamp: Option[Long],
                 var dashboardTransparency: Option[Double],
                 var dashboardUnits: Option[String]) {

  def save = Setup.save(this)
  def saveFile(path: String) = Setup.saveFile(path, this)

  // in millis
  def shift = shiftTimestamp.getOrElse(0L)
  def shift_= (value: Long) = shiftTimestamp = Some(value)

  // in percentage
  def transparency = dashboardTransparency.getOrElse(60d)
  def transparency_=(value: Double) = dashboardTransparency = Some(value)

  // units
  def units = dashboardUnits.getOrElse("Metric")
  def units_=(value: String) = dashboardUnits = Some(value)
}
