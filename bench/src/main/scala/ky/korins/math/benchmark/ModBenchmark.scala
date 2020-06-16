package ky.korins.math.benchmark

import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
class ModBenchmark extends BaseBenchmark {
  @Param(Array("128", "512", "1024", "4096", "8192"))
  override var bits: Int = 0

  @Setup
  def setup(): Unit =
    super.setupNumbers()

  @Benchmark
  def java_two(): Unit = {
    javaPrime1 mod javaTWO
    javaPrime2 mod javaTWO
  }

  @Benchmark
  def java_three(): Unit = {
    javaPrime1 mod javaTHREE
    javaPrime2 mod javaTHREE
  }

  @Benchmark
  def korinsky_two(): Unit = {
    kPrime1 mod kTWO
    kPrime2 mod kTWO
  }

  @Benchmark
  def korinsky_three(): Unit = {
    kPrime1 mod kTHREE
    kPrime2 mod kTHREE
  }
}