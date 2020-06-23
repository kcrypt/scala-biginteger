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
  def java(): Unit = {
    javaPrime1 mod javaTWO
    javaPrime2 mod javaTWO
    javaPrime1 mod javaTHREE
    javaPrime2 mod javaTHREE
  }

  @Benchmark
  def scalajs(): Unit = {
    sPrime1 mod sTWO
    sPrime2 mod sTWO
    sPrime1 mod sTHREE
    sPrime2 mod sTHREE
  }

  @Benchmark
  def java_native(): Unit = {
    nPrime1 mod nTWO
    nPrime2 mod nTWO
    nPrime1 mod nTHREE
    nPrime2 mod nTHREE
  }

  @Benchmark
  def korinsky(): Unit = {
    kPrime1 mod kTWO
    kPrime2 mod kTWO
    kPrime1 mod kTHREE
    kPrime2 mod kTHREE
  }
}
