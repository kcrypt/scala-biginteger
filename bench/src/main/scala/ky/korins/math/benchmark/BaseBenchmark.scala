package ky.korins.math.benchmark

import java.math.{BigInteger => jBigInteger}

import ky.korins.math.{BigInteger => kBigInteger}

trait BaseBenchmark {
  var bits: Int

  val javaONE = jBigInteger.valueOf(1)
  val kONE = kBigInteger.valueOf(1)

  val javaTWO = jBigInteger.valueOf(2)
  val kTWO = kBigInteger.valueOf(2)

  val javaTHREE = jBigInteger.valueOf(3)
  val kTHREE = kBigInteger.valueOf(3)

  var javaPrime1: jBigInteger = jBigInteger.valueOf(0)
  var javaPrime2: jBigInteger = jBigInteger.valueOf(0)

  var javaEven1: jBigInteger = jBigInteger.valueOf(0)
  var javaEven2: jBigInteger = jBigInteger.valueOf(0)

  var kPrime1: kBigInteger = kBigInteger.valueOf(0)
  var kPrime2: kBigInteger = kBigInteger.valueOf(0)

  var kEven1: kBigInteger = kBigInteger.valueOf(0)
  var kEven2: kBigInteger = kBigInteger.valueOf(0)

  def setupNumbers(): Unit = {
    javaPrime1 = new jBigInteger(Numbers.primes_1(bits))
    kPrime1 = new kBigInteger(Numbers.primes_1(bits))

    javaEven1 = javaPrime1 subtract javaONE
    kEven1 = kPrime1 subtract kONE

    javaPrime2 = new jBigInteger(Numbers.primes_2(bits))
    kPrime2 = new kBigInteger(Numbers.primes_2(bits))

    javaEven2 = javaPrime2 subtract javaONE
    kEven2 = kPrime2 subtract kONE
  }
}
