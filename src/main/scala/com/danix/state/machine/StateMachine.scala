package com.danix.state.machine

import scala.language.dynamics
import scala.collection.mutable.{MultiMap, Set, HashMap}

/**
 * @author dpersa
 */
class EventToTransitionsMultiMap extends HashMap[Event, Set[Transition]] with MultiMap[Event, Transition]

case class State(name: String)

case class Event(name: String)

class Transition(event: Event, val from: State, val to: Set[State], guard: => State, execute: => Unit) {
  override def toString() = s"Transition[from $from to $to for event $event]"
  def executeTransition() = execute
  def executeGuard() = guard
}

object Transition {
  def apply(event: Event, from: State, to: Set[State], guard: => State, execute: => Unit)
  = new Transition(event, from, to, guard, execute)
}

trait StateMachine extends Dynamic {
  var state: State
  var states: Set[State]
  var events: Set[Event]
  var transitions: EventToTransitionsMultiMap

  def state(name: String) = {
    println(s"state name $name")
    states += State(name)
  }

  def event(name: String)(transitionInit: Event => Unit) = {
    println(s"event name $name")
    val ev = Event(name)
    transitionInit(ev)
    events += ev
  }

  def transition(ev: Event, from: Set[String], to: String, onTransition: => Unit) = {
    println(s"transition from $from to $to")
    for (fromState <- from) {

      val transition = Transition(ev, State(fromState), Set(State(to)), null, onTransition)
      transitions.addBinding(ev, transition)
    }
  }

  def transition(ev: Event, from: Set[String], to: Set[String], guard: => State, onTransition: => Unit) = {
    println(s"transition from $from to $to")
    for (fromState <- from) {
      val transition = Transition(ev, State(fromState), to.map(str => State(str)), guard, onTransition)
      transitions.addBinding(ev, transition)
    }
  }

  def applyDynamic(methodName: String)() {
    println(s"EVENT: We got the $methodName event!")
    val event = Event(methodName)
    if (!transitions.keySet.contains(event)) {
      println("ERROR: could not find event!!")
      return
    }
    val nextTransition = calculateNextTransition(event)
    if (nextTransition.isEmpty) {
      println("no next state, so return")
      return
    }
    println(s"next transition $nextTransition")
    val nextState = calculateNextState(nextTransition.get)
    println(s"next state $nextState")
    state = nextState
    nextTransition.get.executeTransition()
  }

  def calculateNextTransition(event: Event): Option[Transition] = {
    val possibleTransitions = transitions(event)
    val possibleTransitionsForCurrentState = possibleTransitions.filter({ (p) => p.from == state })
    if (possibleTransitionsForCurrentState.isEmpty) {
      println("ERROR: next transition not found")
      return Option.empty;
    }
    if (possibleTransitionsForCurrentState.size > 1) {
      println("ERROR: to many transitions transitions found")
      return Option.empty;
    }
    val nextTransition = possibleTransitions.head
    return Option(nextTransition)
  }

  def calculateNextState(transition: Transition): State = {
    if (transition.to.size == 1) {
      return transition.to.head
    }
    val nextState = transition.executeGuard()
    if (nextState == null) {
      println(s"STATE: next state not returned by the guard!")
      return null
    }
    return nextState
  }
}

abstract class AbstractStateMachine extends StateMachine {
  var states: Set[State] = Set()
  var events: Set[Event] = Set()
  var transitions: EventToTransitionsMultiMap = new EventToTransitionsMultiMap
}
