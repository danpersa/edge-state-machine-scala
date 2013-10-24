package com.danix.state.machine

import org.junit.Test
import org.junit.Assert._

/**
 * @author dpersa
 */
class UserTest {

  @Test
  def testUser() {

    val user = new User()
    user.activate()
    println(s"current state: ${user.state}")
    user.block()
    println(s"current state: ${user.state}")
    user.activate()
    println(s"current state: ${user.state}")
    assertEquals("State(active)", s"${user.state}")
  }
}
