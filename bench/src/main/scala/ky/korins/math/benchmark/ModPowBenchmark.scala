package ky.korins.math.benchmark

import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
class ModPowBenchmark extends BaseBenchmark {
  @Param(Array("128", "512", "1024", "4096", "8192"))
  override var bits: Int = 0

  @Setup
  def setup(): Unit =
    super.setupNumbers()

  @Benchmark
  def java_odd(): Unit = {
    javaPrime1 modPow (javaPrime2, javaPrime2)
    javaPrime2 modPow (javaPrime1, javaPrime1)
  }

  @Benchmark
  def java_even(): Unit = {
    javaPrime1 modPow (javaPrime2, javaEven2)
    javaPrime2 modPow (javaPrime1, javaEven1)
  }

  @Benchmark
  def scalajs_odd(): Unit = {
    sPrime1 modPow (sPrime2, sPrime2)
    sPrime2 modPow (sPrime1, sPrime1)
  }

  @Benchmark
  def scalajs_even(): Unit = {
    sPrime1 modPow (sPrime2, sEven2)
    sPrime2 modPow (sPrime1, sEven1)
  }

  @Benchmark
  def native_odd(): Unit = {
    nPrime1 modPow (nPrime2, nPrime2)
    nPrime2 modPow (nPrime1, nPrime1)
  }

  @Benchmark
  def native_even(): Unit = {
    nPrime1 modPow (nPrime2, nEven2)
    nPrime2 modPow (nPrime1, nEven1)
  }

  @Benchmark
  def korinsky_odd(): Unit = {
    kPrime1 modPow (kPrime2, kPrime2)
    kPrime2 modPow (kPrime1, kPrime1)
  }

  @Benchmark
  def korinsky_even(): Unit = {
    kPrime1 modPow (kPrime2, kEven2)
    kPrime2 modPow (kPrime2, kEven1)
  }
}
