package com.danix.state.machine

import scala.collection.mutable.Set
import scala.util.Random

/**
 * @author dpersa
 */

object DiceStates {
  val states: List[String] = List("one", "two", "three", "four", "five", "six")
  val statesSet: Set[String] = states.to[Set]
}

class Dice(var state: State = State("one")) extends AbstractStateMachine {
  state("one")
  state("two")
  state("three")
  state("four")
  state("five")
  state("six")

  event("roll") {
    transition(_, from = DiceStates.statesSet,
      to = DiceStates.statesSet,
      guard = rollResult,
      onTransition = displayDiceRollingAnimation)
  }

  def rollResult = { State(DiceStates.states(Random.nextInt(6))) }
  def displayDiceRollingAnimation = println(print(s"--------------- DICE: throw: $state ------------"))
}