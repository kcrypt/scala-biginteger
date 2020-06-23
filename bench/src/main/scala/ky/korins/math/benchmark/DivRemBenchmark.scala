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
    javaPrime1 divideAndRemainder  javaTWO
    javaPrime2 divideAndRemainder javaTWO
    javaPrime1 divideAndRemainder javaPrime2
    javaPrime2 divideAndRemainder javaPrime1
  }

  @Benchmark
  def scalajs(): Unit = {
    sPrime1 divideAndRemainder sTWO
    sPrime2 divideAndRemainder sTWO
    sPrime1 divideAndRemainder sPrime2
    sPrime2 divideAndRemainder sPrime1
  }

  @Benchmark
  def java_native(): Unit = {
    nPrime1 divideAndRemainder nTWO
    nPrime2 divideAndRemainder nTWO
    nPrime1 divideAndRemainder nPrime2
    nPrime2 divideAndRemainder nPrime1
  }

  @Benchmark
  def korinsky(): Unit = {
    kPrime1 divideAndRemainder kTWO
    kPrime2 divideAndRemainder kTWO
    kPrime1 divideAndRemainder kPrime2
    kPrime2 divideAndRemainder kPrime1
  }
}