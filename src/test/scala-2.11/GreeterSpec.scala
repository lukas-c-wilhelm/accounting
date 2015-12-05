/**
  * Created by lwilhelm on 05.12.15.
  */
class GreeterSpec extends UnitSpec {

  describe("A Greeter") {
    val greeter = new Greeter("Lukas")
    it("should create a greeting") {
      greeter.greet() should equal("Hello Lukas")
    }
  }

}
