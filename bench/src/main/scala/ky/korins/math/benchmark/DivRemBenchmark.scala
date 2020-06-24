package ky.korins.math.benchmark

import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
class DivRemBenchmark extends BaseBenchmark {
  @Param(Array("128", "512", "1024", "4096", "8192"))
  override var bits: Int = 0

  @Setup
  def setup(): Unit =
    super.setupNumbers()

  @Benchmark
  def java(): Unit = {
    javaPrime1 divideAndRemainder javaTWO
    javaPrime2 divideAndRemainder javaPrime1
    javaEven1 divideAndRemainder javaPrimeHalf
  }

  @Benchmark
  def scalajs(): Unit = {
    sPrime1 divideAndRemainder sTWO
    sPrime2 divideAndRemainder sPrime1
    sEven1 divideAndRemainder sPrimeHalf
  }

  @Benchmark
  def n_native(): Unit = {
    nPrime1 divideAndRemainder nTWO
    nPrime2 divideAndRemainder nPrime1
    nEven1 divideAndRemainder nPrimeHalf
  }

  @Benchmark
  def korinsky(): Unit = {
    kPrime1 divideAndRemainder kTWO
    kPrime2 divideAndRemainder kPrime1
    kEven1 divideAndRemainder kPrimeHalf
  }
}