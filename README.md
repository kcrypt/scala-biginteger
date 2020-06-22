# BigInteger for scala

This is optimized implementation of BigInteger on scala for scala, scala-js and scala-native.

As the base it is used scala-js implementation from https://github.com/scala-js/scala-js/commit/c82fa8c16f14a36f96af8b08313d36a6dd70b2b1
that was ported from java by Alistair Johnson that is technically GWT/Harmony/IBM JDK implementation.

All this optimization mainly focused on `modPow`.
I've created a very optimized Montgomery multiplication and reduction function.
Optimization was directed for both speed in ms per operation and for memory overhead.

For compare performance I've included to benchmarks BigInteger implementation from OpenJDK 14.

Performance on `Intel® Core™ i7-8700B`:
```
Benchmark                                                      (bits)  Mode  Cnt         Score           Error   Units
ModPowBenchmark.java_even                                        8192  avgt    7      1455,056 ±        36,266   ms/op
ModPowBenchmark.java_even:·gc.alloc.rate                         8192  avgt    7         0,638 ±         0,026  MB/sec
ModPowBenchmark.java_even:·gc.alloc.rate.norm                    8192  avgt    7   1020863,020 ±     29899,475    B/op
ModPowBenchmark.java_even:·gc.churn.G1_Eden_Space                8192  avgt    7         0,645 ±         3,841  MB/sec
ModPowBenchmark.java_even:·gc.churn.G1_Eden_Space.norm           8192  avgt    7   1027176,490 ±   6120755,885    B/op
ModPowBenchmark.java_even:·gc.count                              8192  avgt    7         1,000                  counts
ModPowBenchmark.java_even:·gc.time                               8192  avgt    7         1,000                      ms
ModPowBenchmark.java_odd                                         8192  avgt    7      1468,281 ±         3,132   ms/op
ModPowBenchmark.java_odd:·gc.alloc.rate                          8192  avgt    7         0,442 ±         0,018  MB/sec
ModPowBenchmark.java_odd:·gc.alloc.rate.norm                     8192  avgt    7    714674,776 ±     29865,100    B/op
ModPowBenchmark.java_odd:·gc.churn.G1_Eden_Space                 8192  avgt    7         0,635 ±         3,786  MB/sec
ModPowBenchmark.java_odd:·gc.churn.G1_Eden_Space.norm            8192  avgt    7   1027176,490 ±   6120755,885    B/op
ModPowBenchmark.java_odd:·gc.count                               8192  avgt    7         1,000                  counts
ModPowBenchmark.java_odd:·gc.time                                8192  avgt    7         1,000                      ms
ModPowBenchmark.korinsky_even                                    8192  avgt    7      1227,986 ±        10,386   ms/op
ModPowBenchmark.korinsky_even:·gc.alloc.rate                     8192  avgt    7         0,916 ±         0,007  MB/sec
ModPowBenchmark.korinsky_even:·gc.alloc.rate.norm                8192  avgt    7   1232983,619 ±      2529,568    B/op
ModPowBenchmark.korinsky_even:·gc.churn.G1_Eden_Space            8192  avgt    7         0,691 ±         4,120  MB/sec
ModPowBenchmark.korinsky_even:·gc.churn.G1_Eden_Space.norm       8192  avgt    7    932067,556 ±   5554019,229    B/op
ModPowBenchmark.korinsky_even:·gc.count                          8192  avgt    7         1,000                  counts
ModPowBenchmark.korinsky_even:·gc.time                           8192  avgt    7         2,000                      ms
ModPowBenchmark.korinsky_odd                                     8192  avgt    7      1223,638 ±         5,134   ms/op
ModPowBenchmark.korinsky_odd:·gc.alloc.rate                      8192  avgt    7         0,904 ±         0,004  MB/sec
ModPowBenchmark.korinsky_odd:·gc.alloc.rate.norm                 8192  avgt    7   1213131,683 ±      2489,735    B/op
ModPowBenchmark.korinsky_odd:·gc.churn.G1_Eden_Space             8192  avgt    7         0,695 ±         4,138  MB/sec
ModPowBenchmark.korinsky_odd:·gc.churn.G1_Eden_Space.norm        8192  avgt    7    932067,556 ±   5554019,229    B/op
ModPowBenchmark.korinsky_odd:·gc.count                           8192  avgt    7         1,000                  counts
ModPowBenchmark.korinsky_odd:·gc.time                            8192  avgt    7         2,000                      ms
ModPowBenchmark.native_even                                      8192  avgt    7       421,476 ±         1,294   ms/op
ModPowBenchmark.native_even:·gc.alloc.rate                       8192  avgt    7         1,615 ±         0,006  MB/sec
ModPowBenchmark.native_even:·gc.alloc.rate.norm                  8192  avgt    7    749660,714 ±      1014,241    B/op
ModPowBenchmark.native_even:·gc.churn.G1_Eden_Space              8192  avgt    7         0,754 ±         4,490  MB/sec
ModPowBenchmark.native_even:·gc.churn.G1_Eden_Space.norm         8192  avgt    7    349525,333 ±   2082757,211    B/op
ModPowBenchmark.native_even:·gc.count                            8192  avgt    7         1,000                  counts
ModPowBenchmark.native_even:·gc.time                             8192  avgt    7         2,000                      ms
ModPowBenchmark.native_odd                                       8192  avgt    7       422,308 ±         0,570   ms/op
ModPowBenchmark.native_odd:·gc.alloc.rate                        8192  avgt    7         0,957 ±         0,019  MB/sec
ModPowBenchmark.native_odd:·gc.alloc.rate.norm                   8192  avgt    7    445000,095 ±      8613,876    B/op
ModPowBenchmark.native_odd:·gc.churn.G1_Eden_Space               8192  avgt    7         1,396 ±         5,394  MB/sec
ModPowBenchmark.native_odd:·gc.churn.G1_Eden_Space.norm          8192  avgt    7    649118,476 ±   2507089,172    B/op
ModPowBenchmark.native_odd:·gc.count                             8192  avgt    7         2,000                  counts
ModPowBenchmark.native_odd:·gc.time                              8192  avgt    7         3,000                      ms
ModPowBenchmark.scalajs_even                                     8192  avgt    7      1730,524 ±         7,563   ms/op
ModPowBenchmark.scalajs_even:·gc.alloc.rate                      8192  avgt    7        22,659 ±         0,096  MB/sec
ModPowBenchmark.scalajs_even:·gc.alloc.rate.norm                 8192  avgt    7  43117135,238 ±      4682,479    B/op
ModPowBenchmark.scalajs_even:·gc.churn.G1_Eden_Space             8192  avgt    7        15,952 ±        61,359  MB/sec
ModPowBenchmark.scalajs_even:·gc.churn.G1_Eden_Space.norm        8192  avgt    7  30358771,810 ± 116771958,749    B/op
ModPowBenchmark.scalajs_even:·gc.churn.G1_Survivor_Space         8192  avgt    7         0,001 ±         0,003  MB/sec
ModPowBenchmark.scalajs_even:·gc.churn.G1_Survivor_Space.norm    8192  avgt    7      1039,810 ±      6196,034    B/op
ModPowBenchmark.scalajs_even:·gc.count                           8192  avgt    7         2,000                  counts
ModPowBenchmark.scalajs_even:·gc.time                            8192  avgt    7         3,000                      ms
ModPowBenchmark.scalajs_odd                                      8192  avgt    7      1604,774 ±         3,251   ms/op
ModPowBenchmark.scalajs_odd:·gc.alloc.rate                       8192  avgt    7        24,504 ±         0,046  MB/sec
ModPowBenchmark.scalajs_odd:·gc.alloc.rate.norm                  8192  avgt    7  43087485,878 ±      4580,834    B/op
ModPowBenchmark.scalajs_odd:·gc.churn.G1_Eden_Space              8192  avgt    7        22,195 ±        62,346  MB/sec
ModPowBenchmark.scalajs_odd:·gc.churn.G1_Eden_Space.norm         8192  avgt    7  39032706,612 ± 109643375,812    B/op
ModPowBenchmark.scalajs_odd:·gc.churn.G1_Survivor_Space          8192  avgt    7        ≈ 10⁻⁴                  MB/sec
ModPowBenchmark.scalajs_odd:·gc.churn.G1_Survivor_Space.norm     8192  avgt    7       371,592 ±      2214,247    B/op
ModPowBenchmark.scalajs_odd:·gc.count                            8192  avgt    7         3,000                  counts
ModPowBenchmark.scalajs_odd:·gc.time                             8192  avgt    7         5,000                      ms
```
where
 - `java` means OpenJDK java implementation
 - `native` the same OpenJDK java implementation that used all JVM optimization
 - `scalajs` the base version
 - `korinsky` this code

It is clear that my implementation is the best one:
 - it is faster than the base version for about 40 percent,
 - it produces 40 times less sweepings,
 - it is faster than OpenJDK implementation for about 20 percent.
but it is slower than `native` because it used `@HotSpotIntrinsicCandidate` to make faster access to arrays.
