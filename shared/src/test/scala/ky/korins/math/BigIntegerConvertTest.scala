/*
 * Scala.js (https://www.scala-js.org/)
 *
 * Copyright EPFL.
 *
 * Licensed under Apache License 2.0
 * (https://www.apache.org/licenses/LICENSE-2.0).
 *
 * See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 */

// scalastyle:off line.size.limit
/*
 * Ported by Alistair Johnson from
 * https://github.com/gwtproject/gwt/blob/master/user/test/com/google/gwt/emultest/java/math/BigIntegerConvertTest.java
 */
// scalastyle:on line.size.limit

package ky.korins.math

import org.scalatest.wordspec

import scala.language.implicitConversions

class BigIntegerConvertTest extends wordspec.AnyWordSpec {

  // Wrappers to overstep https://github.com/scala-native/scala-native/issues/1836
  private def crashAsDouble(b: BigInteger)(cb: BigInteger => Unit): Unit = {
    try {
      cb(b)
    } catch {
      case e: NumberFormatException =>
        try {
          b.toString().toDouble
          fail(e)
        } catch {
          case de: NumberFormatException if e.getMessage == de.getMessage =>
        }
    }
  }

  private def crashAsFloat(b: BigInteger)(cb: BigInteger => Unit): Unit = {
    try {
      cb(b)
    } catch {
      case e: NumberFormatException =>
        try {
          b.toString().toFloat
          fail(e)
        } catch {
          case de: NumberFormatException if e.getMessage == de.getMessage =>
        }
    }
  }

  "DoubleValueNegative1" in {
    val a = "-27467238945"
    val result = -2.7467238945E10
    val aNumber = new BigInteger(a).doubleValue()
    assert(result == aNumber)
  }

  "DoubleValueNegative2" in {
    val a = "-2746723894572364578265426346273456972"
    val result = -2.7467238945723645E36
    val aNumber = new BigInteger(a).doubleValue()
    assert(result == aNumber)
  }

  "DoubleValueNegativeInfinity1" in {
    val a = "-274672389457236457826542634627345697228374687236476867674746" +
      "2342342342342342342342323423423423423423426767456345745293762384756" +
      "2384756345634568456345689345683475863465786485764785684564576348756" +
      "7384567845678658734587364576745683475674576345786348576847567846578" +
      "3456702897830296720476846578634576384567845678346573465786457863"
    crashAsDouble(new BigInteger(a)) { b =>
      val aNumber = b.doubleValue()
      assert(Double.NegativeInfinity == aNumber)
    }
  }

