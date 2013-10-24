package com.danix.state.machine

import scala.collection.mutable.{Set}

/**
 * @author dpersa
 */
class User(var state: State = State("pending")) extends AbstractStateMachine {
  state("pending")
  state("active")
  state("blocked")

  event("activate")  {
    transition(_, from = Set("pending", "blocked"), to = "active", doActivate)
  }

  event("block") {
    transition(_, from = Set("active"), to = "blocked", doBlock)
  }

  def doBlock = println("do_block")

  def doActivate = println("do_activate")
}