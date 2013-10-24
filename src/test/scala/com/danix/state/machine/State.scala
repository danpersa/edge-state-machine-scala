package com.danix.state.machine

import scala.language.dynamics
import collection.mutable.{ HashMap, MultiMap, Set }
import scala.util.Random

/**
 * @author dpersa
 */


object Main {
  def main(args: Array[String]) {
    val user = new User()
    user.activate()
    println(s"current state: ${user.state}")
    user.block()
    println(s"current state: ${user.state}")
    user.activate()
    println(s"current state: ${user.state}")
    
    
    val dice = new Dice()
    for(i <- 1 to 100) {
      dice.roll()
      
    }
  }
}