  "DoubleValueNegativeInfinity2" in {
    val a = Array[Byte](-1, -1, -1, -1, -1, -1, -1, -8, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val aSign = -1
    crashAsDouble(new BigInteger(aSign, a)) { b =>
      val aNumber = b.doubleValue()
      assert(Double.NegativeInfinity == aNumber)
    }
  }

  "DoubleValueNegMantissaIsZero" in {
    val a = Array[Byte](-128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val aSign = -1
    val aNumber = new BigInteger(aSign, a).doubleValue()
    assert(-8.98846567431158E307 == aNumber)
  }

  "DoubleValueNegMaxValue" in {
    val a = Array[Byte](0, -1, -1, -1, -1, -1, -1, -8, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1)
    val aSign = -1
    val aNumber = new BigInteger(aSign, a).doubleValue()
    assert(-Double.MaxValue == aNumber)
  }

  "DoubleValueNegNotRounded" in {
    val a = Array[Byte](-128, 1, 2, 3, 4, 5, -128, 23, 1, -3, -5)
    val aSign = -1
    val result = -1.5474726438794828E26
    val aNumber = new BigInteger(aSign, a).doubleValue()
    assert(result == aNumber)
  }

  "DoubleValueNegRounded1" in {
    val a = Array[Byte](-128, 1, 2, 3, 4, 5, 60, 23, 1, -3, -5)
    val aSign = -1
    val result = -1.54747264387948E26
    val aNumber = new BigInteger(aSign, a).doubleValue()
    assert(result == aNumber)
  }

  "DoubleValueNegRounded2" in {
    val a = Array[Byte](-128, 1, 2, 3, 4, 5, 36, 23, 1, -3, -5)
    val aSign = -1
    val result = -1.547472643879479E26
    val aNumber = new BigInteger(aSign, a).doubleValue()
    assert(result == aNumber)
  }

  "DoubleValuePositive1" in {
    val a = "27467238945"
    val result = 2.7467238945E10
    val aNumber = new BigInteger(a).doubleValue()
    assert(result == aNumber)
  }

  "DoubleValuePositive2" in {
    val a = "2746723894572364578265426346273456972"
    val result = 2.7467238945723645E36
    val aNumber = new BigInteger(a).doubleValue()
    assert(result == aNumber)
  }

  "DoubleValuePositiveInfinity1" in {
    val a = Array[Byte](-1, -1, -1, -1, -1, -1, -1, -8, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val aSign = 1
    crashAsDouble(new BigInteger(aSign, a)) { b =>
      val aNumber = b.doubleValue()
      assert(Double.PositiveInfinity == aNumber)
    }
  }

  "DoubleValuePositiveInfinity2" in {
    val a = "2746723894572364578265426346273456972283746872364768676747462" +
      "3423423423423423423423234234234234234234267674563457452937623847562" +
      "3847563456345684563456893456834758634657864857647856845645763487567" +
      "3845678456786587345873645767456834756745763457863485768475678465783" +
      "456702897830296720476846578634576384567845678346573465786457863"
    crashAsDouble(new BigInteger(a)) { b =>
      val aNumber = b.doubleValue()
      assert(Double.PositiveInfinity == aNumber)
    }
  }

  "DoubleValuePosMantissaIsZero" in {
    val a = Array[Byte](-128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val aSign = 1
    val result = 8.98846567431158E307
    val aNumber = new BigInteger(aSign, a).doubleValue()
    assert(result == aNumber)
  }

  "DoubleValuePosMaxValue" in {
    val a = Array[Byte](0, -1, -1, -1, -1, -1, -1, -8, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1)
    val aSign = 1
    val aNumber = new BigInteger(aSign, a).doubleValue()
    assert(Double.MaxValue == aNumber)
  }

  "DoubleValuePosNotRounded" in {
    val a = Array[Byte](-128, 1, 2, 3, 4, 5, -128, 23, 1, -3, -5)
    val aSign = 1
    val result = 1.5474726438794828E26
    val aNumber = new BigInteger(aSign, a).doubleValue()
    assert(result == aNumber)
  }

  "DoubleValuePosRounded1" in {
    val a = Array[Byte](-128, 1, 2, 3, 4, 5, 60, 23, 1, -3, -5)
    val aSign = 1
    val result = 1.54747264387948E26
    val aNumber = new BigInteger(aSign, a).doubleValue()
    assert(result == aNumber)
  }

  "DoubleValuePosRounded2" in {
    val a = Array[Byte](-128, 1, 2, 3, 4, 5, 36, 23, 1, -3, -5)
    val aSign = 1
    val result = 1.547472643879479E26
    val aNumber = new BigInteger(aSign, a).doubleValue()
    assert(result == aNumber)
  }

  "DoubleValueZero" in {
    val a = "0"
    val result = 0.0
    val aNumber = new BigInteger(a).doubleValue()
    assert(result == aNumber)
  }

  "FloatValueNearNegMaxValue" in {
    val a = Array[Byte](0, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val aSign = -1
    val aNumber:Float = new BigInteger(aSign, a).floatValue()
    val result = -3.4028235e38
    val delta = 1e31
    assert(Math.abs(aNumber - result) < delta)
  }

  "FloatValueNearPosMaxValue" in {
    val a = Array[Byte](0, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val aSign = 1
    val aNumber = new BigInteger(aSign, a).floatValue()
    val result = 3.4028235e38
    val delta = 1e31
    assert(Math.abs(aNumber - result) < delta)
  }

  "FloatValueNegative1" in {
    val a = "-27467238"
    val result = -2.7467238E7f
    val aNumber = new BigInteger(a).floatValue()
    val delta = 1
    assert(Math.abs(aNumber - result) < delta)
  }

  "FloatValueNegative2" in {
    val a = "-27467238945723645782"
    val result = -2.7467239E19f
    val aNumber = new BigInteger(a).floatValue()
    val delta = 1e12
    assert(aNumber - result < delta)
  }

  "FloatValueNegativeInfinity1" in {
    val a = "-274672389457236457826542634627345697228374687236476867674746" +
      "2342342342342342342342323423423423423423426767456345745293762384756" +
      "2384756345634568456345689345683475863465786485764785684564576348756" +
      "7384567845678658734587364576745683475674576345786348576847567846578" +
      "3456702897830296720476846578634576384567845678346573465786457863"
    crashAsFloat(new BigInteger(a)) { b =>
      val aNumber = b.floatValue()
      assert(Float.NegativeInfinity == aNumber)
    }
  }

  "FloatValueNegativeInfinity2" in {
    val a = Array[Byte](0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1)
    val aSign = -1
    crashAsFloat(new BigInteger(aSign, a)) { b =>
      val aNumber = b.floatValue()
      assert(Float.NegativeInfinity == aNumber)
    }
  }

  "FloatValueNegMantissaIsZero" in {
    val a = Array[Byte](1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val aSign = -1
    crashAsFloat(new BigInteger(aSign, a)) { b =>
      val aNumber = b.floatValue()
      assert(Float.NegativeInfinity == aNumber)
    }
  }

  "FloatValueNegNotRounded" in {
    val a = Array[Byte](-128, 1, 2, 3, 4, 5, 60, 23, 1, -3, -5)
    val aSign = -1
    val result = -1.5474726E26f
    val aNumber = new BigInteger(aSign, a).floatValue()
    val delta = 1e19
    assert(aNumber - result < delta)
  }

  "FloatValueNegRounded1" in {
    val a = Array[Byte](-128, 1, -1, -4, 4, 5, 60, 23, 1, -3, -5)
    val aSign = -1
    val result = -1.5475195E26f
    val aNumber = new BigInteger(aSign, a).floatValue()
    val delta = 1e19
    assert(aNumber - result < delta)
  }

  "FloatValueNegRounded2" in {
    val a = Array[Byte](-128, 1, 2, -128, 4, 5, 60, 23, 1, -3, -5)
    val aSign = -1
    val result = -1.5474728E26f
    val aNumber = new BigInteger(aSign, a).floatValue()
    val delta = 1e19
    assert(aNumber - result < delta)
  }

  "FloatValuePastNegMaxValue" in {
    val a = Array[Byte](0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1)
    val aSign = -1
    crashAsFloat(new BigInteger(aSign, a)) { b =>
      val aNumber = b.floatValue()
      assert(Float.NegativeInfinity == aNumber)
    }
  }

  "FloatValuePastPosMaxValue" in {
    val a = Array[Byte](0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1)
    val aSign = 1
    crashAsFloat(new BigInteger(aSign, a)) { b =>
      val aNumber = b.floatValue()
      assert(Float.PositiveInfinity == aNumber)
    }
  }

  "FloatValuePositive1" in {
    val a = "27467238"
    val result = 2.7467238E7f
    val aNumber = new BigInteger(a).floatValue()
    assert(result == aNumber)
  }

  "FloatValuePositive2" in {
    val a = "27467238945723645782"
    val result = 2.7467239E19f
    val aNumber = new BigInteger(a).floatValue()
    val delta = 1e12
    assert(aNumber - result < delta)
  }

  "FloatValuePositiveInfinity1" in {
    val a = Array[Byte](0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1)
    val aSign = 1
    crashAsFloat(new BigInteger(aSign, a)) { b =>
      val aNumber: Float = b.floatValue()
      assert(Float.PositiveInfinity == aNumber)
    }
  }

  "FloatValuePositiveInfinity2" in {
    val a = "274672389457236457826542634627345697228374687236476867674746234" +
      "23423423423423423423234234234234234234267674563457452937623847562384" +
      "75634563456845634568934568347586346578648576478568456457634875673845" +
      "67845678658734587364576745683475674576345786348576847567846578345670" +
      "2897830296720476846578634576384567845678346573465786457863"
    crashAsFloat(new BigInteger(a)) { b =>
      val aNumber = b.floatValue()
      assert(Float.PositiveInfinity == aNumber)
    }
  }

  "FloatValuePosMantissaIsZero" in {
    val a = Array[Byte](-128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val aSign = 1
    val result = 1.7014118E38f
    val aNumber = new BigInteger(aSign, a).floatValue()
    val delta = 1e31
    assert(aNumber - result < delta)
  }

  "FloatValuePosNotRounded" in {
    val a = Array[Byte](-128, 1, 2, 3, 4, 5, 60, 23, 1, -3, -5)
    val aSign = 1
    val result = 1.5474726E26f
    val aNumber = new BigInteger(aSign, a).floatValue()
    val delta = 1e19
    assert(aNumber - result < delta)
  }

  "FloatValuePosRounded1" in {
    val a = Array[Byte](-128, 1, -1, -4, 4, 5, 60, 23, 1, -3, -5)
    val aSign = 1
    val result = 1.5475195E26f
    val aNumber = new BigInteger(aSign, a).floatValue()
    val delta = 1e19
    assert(aNumber - result < delta)
  }

  "FloatValuePosRounded2" in {
    val a = Array[Byte](-128, 1, 2, -128, 4, 5, 60, 23, 1, -3, -5)
    val aSign = 1
    val result = 1.5474728E26f
    val aNumber = new BigInteger(aSign, a).floatValue()
    val delta = 1e19
    assert(aNumber - result < delta)
  }

  "FloatValueZero" in {
    val a = "0"
    val result = 0.0f
    val aNumber = new BigInteger(a).floatValue()
    assert(result == aNumber)
  }

  "IntValueNegative1" in {
    val aBytes = Array[Byte](12, 56, 100, -2, -76, -128, 45, 91, 3)
    val sign = -1
    val resInt = 2144511229
    val aNumber = new BigInteger(sign, aBytes).intValue()
    assert(resInt == aNumber)
  }

  "IntValueNegative2" in {
    val aBytes = Array[Byte](-12, 56, 100)
    val result = -771996
    val aNumber = new BigInteger(aBytes).intValue()
    assert(result == aNumber)
  }

  "IntValueNegative3" in {
    val aBytes = Array[Byte](12, 56, 100, -2, -76, 127, 45, 91, 3)
    val sign = -1
    val resInt = -2133678851
    val aNumber = new BigInteger(sign, aBytes).intValue()
    assert(resInt == aNumber)
  }

  "IntValuePositive1" in {
    val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3)
    val resInt = 1496144643
    val aNumber = new BigInteger(aBytes).intValue()
    assert(resInt == aNumber)
  }

  "IntValuePositive2" in {
    val aBytes = Array[Byte](12, 56, 100)
    val resInt = 800868
    val aNumber = new BigInteger(aBytes).intValue()
    assert(resInt == aNumber)
  }

  "IntValuePositive3" in {
    val aBytes = Array[Byte](56, 13, 78, -12, -5, 56, 100)
    val sign = 1
    val resInt = -184862620
    val aNumber = new BigInteger(sign, aBytes).intValue()
    assert(resInt == aNumber)
  }

  "LongValueNegative1" in {
    val aBytes = Array[Byte](12, -1, 100, -2, -76, -128, 45, 91, 3)
    val result = -43630045168837885L
    val aNumber = new BigInteger(aBytes).longValue()
    assert(result == aNumber)
  }

  "LongValueNegative2" in {
    val aBytes = Array[Byte](-12, 56, 100, 45, -101, 45, 98)
    val result = -3315696807498398L
    val aNumber = new BigInteger(aBytes).longValue()
    assert(result == aNumber)
  }

  "LongValuePositive1" in {
    val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, 120, -34, -12, 45, 98)
    val result = 3268209772258930018L
    val aNumber = new BigInteger(aBytes).longValue()
    assert(result == aNumber)
  }

  "LongValuePositive2" in {
    val aBytes = Array[Byte](12, 56, 100, 18, -105, 34, -18, 45)
    val result = 880563758158769709L
    val aNumber = new BigInteger(aBytes).longValue()
    assert(result == aNumber)
  }

  "ValueOfIntegerMax" in {
    val longVal = Int.MaxValue
    val aNumber = BigInteger.valueOf(longVal)
    val rBytes = Array[Byte](127, -1, -1, -1)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
       assert(rBytes(i) == resBytes(i))
    }
    assert(1 == aNumber.signum())
  }

  "ValueOfIntegerMin" in {
    val longVal =  Int.MinValue
    val aNumber = BigInteger.valueOf(longVal)
    val rBytes = Array[Byte](-128, 0, 0, 0)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
       assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == aNumber.signum())
  }

  "ValueOfLongMax" in {
    val longVal = Long.MaxValue
    val aNumber = BigInteger.valueOf(longVal)
    val rBytes = Array[Byte](127, -1, -1, -1, -1, -1, -1, -1)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
       assert(rBytes(i) == resBytes(i))
    }
    assert(1 == aNumber.signum())
  }

  "ValueOfLongMin" in {
    val longVal = Long.MinValue
    val aNumber = BigInteger.valueOf(longVal)
    val rBytes = Array[Byte](-128, 0, 0, 0, 0, 0, 0, 0)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
       assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == aNumber.signum())
  }

  "ValueOfLongNegative1" in {
    val longVal = -268209772258930018L
    val aNumber = BigInteger.valueOf(longVal)
    val rBytes = Array[Byte](-4, 71, 32, -94, 23, 55, -46, -98)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
       assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == aNumber.signum())
  }

  "ValueOfLongNegative2" in {
    val longVal = -58930018L
    val aNumber = BigInteger.valueOf(longVal)
    val rBytes = Array[Byte](-4, 124, -52, -98)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
       assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == aNumber.signum())
  }

  "ValueOfLongPositive1" in {
    val longVal = 268209772258930018L
    val aNumber = BigInteger.valueOf(longVal)
    val rBytes = Array[Byte](3, -72, -33, 93, -24, -56, 45, 98)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
       assert(rBytes(i) == resBytes(i))
    }
    assert(1 == aNumber.signum())
  }

  "ValueOfLongPositive2" in {
    val longVal = 58930018L
    val aNumber = BigInteger.valueOf(longVal)
    val rBytes = Array[Byte](3, -125, 51, 98)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
       assert(rBytes(i) == resBytes(i))
    }
    assert(1 == aNumber.signum())
  }

  "ValueOfLongZero" in {
    val longVal = 0L
    val aNumber = BigInteger.valueOf(longVal)
    val rBytes = Array[Byte](0)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
       assert(rBytes(i) == resBytes(i))
    }
    assert(0 == aNumber.signum())
  }

  "FloatValueBug2482" in {
    val a = "2147483649"
    val result = 2.14748365E9f
    val aNumber = new BigInteger(a).floatValue()
    val delta = 0.0f
    assert(delta == Math.abs(aNumber - result))
  }
}
