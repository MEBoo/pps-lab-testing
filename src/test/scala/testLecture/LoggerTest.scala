package testLecture

import org.junit.jupiter.api.Test
import testLab.BasicLogger

class LoggerTest {

  @Test def testBasic(){
    new BasicLogger("> ").log("test")
  }

}
