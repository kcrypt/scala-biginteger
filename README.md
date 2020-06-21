# BigInteger for scala

This is optimized implementation of BigInteger on scala for scala, scala-js and scala-native.

As the base it is used scala-js implementation from https://github.com/scala-js/scala-js/commit/c82fa8c16f14a36f96af8b08313d36a6dd70b2b1
that was ported from java by Alistair Johnson that is technically GWT/IBM JDK implementation.

All this optimization mainly focused on `modPow`.
I've created a very optimized Montgomery multiplication and reduction function.

For compare performance I've included to benchmarks BigInteger implementation from OpenJDK 14.

Performance on `Intel® Core™ i7-8700B`:
```
    Benchmark                      (bits)  Mode  Cnt     Score    Error  Units
    ModPowBenchmark.java_even        8192  avgt    7  1437,115 ±  7,650  ms/op
    ModPowBenchmark.java_odd         8192  avgt    7  1460,209 ±  2,460  ms/op
    ModPowBenchmark.korinsky_even    8192  avgt    7  1283,436 ± 17,791  ms/op
    ModPowBenchmark.korinsky_odd     8192  avgt    7  1269,383 ±  5,961  ms/op
    ModPowBenchmark.native_even      8192  avgt    7   423,211 ±  0,536  ms/op
    ModPowBenchmark.native_odd       8192  avgt    7   423,299 ±  1,358  ms/op
    ModPowBenchmark.scalajs_even     8192  avgt    7  1713,470 ±  8,385  ms/op
    ModPowBenchmark.scalajs_odd      8192  avgt    7  1532,935 ±  3,465  ms/op
```
where
 - `java` means OpenJDK java implementation
 - `native` the same OpenJDK java implementation that used all JVM optimization
 - `scalajs` the base version
 - `korinsky` this code

It is clear that my implementation is the best one but it is slower than `native`
because it used `@HotSpotIntrinsicCandidate` to make faster access to arrays.