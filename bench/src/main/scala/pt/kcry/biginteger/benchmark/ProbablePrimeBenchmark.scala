/*
 * scala-biginteger - highly optimized BigInteger implementation for scala, scala-js and scala-native.
 *
 * Copyright 2020, 2021 Kirill A. Korinsky <kirill@korins.ky>
 * Copyright 2022 Kcrypt Lab UG <support@kcry.pt>
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

package pt.kcry.biginteger.benchmark

import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
class ProbablePrimeBenchmark extends BaseBenchmark {
  @Param(Array("128", "512", "1024"))
  override var bits: Int = 0

  @Setup
  def setup(): Unit =
    super.setupNumbers()

  @Benchmark
  def java(): Unit = {
    javaPrime1.nextProbablePrime()
    javaPrime2.nextProbablePrime()
    javaPrimeHalf.nextProbablePrime()
  }

  @Benchmark
  def scalajs(): Unit = {
    sPrime1.nextProbablePrime()
    sPrime2.nextProbablePrime()
    sPrimeHalf.nextProbablePrime()
  }

  @Benchmark
  def java_native(): Unit = {
    nPrime1.nextProbablePrime()
    nPrime2.nextProbablePrime()
    nPrimeHalf.nextProbablePrime()
  }

  @Benchmark
  def korinsky(): Unit = {
    kPrime1.nextProbablePrime()
    kPrime2.nextProbablePrime()
    kPrimeHalf.nextProbablePrime()
  }
}
