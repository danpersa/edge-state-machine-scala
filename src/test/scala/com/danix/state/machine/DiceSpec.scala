package com.danix.state.machine

import org.specs2.mutable.SpecificationWithJUnit

/**
 * @author dpersa
 */
class DiceSpec extends SpecificationWithJUnit {

  "The Dice state machine" should {
    "be in some state" in {
      val dice = new Dice()
      dice.roll()
      dice.state.name must(beOneOf("one", "two", "three", "four", "five", "six"))
    }
  }

}
