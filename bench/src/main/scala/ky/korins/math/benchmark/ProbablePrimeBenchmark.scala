package ky.korins.math.benchmark

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
