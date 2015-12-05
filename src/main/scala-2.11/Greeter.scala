/**
  * Created by lwilhelm on 05.12.15.
  */
class Greeter(name: String) {

  private val message = "Hello " + name

  def greet(): String = message
}
