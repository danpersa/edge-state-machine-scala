package com.danix.state.machine

import org.specs2.mutable._
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner


/**
 * @author dpersa
 */
class UserSpec extends SpecificationWithJUnit {

  val user = new User()
  
  "The User state machine" should {
    "activate" in {
      user activate()
      user.state.must(beEqualTo(State("active")))
    }
  }
  
}
