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

package ky.korins.math

import org.scalatest.wordspec

import scala.language.implicitConversions

class BigDecimalToStringTest extends wordspec.AnyWordSpec {

  "toStringWithCornerCaseScales" in {
    val bigIntOne = 1L

    assert("1" == Conversion.toDecimalScaledString(bigIntOne, 0))

    assert("0.01" == Conversion.toDecimalScaledString(bigIntOne, 2))
    assert("0.000001" == Conversion.toDecimalScaledString(bigIntOne, 6))
    assert("1E-7" == Conversion.toDecimalScaledString(bigIntOne, 7))
    assert("1E-2147483647" == Conversion.toDecimalScaledString(bigIntOne, 2147483647))

    assert("1E+1" == Conversion.toDecimalScaledString(bigIntOne, -1))
    assert("1E+2" == Conversion.toDecimalScaledString(bigIntOne, -2))
    assert("1E+15" == Conversion.toDecimalScaledString(bigIntOne, -15))
    assert("1E+2147483647" == Conversion.toDecimalScaledString(bigIntOne, -2147483647))
    assert("1E+2147483648" == Conversion.toDecimalScaledString(bigIntOne, -2147483648)) // #4088

    val bigInt123 = 123L

    assert("123" == Conversion.toDecimalScaledString(bigInt123, 0))

    assert("1.23" == Conversion.toDecimalScaledString(bigInt123, 2))
    assert("0.000123" == Conversion.toDecimalScaledString(bigInt123, 6))
    assert("0.00000123" == Conversion.toDecimalScaledString(bigInt123, 8))
    assert("1.23E-7" == Conversion.toDecimalScaledString(bigInt123, 9))
    assert("1.23E-2147483645" == Conversion.toDecimalScaledString(bigInt123, 2147483647))

    assert("1.23E+3" == Conversion.toDecimalScaledString(bigInt123, -1))
    assert("1.23E+4" == Conversion.toDecimalScaledString(bigInt123, -2))
    assert("1.23E+17" == Conversion.toDecimalScaledString(bigInt123, -15))
    assert("1.23E+2147483649" == Conversion.toDecimalScaledString(bigInt123, -2147483647)) // #4088
    assert("1.23E+2147483650" == Conversion.toDecimalScaledString(bigInt123, -2147483648)) // #4088
  }

}