package com.ibm

/**
 * Hello world!
 *
 */
import akka.actor.{Actor, ActorSystem}
import akka.actor.Props


class LocalActor(remoteIp: String) extends Actor {

  // create the remote actor (Akka 2.1 syntax)
  var actorPath = "akka.tcp://HelloSystem@"+remoteIp+":5150/user/remoteHelloActor"
  val remote = context.actorSelection(actorPath)
  var counter = 0

  def receive = {
    case "START" =>
      remote ! "Hello from the LocalActor"
    case msg: String =>
      println(s"LocalActor received message: '$msg'")
      if (counter < 5) {
        sender ! "Hello back to you"
        counter += 1
      }
  }
}

object App {

  def main (args: Array[String]) {

    val system = ActorSystem("HelloClient")

    println(args.length)
    //println(args(0))

    val remoteIp = if (args.length > 0) args(0) else "9.124.41.245"

    val remoteActorRef = system.actorSelection("akka.tcp://HelloSystem@"+remoteIp+":5150/user/remoteHelloActor")

    remoteActorRef ! "Hello from main"

   // val localActorRef = system.actorOf(Props(new LocalActor(remoteIp)), name = "LocalActor")
    //localActorRef ! "START"



  }
}
