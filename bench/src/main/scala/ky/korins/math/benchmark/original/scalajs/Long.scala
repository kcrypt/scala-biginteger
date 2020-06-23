package ky.korins.math.benchmark.original.scalajs

// Scala.js uses Long.divideUnsigned and Long.remainderUnsigned
// as result it switches to java's naive implementation
// this simple class prevent it to measure real cost of implementation.
object Long {
  private final val SignBit = scala.Long.MinValue

  final val MinValue = scala.Long.MinValue

  final val MaxValue = scala.Long.MaxValue

  // Intrinsic
  def divideUnsigned(dividend: scala.Long, divisor: scala.Long): scala.Long =
    divModUnsigned(dividend, divisor, isDivide = true)

  // Intrinsic
  def remainderUnsigned(dividend: scala.Long, divisor: scala.Long): scala.Long =
    divModUnsigned(dividend, divisor, isDivide = false)

  private def divModUnsigned(a: scala.Long, b: scala.Long,
    isDivide: scala.Boolean): scala.Long = {
    /* This is a much simplified (and slow) version of
     * RuntimeLong.unsignedDivModHelper.
     */

    if (b == 0L)
      throw new ArithmeticException("/ by zero")

    var shift = numberOfLeadingZeros(b) - numberOfLeadingZeros(a)
    var bShift = b << shift

    var rem = a
    var quot = 0L

    /* Invariants:
     *   bShift == b << shift == b * 2^shift
     *   quot >= 0
     *   0 <= rem < 2 * bShift
     *   quot * b + rem == a
     */
    while (shift >= 0 && rem != 0) {
      if ((rem ^ SignBit) >= (bShift ^ SignBit)) {
        rem -= bShift
        quot |= (1L << shift)
      }
      shift -= 1
      bShift >>>= 1
    }

    if (isDivide) quot
    else rem
  }

  @inline
  def numberOfLeadingZeros(l: scala.Long): Int = {
    val hi = (l >>> 32).toInt
    if (hi != 0) Integer.numberOfLeadingZeros(hi)
    else         Integer.numberOfLeadingZeros(l.toInt) + 32
  }
}
