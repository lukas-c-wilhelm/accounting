import org.slf4j.LoggerFactory

/**
  * Created by lwilhelm on 05.12.15.
  */
class Greeter(name: String) {

  private val message = "Hello " + name

  def greet(): String = {
    logger.debug(String.format("returning %s", message))

    message
  }

  def logger = LoggerFactory.getLogger("Greeter")
}
