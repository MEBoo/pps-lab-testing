package testLab

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@CucumberOptions(features = Array("classpath:features/Shopping.feature"),
  tags = Array("not @Wip"), glue = Array("classpath:testLab.steps"),
  plugin = Array("pretty", "html:target/cucumber/html"))
class RunCucumberTests
