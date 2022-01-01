/*
 * scala-biginteger - highly optimized BigInteger implementation for scala, scala-js and scala-native.
 *
 * Copyright 2020, 2021 Kirill A. Korinsky <kirill@korins.ky>
 * Copyright 2022 Kcrypt Lab UG <opensource@kcry.pt>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

/*
 * Ported by Alistair Johnson from
 * https://github.com/gwtproject/gwt/blob/master/user/super/com/google/gwt/emul/java/math/Primality.java
 * Original license copied below:
 */

/*
 * Copyright 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * INCLUDES MODIFICATIONS BY RICHARD ZSCHECH AS WELL AS GOOGLE.
 */

package pt.kcry.biginteger

import java.util.Arrays
import java.util.Random

import scala.language.implicitConversions

/** Provides primality probabilistic methods. */
private[biginteger] object Primality {
  val searchLen = 1024 // for searching of the next probable prime number

  /** All prime numbers with bit length lesser than 10 bits. */
  private val Primes = Array(2, 3, 5, 7, 11, 13, 17, 19, 23, 29,
      31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101,
      103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167,
      173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239,
      241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313,
      317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397,
      401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467,
      479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569,
      571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643,
      647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733,
      739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823,
      827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911,
      919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997, 1009,
      1013, 1019, 1021)

  /** Encodes how many i-bit primes there are in the table for {@code i=2,...,10}.
   *
   *  For example {@code offsetPrimes[6]} says that from index
   *  {@code 11} exists {@code 7} consecutive {@code 6}-bit prime numbers in the
   *  array.
   */
  private val OffsetPrimes = Array(
      null, null, (0, 2), (2, 2), (4, 2), (6, 5), (11, 7),
      (18, 13), (31, 23), (54, 43), (97, 75))

  /** All {@code BigInteger} prime numbers with bit length lesser than 8 bits. */
  private val BiPrimes =
    Array.tabulate[BigInteger](Primes.length)(i => BigInteger.valueOf(Primes(i)))

  /** A random number is generated until a probable prime number is found.
   *
   *  @see BigInteger#BigInteger(int,int,Random)
   *  @see BigInteger#probablePrime(int,Random)
   *  @see #isProbablePrime(BigInteger, int)
   */
  def consBigInteger(bitLength: Int, certainty: Int, rnd: Random): BigInteger = {
    // PRE: bitLength >= 2
    // For small numbers get a random prime from the prime table
    if (bitLength <= 10) {
      val rp = OffsetPrimes(bitLength)
      BiPrimes(rp._1 + rnd.nextInt(rp._2))
    } else {
      val shiftCount = (-bitLength) & 31
      val count = (bitLength + 31) >> 5
      val probPrime = new BigInteger(1, count, new Array[Int](count))

      val last = count - 1
      var sieve: Sieve = null
      while (true) {
        if (probPrime.bitLength() != bitLength) {
          // To fill the array with random integers
          var i = 0
          while (i < probPrime.numberLength) {
            probPrime.digits(i) = rnd.nextInt()
            i += 1
          }
          // To fix to the correct bitLength
          probPrime.digits(last) = (probPrime.digits(last) | 0x80000000) >>> shiftCount
          // To create an odd number
          probPrime.digits(0) |= 1
          sieve = new Sieve(probPrime, searchLen)
        }

        if (sieve.checkSearchLen(probPrime, certainty)) {
          return probPrime
        }
      }
      throw new AssertionError("Primality.consBigInteger: Should not get here")
    }
  }

  /** Returns true if this is a prime, within the provided certainty.
   *
   *  @see BigInteger#isProbablePrime(int)
   *  @see #millerRabin(BigInteger, int)
   *  @ar.org.fitc.ref Optimizations: "A. Menezes - Handbook of applied
   *                   Cryptography, Chapter 4".
   */
  def isProbablePrime(n: BigInteger, certainty: Int): Boolean = {
    // scalastyle:off return
    // PRE: n >= 0
    if (certainty <= 0 || (n.numberLength == 1 && n.digits(0) == 2)) {
      true
    } else if (!n.testBit(0)) {
      // To discard all even numbers
      false
    } else if (n.numberLength == 1 && (n.digits(0) & 0XFFFFFC00) == 0) {
      // To check if 'n' exists in the table (it fit in 10 bits)
      Arrays.binarySearch(Primes, n.digits(0)) >= 0
    } else {
      // To check if 'n' is divisible by some prime of the table
      var i = 0
      while (i < Primes.length) {
        if (Division.remainderArrayByInt(n.digits, n.numberLength, Primes(i)) == 0)
          return false
        i += 1
      }

      isProbablePrimeImpl(n, certainty)
    }
    // scalastyle:on return
  }

  def isProbablePrimeImpl(n: BigInteger, certainty: Int): Boolean = {
    /*
      ANSI X9.80 specifies the checking that make up a round of the following 3 probabilistic algorithms:
        1. Miller-Rabin (MR)
        2. Lucas - Lehmer
        3. Frobenius-Grantham (FG).
      And then defines the following 3 methods as conforming to X9.80:
        1. 50 MR rounds.
        2. a lesser number of MR rounds based on size (between 2 and 27) followed by 1 Lucas round.
        3. a specific number of FG rounds based on size (between 2 and 8).

      This allows to reduce number of MR rounds significant
     */
    if (n.bitLength() < 100) {
      return millerRabin(n, 50) // fast, very-very-fast only MR
    }

    val bitLength = n.bitLength()
    val x980_rounds =
      if (bitLength < 256) 27
      else if (bitLength < 512) 15
      else if (bitLength < 768) 8
      else if (bitLength < 1024) 4
      else 2

    val newCertainty = Math.min(x980_rounds, 1 + ((certainty - 1) >> 1))

    millerRabin(n, newCertainty) && lucasLehmer(n)
  }

  /** Returns the next, probable prime number.
   *
   *  It uses the sieve of Eratosthenes to discard several composite numbers in
   *  some appropriate range (at the moment {@code [this, this + 1024]}). After
   *  this process it applies the Miller-Rabin test to the numbers that were not
   *  discarded in the sieve.
   *
   *  @see BigInteger#nextProbablePrime()
   *  @see #millerRabin(BigInteger, int)
   */
  def nextProbablePrime(n: BigInteger, certainty: Int): BigInteger = {
    // scalastyle:off return
    // PRE: n >= 0

    // If n < "last prime of table" searches next prime in the table
    val digitsLessPrime = (n.digits(0) < Primes(Primes.length - 1))
    if ((n.numberLength == 1) && (n.digits(0) >= 0) && digitsLessPrime) {
      var i = 0
      while (n.digits(0) >= Primes(i)) {
        i += 1
      }
      return BiPrimes(i)
    }

    /*
     * Creates a "N" enough big to hold the next probable prime Note that: N <
     * "next prime" < 2*N
     */
    val a = new Array[Int](n.numberLength + 1)
    val probPrime: BigInteger = new BigInteger(1, n.numberLength, a)
    System.arraycopy(n.digits, 0, probPrime.digits, 0, n.numberLength)

    // To fix N to the "next odd number"
    if (n.testBit(0)) Elementary.inplaceAdd(probPrime, 2)
    else probPrime.digits(0) |= 1

    val sieve = new Sieve(probPrime, searchLen)
    while (true) {
      if (sieve.checkSearchLen(probPrime, certainty)) {
        return probPrime
      }
    }
    throw new AssertionError("Primality.nextProbablePrime: Should not get here")
    // scalastyle:on return
  }

  // Implement sieve of Eratosthenes
  class Sieve(startPoint: BigInteger, searchLen: Int) {
    private val isDivisible = {
      val modules = new Array[Int](Primes.length)

      // To calculate modules: N mod p1, N mod p2, ... for first primes.
      var i = 0
      while (i < Primes.length) {
        modules(i) = Division.remainder(startPoint, Primes(i)) - searchLen
        i += 1
      }

      // At this point, all numbers in the gap are initialized as probably primes
      val isDivisible = new Array[Boolean](searchLen)

      // To discard multiples of first primes
      i = 0
      while (i < Primes.length) {
        modules(i) = (modules(i) + searchLen) % Primes(i)
        var j =
          if (modules(i) == 0) 0
          else Primes(i) - modules(i)
        while (j < searchLen) {
          isDivisible(j) = true
          j += Primes(i)
        }
        i += 1
      }

      isDivisible
    }

    def checkSearchLen(probPrime: BigInteger, certainty: Int): Boolean = {
      var j = 0
      var skip = 0
      while (j < searchLen) {
        if (!isDivisible(j)) {
          Elementary.inplaceAdd(probPrime, skip)
          skip = 1
          if (isProbablePrimeImpl(probPrime, certainty)) {
            return true
          }
        } else {
          skip += 1
        }
        j += 1
      }
      Elementary.inplaceAdd(probPrime, skip)
      false
    }
  }



  /** The Miller-Rabin primality test.
   *
   *  @param n the input number to be tested.
   *  @param t the number of trials.
   *  @return {@code false} if the number is definitely compose, otherwise
   *          {@code true} with probability {@code 1 - 4<sup>(-t)</sup>}.
   *  @ar.org.fitc.ref "D. Knuth, The Art of Computer Programming Vo.2, Section
   *                   4.5.4., Algorithm P"
   */
  private def millerRabin(n: BigInteger, t: Int): Boolean = {
    // scalastyle:off return
    // PRE: n >= 0, t >= 0
    var x: BigInteger = null
    var y: BigInteger = null
    val nMinus1 = n.subtract(BigInteger.ONE)
    val bitLength = nMinus1.bitLength()
    val k = nMinus1.getLowestSetBit()
    val q = nMinus1.shiftRight(k)
    val rnd = new Random()
    var i = 0
    while (i < t) {
      // To generate a witness 'x', first it use the primes of table
      if (i < Primes.length) {
        x = BiPrimes(i)
      } else {
        /*
         * It generates random witness only if it's necesssary. Note that all
         * methods would call Miller-Rabin with t <= 50 so this part is only to
         * do more robust the algorithm
         */
        while ({
          x = new BigInteger(bitLength, rnd)

          (x.compareTo(n) >= BigInteger.EQUALS) || x.sign == 0 || x.isOne
        }) ()
      }

      y = x.modPow(q, n)
      if (!(y.isOne || (y equals nMinus1))) {
        var j = 1
        while (j < k) {
          if (y != nMinus1) {
            y = y.multiply(y).mod(n)
            if (y.isOne)
              return false
          }
          j += 1
        }
        if (y != nMinus1)
          return false
      }
      i += 1
    }
    true
    // scalastyle:on return
  }

  /**
   * Compute the jacobi symbol (a/n), as described at
   * Digital signature standard (DSS).
   * FIPS PUB 186-4, National Institute of Standards and Technology (NIST), 2013.
   *
   * Because it can be used for [[BigInteger]], it replaces recursively call to iteration.
   */
  def jacobi(a: BigInteger, n: BigInteger): Int = {
    var _a = a
    var _n = n
    var _s = 1

    if (_n.remainder(BigInteger.TWO) equals BigInteger.ZERO) {
      throw new ArithmeticException("The bottom number (n) must be odd.")
    }

    while (true) {
      // step 1
      _a = _a mod _n

      // step 2
      if ((_a equals BigInteger.ONE) || (_n equals BigInteger.ONE)) {
        return _s
      }

      // step 3
      if (_a equals BigInteger.ZERO) {
        return 0
      }

      // step 4: a=2^e*_a1
      var _e = 0
      var _a1 = _a
      while ((_a1 and BigInteger.ONE) equals BigInteger.ZERO) {
        _e += 1
        _a1 = _a1 shiftRight 1
      }

      // multiple to 1 doesn't mean anything so skip:
      // Step 5 when e is event
      // Step 5 when n mod 8 is 1 or 8
      if ((_e & 0x1) != 0) {
        val `n mod 8` = _n mod BigInteger.EIGHT
        if ((`n mod 8` equals BigInteger.THREE) || (`n mod 8` equals BigInteger.FIVE)) {
          _s = -_s
        }
      }

      // step 6
      if (((_n mod BigInteger.FOUR) equals BigInteger.THREE) && ((_a1 mod BigInteger.FOUR) equals BigInteger.THREE)) {
        _s = -_s
      }

      // step 7
      _a = _n
      _n = _a1

      // step 8: just iterate
    }

    throw new AssertionError("jacobi: Should not get here")
  }

  /* Lucas-Lehmer probable prime. */
  private def lucasLehmer(n: BigInteger): Boolean = {
    var d: BigInteger = BigInteger.MINUS_THREE
    while ({
      d = if (d.sign < 0) d.abs() add BigInteger.TWO
      else (d add BigInteger.TWO).negate()
      val j = jacobi(d, n)
      // if jacobi symbol is zero, it isn't a prime
      // it stastes in Section C.3.3 step 2 by
      // FIPS PUB 186-4, National Institute of Standards and Technology (NIST), 2013
      if (j == 0) {
        return false
      }
      j != -1
    })()

    val k = n add BigInteger.ONE
    var u = BigInteger.ONE
    var v = BigInteger.ONE

    var i = k.bitLength() - 2
    while (i >= 0) {
      var u2 = u multiply v mod n
      var v2 = v.pow(2) add (d multiply u.pow(2)) mod n
      if (v2.testBit(0)) {
        v2 = v2 subtract n
      }
      v2 = v2.shiftRight(1)
      u = u2
      v = v2
      if (k.testBit(i)) {
        u2 = u add(v) mod n
        if (u2.testBit(0)) {
          u2 = u2 subtract n
        }

        u2 = u2.shiftRight(1)
        v2 = v add(d multiply u) mod n
        if (v2.testBit(0)) {
          v2 = v2 subtract n
        }
        v2 = v2.shiftRight(1)

        u = u2
        v = v2
      }
      i -= 1
    }

    (u mod n) equals  BigInteger.ZERO
  }
}
