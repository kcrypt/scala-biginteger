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
Benchmark                                                     (bits)  Mode  Cnt         Score           Error   Units
ModPowBenchmark.java_even                                       8192  avgt    7      1446,234 ±         4,606   ms/op
ModPowBenchmark.java_even:·gc.alloc.rate                        8192  avgt    7         0,641 ±         0,019  MB/sec
ModPowBenchmark.java_even:·gc.alloc.rate.norm                   8192  avgt    7   1020864,327 ±     29898,178    B/op
ModPowBenchmark.java_even:·gc.churn.G1_Eden_Space               8192  avgt    7         0,645 ±         3,844  MB/sec
ModPowBenchmark.java_even:·gc.churn.G1_Eden_Space.norm          8192  avgt    7   1027176,490 ±   6120755,885    B/op
ModPowBenchmark.java_even:·gc.count                             8192  avgt    7         1,000                  counts
ModPowBenchmark.java_even:·gc.time                              8192  avgt    7         2,000                      ms
ModPowBenchmark.java_odd                                        8192  avgt    7      1467,695 ±        10,776   ms/op
ModPowBenchmark.java_odd:·gc.alloc.rate                         8192  avgt    7         0,442 ±         0,019  MB/sec
ModPowBenchmark.java_odd:·gc.alloc.rate.norm                    8192  avgt    7    714673,469 ±     29866,398    B/op
ModPowBenchmark.java_odd:·gc.churn.G1_Eden_Space                8192  avgt    7         0,636 ±         3,791  MB/sec
ModPowBenchmark.java_odd:·gc.churn.G1_Eden_Space.norm           8192  avgt    7   1027176,490 ±   6120755,885    B/op
ModPowBenchmark.java_odd:·gc.count                              8192  avgt    7         1,000                  counts
ModPowBenchmark.java_odd:·gc.time                               8192  avgt    7         1,000                      ms
ModPowBenchmark.korinsky_even                                   8192  avgt    7      1388,179 ±         9,320   ms/op
ModPowBenchmark.korinsky_even:·gc.alloc.rate                    8192  avgt    7         0,123 ±         0,001  MB/sec
ModPowBenchmark.korinsky_even:·gc.alloc.rate.norm               8192  avgt    7    187193,143 ±      1164,814    B/op
ModPowBenchmark.korinsky_even:·gc.count                         8192  avgt    7           ≈ 0                  counts
ModPowBenchmark.korinsky_odd                                    8192  avgt    7      1374,055 ±         6,893   ms/op
ModPowBenchmark.korinsky_odd:·gc.alloc.rate                     8192  avgt    7         0,111 ±         0,001  MB/sec
ModPowBenchmark.korinsky_odd:·gc.alloc.rate.norm                8192  avgt    7    167412,000 ±      1860,303    B/op
ModPowBenchmark.korinsky_odd:·gc.count                          8192  avgt    7           ≈ 0                  counts
ModPowBenchmark.native_even                                     8192  avgt    7       420,998 ±         0,771   ms/op
ModPowBenchmark.native_even:·gc.alloc.rate                      8192  avgt    7         1,617 ±         0,004  MB/sec
ModPowBenchmark.native_even:·gc.alloc.rate.norm                 8192  avgt    7    749661,619 ±      1013,474    B/op
ModPowBenchmark.native_even:·gc.churn.G1_Eden_Space             8192  avgt    7         0,754 ±         4,492  MB/sec
ModPowBenchmark.native_even:·gc.churn.G1_Eden_Space.norm        8192  avgt    7    349525,333 ±   2082757,211    B/op
ModPowBenchmark.native_even:·gc.count                           8192  avgt    7         1,000                  counts
ModPowBenchmark.native_even:·gc.time                            8192  avgt    7         2,000                      ms
ModPowBenchmark.native_odd                                      8192  avgt    7       421,865 ±         2,430   ms/op
ModPowBenchmark.native_odd:·gc.alloc.rate                       8192  avgt    7         0,958 ±         0,020  MB/sec
ModPowBenchmark.native_odd:·gc.alloc.rate.norm                  8192  avgt    7    445000,286 ±      8613,518    B/op
ModPowBenchmark.native_odd:·gc.churn.G1_Eden_Space              8192  avgt    7         1,399 ±         5,402  MB/sec
ModPowBenchmark.native_odd:·gc.churn.G1_Eden_Space.norm         8192  avgt    7    649118,476 ±   2507089,172    B/op
ModPowBenchmark.native_odd:·gc.count                            8192  avgt    7         2,000                  counts
ModPowBenchmark.native_odd:·gc.time                             8192  avgt    7         3,000                      ms
ModPowBenchmark.scalajs_even                                    8192  avgt    7      1645,097 ±         4,027   ms/op
ModPowBenchmark.scalajs_even:·gc.alloc.rate                     8192  avgt    7        23,942 ±         0,057  MB/sec
ModPowBenchmark.scalajs_even:·gc.alloc.rate.norm                8192  avgt    7  43117442,612 ±      4520,181    B/op
ModPowBenchmark.scalajs_even:·gc.churn.G1_Eden_Space            8192  avgt    7        21,683 ±        60,909  MB/sec
ModPowBenchmark.scalajs_even:·gc.churn.G1_Eden_Space.norm       8192  avgt    7  39032706,612 ± 109643375,812    B/op
ModPowBenchmark.scalajs_even:·gc.count                          8192  avgt    7         3,000                  counts
ModPowBenchmark.scalajs_even:·gc.time                           8192  avgt    7         5,000                      ms
ModPowBenchmark.scalajs_odd                                     8192  avgt    7      1607,433 ±         4,838   ms/op
ModPowBenchmark.scalajs_odd:·gc.alloc.rate                      8192  avgt    7        24,460 ±         0,066  MB/sec
ModPowBenchmark.scalajs_odd:·gc.alloc.rate.norm                 8192  avgt    7  43087486,531 ±      4582,666    B/op
ModPowBenchmark.scalajs_odd:·gc.churn.G1_Eden_Space             8192  avgt    7        22,151 ±        62,223  MB/sec
ModPowBenchmark.scalajs_odd:·gc.churn.G1_Eden_Space.norm        8192  avgt    7  39032706,612 ± 109643375,812    B/op
ModPowBenchmark.scalajs_odd:·gc.churn.G1_Survivor_Space         8192  avgt    7        ≈ 10⁻⁴                  MB/sec
ModPowBenchmark.scalajs_odd:·gc.churn.G1_Survivor_Space.norm    8192  avgt    7        64,327 ±       383,310    B/op
ModPowBenchmark.scalajs_odd:·gc.count                           8192  avgt    7         3,000                  counts
ModPowBenchmark.scalajs_odd:·gc.time                            8192  avgt    7         5,000                      ms
```
where
 - `java` means OpenJDK java implementation
 - `native` the same OpenJDK java implementation that used all JVM optimization
 - `scalajs` the base version
 - `korinsky` this code

It is clear that my implementation is the best one:
 - it is faster than the base version for about 25 percent,
 - it produces about 200 times less sweepings that the base version,
 - it is faster than OpenJDK implementation for about 5 percent,
 - it produces about 5 time less sweepings that OpenJDK version,
 - and it prodces 2-3 time less sweepings that OpenJDK native version.
but it is slower than `native` because it used `@HotSpotIntrinsicCandidate` to make faster access to arrays.
