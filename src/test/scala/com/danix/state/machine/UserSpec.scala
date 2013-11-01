package com.danix.state.machine

import org.specs2.mutable._
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner


/**
 * @author dpersa
 */
class UserSpec extends SpecificationWithJUnit {
   
  "The User state machine" should {
    "be pending" in {
      val user = new User()
      user.state must beEqualTo(State("pending"))
    }
    "activate" in {
      val user = new User()
      user activate()
      user.state must beEqualTo(State("active"))
    }
    "block" in {
      val user = new User()
      user activate()
      println(s"USER IS IN : ${user.state} STATE")
      user.block()
      user.state must beEqualTo(State("blocked"))
    }
    "activate" in {
      val user = new User()
      user activate()
      user block()
      user activate()
      user.state must beEqualTo(State("active"))
    }
  }
}
