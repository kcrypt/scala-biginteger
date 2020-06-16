package ky.korins.math.benchmark

import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
class ModPowBenchmark extends BaseBenchmark {
  @Param(Array("128", "512", "1024"))
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
