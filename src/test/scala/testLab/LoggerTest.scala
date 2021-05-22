package testLab

import org.junit.jupiter.api.Test

class LoggerTest {

  @Test def testBasic(){
    new BasicLogger("> ").log("test")
  }

}
