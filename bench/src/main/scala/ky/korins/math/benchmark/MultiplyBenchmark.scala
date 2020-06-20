package ky.korins.math.benchmark

import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
class MultiplyBenchmark extends BaseBenchmark {
  @Param(Array("128", "512", "1024", "4096", "8192"))
  override var bits: Int = 0

  @Setup
  def setup(): Unit =
    super.setupNumbers()

  @Benchmark
  def java_two(): Unit = {
    javaPrime1 multiply javaTWO
    javaPrime2 multiply javaTWO
  }

  @Benchmark
  def java_big(): Unit = {
    javaPrime1 multiply javaPrime2
    javaPrime2 multiply javaPrime1
  }

  @Benchmark
  def scalajs_two(): Unit = {
    sPrime1 multiply sTWO
    sPrime2 multiply sTWO
  }

  @Benchmark
  def scalajs_big(): Unit = {
    sPrime1 multiply sPrime2
    sPrime2 multiply sPrime1
  }

  @Benchmark
  def native_two(): Unit = {
    nPrime1 multiply nTWO
    nPrime2 multiply nTWO
  }

  @Benchmark
  def native_big(): Unit = {
    nPrime1 multiply nPrime2
    nPrime2 multiply nPrime1
  }

  @Benchmark
  def korinsky_two(): Unit = {
    kPrime1 multiply kTWO
    kPrime2 multiply kTWO
  }

  @Benchmark
  def korinsky_big(): Unit = {
    kPrime1 multiply kPrime2
    kPrime2 multiply kPrime1
  }
}