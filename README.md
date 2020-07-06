# BigInteger for scala

This is optimized implementation of BigInteger on scala for scala, scala-js and scala-native.

As the base it is used scala-js implementation that was ported from java by Alistair Johnson
 that is technically GWT/Harmony/IBM JDK implementation.

All this optimization mainly focused to less sweepings and highest performance on `mod`, `modPow` and `isProbablePrime`.
As side effect I've optimized `devide`, `reminder`, `nextProbablePrime` and something near.

Summary of optimizations:
 - replaced `for` loop to `while`
 - introduced Burnikel-Ziegler division
 - switched to sieve of Eratosthenes at construction of `BigInteger`
 - introduced Lucas-Lehmer probable prime test with following ANSI X9.80 specification
 - introduced a merged montgomery multiplication and reduction

The key idea of montgomery function is splitting it to `modSquare` and `modProp`
 where each of them is merged version of multiplication and reduction that used constant sweepings.

For compare performance I've included to benchmarks BigInteger implementation from OpenJDK 14,
but I haven't used any part of inside the code.

All benchmarks was performed on `JDK 14.0.1, Java HotSpot(TM) 64-Bit Server VM, 14.0.1+7` at `Intel® Core™ i7-8700B`.
```
Benchmark                                                       (bits)  Mode  Cnt          Score            Error   Units
DivRemBenchmark.java                                              1024  avgt    3          0,001 ±          0,001   ms/op
DivRemBenchmark.java:·gc.alloc.rate                               1024  avgt    3       1660,904 ±         28,885  MB/sec
DivRemBenchmark.java:·gc.alloc.rate.norm                          1024  avgt    3       1776,073 ±          0,028    B/op
DivRemBenchmark.java:·gc.churn.G1_Eden_Space                      1024  avgt    3       1656,993 ±        614,274  MB/sec
DivRemBenchmark.java:·gc.churn.G1_Eden_Space.norm                 1024  avgt    3       1771,910 ±        682,022    B/op
DivRemBenchmark.java:·gc.churn.G1_Survivor_Space                  1024  avgt    3          0,003 ±          0,014  MB/sec
DivRemBenchmark.java:·gc.churn.G1_Survivor_Space.norm             1024  avgt    3          0,003 ±          0,015    B/op
DivRemBenchmark.java:·gc.count                                    1024  avgt    3         86,000                   counts
DivRemBenchmark.java:·gc.time                                     1024  avgt    3         76,000                       ms
DivRemBenchmark.java                                              8192  avgt    3          0,007 ±          0,001   ms/op
DivRemBenchmark.java:·gc.alloc.rate                               8192  avgt    3        997,874 ±         23,091  MB/sec
DivRemBenchmark.java:·gc.alloc.rate.norm                          8192  avgt    3       7152,292 ±          0,010    B/op
DivRemBenchmark.java:·gc.churn.G1_Eden_Space                      8192  avgt    3        982,560 ±          4,287  MB/sec
DivRemBenchmark.java:·gc.churn.G1_Eden_Space.norm                 8192  avgt    3       7042,530 ±        140,654    B/op
DivRemBenchmark.java:·gc.churn.G1_Survivor_Space                  8192  avgt    3          0,002 ±          0,008  MB/sec
DivRemBenchmark.java:·gc.churn.G1_Survivor_Space.norm             8192  avgt    3          0,011 ±          0,059    B/op
DivRemBenchmark.java:·gc.count                                    8192  avgt    3         51,000                   counts
DivRemBenchmark.java:·gc.time                                     8192  avgt    3         47,000                       ms
DivRemBenchmark.java_native                                       1024  avgt    3          0,001 ±          0,001   ms/op
DivRemBenchmark.java_native:·gc.alloc.rate                        1024  avgt    3       1779,423 ±         19,977  MB/sec
DivRemBenchmark.java_native:·gc.alloc.rate.norm                   1024  avgt    3       1776,074 ±          0,001    B/op
DivRemBenchmark.java_native:·gc.churn.G1_Eden_Space               1024  avgt    3       1792,379 ±          8,399  MB/sec
DivRemBenchmark.java_native:·gc.churn.G1_Eden_Space.norm          1024  avgt    3       1789,006 ±         15,019    B/op
DivRemBenchmark.java_native:·gc.churn.G1_Survivor_Space           1024  avgt    3          0,003 ±          0,008  MB/sec
DivRemBenchmark.java_native:·gc.churn.G1_Survivor_Space.norm      1024  avgt    3          0,003 ±          0,008    B/op
DivRemBenchmark.java_native:·gc.count                             1024  avgt    3         93,000                   counts
DivRemBenchmark.java_native:·gc.time                              1024  avgt    3         83,000                       ms
DivRemBenchmark.java_native                                       8192  avgt    3          0,006 ±          0,001   ms/op
DivRemBenchmark.java_native:·gc.alloc.rate                        8192  avgt    3       1006,543 ±         15,374  MB/sec
DivRemBenchmark.java_native:·gc.alloc.rate.norm                   8192  avgt    3       7152,295 ±          0,172    B/op
DivRemBenchmark.java_native:·gc.churn.G1_Eden_Space               8192  avgt    3       1002,284 ±        616,449  MB/sec
DivRemBenchmark.java_native:·gc.churn.G1_Eden_Space.norm          8192  avgt    3       7121,945 ±       4305,868    B/op
DivRemBenchmark.java_native:·gc.churn.G1_Survivor_Space           8192  avgt    3          0,002 ±          0,005  MB/sec
DivRemBenchmark.java_native:·gc.churn.G1_Survivor_Space.norm      8192  avgt    3          0,012 ±          0,038    B/op
DivRemBenchmark.java_native:·gc.count                             8192  avgt    3         52,000                   counts
DivRemBenchmark.java_native:·gc.time                              8192  avgt    3         49,000                       ms
DivRemBenchmark.korinsky                                          1024  avgt    3          0,002 ±          0,001   ms/op
DivRemBenchmark.korinsky:·gc.alloc.rate                           1024  avgt    3        382,711 ±          4,840  MB/sec
DivRemBenchmark.korinsky:·gc.alloc.rate.norm                      1024  avgt    3        952,042 ±          0,125    B/op
DivRemBenchmark.korinsky:·gc.churn.G1_Eden_Space                  1024  avgt    3        385,565 ±        607,039  MB/sec
DivRemBenchmark.korinsky:·gc.churn.G1_Eden_Space.norm             1024  avgt    3        959,179 ±       1521,099    B/op
DivRemBenchmark.korinsky:·gc.churn.G1_Survivor_Space              1024  avgt    3          0,070 ±          2,086  MB/sec
DivRemBenchmark.korinsky:·gc.churn.G1_Survivor_Space.norm         1024  avgt    3          0,173 ±          5,190    B/op
DivRemBenchmark.korinsky:·gc.count                                1024  avgt    3         20,000                   counts
DivRemBenchmark.korinsky:·gc.time                                 1024  avgt    3         21,000                       ms
DivRemBenchmark.korinsky                                          8192  avgt    3          0,044 ±          0,001   ms/op
DivRemBenchmark.korinsky:·gc.alloc.rate                           8192  avgt    3         94,063 ±          0,938  MB/sec
DivRemBenchmark.korinsky:·gc.alloc.rate.norm                      8192  avgt    3       4536,196 ±          1,215    B/op
DivRemBenchmark.korinsky:·gc.churn.G1_Eden_Space                  8192  avgt    3         96,342 ±        608,916  MB/sec
DivRemBenchmark.korinsky:·gc.churn.G1_Eden_Space.norm             8192  avgt    3       4645,558 ±      29329,709    B/op
DivRemBenchmark.korinsky:·gc.churn.G1_Survivor_Space              8192  avgt    3          0,001 ±          0,031  MB/sec
DivRemBenchmark.korinsky:·gc.churn.G1_Survivor_Space.norm         8192  avgt    3          0,059 ±          1,496    B/op
DivRemBenchmark.korinsky:·gc.count                                8192  avgt    3          5,000                   counts
DivRemBenchmark.korinsky:·gc.time                                 8192  avgt    3          8,000                       ms
DivRemBenchmark.scalajs                                           1024  avgt    3          0,002 ±          0,001   ms/op
DivRemBenchmark.scalajs:·gc.alloc.rate                            1024  avgt    3        454,416 ±          8,632  MB/sec
DivRemBenchmark.scalajs:·gc.alloc.rate.norm                       1024  avgt    3        952,040 ±          0,002    B/op
DivRemBenchmark.scalajs:·gc.churn.G1_Eden_Space                   1024  avgt    3        462,592 ±          2,880  MB/sec
DivRemBenchmark.scalajs:·gc.churn.G1_Eden_Space.norm              1024  avgt    3        969,171 ±         22,659    B/op
DivRemBenchmark.scalajs:·gc.churn.G1_Survivor_Space               1024  avgt    3          0,004 ±          0,089  MB/sec
DivRemBenchmark.scalajs:·gc.churn.G1_Survivor_Space.norm          1024  avgt    3          0,008 ±          0,186    B/op
DivRemBenchmark.scalajs:·gc.count                                 1024  avgt    3         24,000                   counts
DivRemBenchmark.scalajs:·gc.time                                  1024  avgt    3         23,000                       ms
DivRemBenchmark.scalajs                                           8192  avgt    3          0,042 ±          0,001   ms/op
DivRemBenchmark.scalajs:·gc.alloc.rate                            8192  avgt    3         97,416 ±          3,129  MB/sec
DivRemBenchmark.scalajs:·gc.alloc.rate.norm                       8192  avgt    3       4536,189 ±          1,177    B/op
DivRemBenchmark.scalajs:·gc.churn.G1_Eden_Space                   8192  avgt    3         96,341 ±        608,695  MB/sec
DivRemBenchmark.scalajs:·gc.churn.G1_Eden_Space.norm              8192  avgt    3       4487,526 ±      28432,460    B/op
DivRemBenchmark.scalajs:·gc.churn.G1_Survivor_Space               8192  avgt    3          0,002 ±          0,034  MB/sec
DivRemBenchmark.scalajs:·gc.churn.G1_Survivor_Space.norm          8192  avgt    3          0,089 ±          1,580    B/op
DivRemBenchmark.scalajs:·gc.count                                 8192  avgt    3          5,000                   counts
DivRemBenchmark.scalajs:·gc.time                                  8192  avgt    3          8,000                       ms
ModBenchmark.java                                                 1024  avgt    3          0,002 ±          0,001   ms/op
ModBenchmark.java:·gc.alloc.rate                                  1024  avgt    3        669,200 ±          3,656  MB/sec
ModBenchmark.java:·gc.alloc.rate.norm                             1024  avgt    3       1120,047 ±          0,043    B/op
ModBenchmark.java:·gc.churn.G1_Eden_Space                         1024  avgt    3        674,572 ±        607,608  MB/sec
ModBenchmark.java:·gc.churn.G1_Eden_Space.norm                    1024  avgt    3       1129,045 ±       1021,267    B/op
ModBenchmark.java:·gc.churn.G1_Survivor_Space                     1024  avgt    3          0,003 ±          0,060  MB/sec
ModBenchmark.java:·gc.churn.G1_Survivor_Space.norm                1024  avgt    3          0,004 ±          0,100    B/op
ModBenchmark.java:·gc.count                                       1024  avgt    3         35,000                   counts
ModBenchmark.java:·gc.time                                        1024  avgt    3         33,000                       ms
ModBenchmark.java                                                 8192  avgt    3          0,011 ±          0,001   ms/op
ModBenchmark.java:·gc.alloc.rate                                  8192  avgt    3        375,964 ±          7,019  MB/sec
ModBenchmark.java:·gc.alloc.rate.norm                             8192  avgt    3       4704,214 ±          0,626    B/op
ModBenchmark.java:·gc.churn.G1_Eden_Space                         8192  avgt    3        385,520 ±        608,686  MB/sec
ModBenchmark.java:·gc.churn.G1_Eden_Space.norm                    8192  avgt    3       4823,509 ±       7534,546    B/op
ModBenchmark.java:·gc.churn.G1_Survivor_Space                     8192  avgt    3          0,071 ±          2,127  MB/sec
ModBenchmark.java:·gc.churn.G1_Survivor_Space.norm                8192  avgt    3          0,882 ±         26,589    B/op
ModBenchmark.java:·gc.count                                       8192  avgt    3         20,000                   counts
ModBenchmark.java:·gc.time                                        8192  avgt    3         22,000                       ms
ModBenchmark.java_native                                          1024  avgt    3          0,001 ±          0,001   ms/op
ModBenchmark.java_native:·gc.alloc.rate                           1024  avgt    3        691,255 ±         15,722  MB/sec
ModBenchmark.java_native:·gc.alloc.rate.norm                      1024  avgt    3       1120,047 ±          0,002    B/op
ModBenchmark.java_native:·gc.churn.G1_Eden_Space                  1024  avgt    3        693,657 ±          0,455  MB/sec
ModBenchmark.java_native:·gc.churn.G1_Eden_Space.norm             1024  avgt    3       1123,939 ±         25,588    B/op
ModBenchmark.java_native:·gc.churn.G1_Survivor_Space              1024  avgt    3          0,003 ±          0,062  MB/sec
ModBenchmark.java_native:·gc.churn.G1_Survivor_Space.norm         1024  avgt    3          0,005 ±          0,101    B/op
ModBenchmark.java_native:·gc.count                                1024  avgt    3         36,000                   counts
ModBenchmark.java_native:·gc.time                                 1024  avgt    3         35,000                       ms
ModBenchmark.java_native                                          8192  avgt    3          0,011 ±          0,001   ms/op
ModBenchmark.java_native:·gc.alloc.rate                           8192  avgt    3        374,781 ±          6,380  MB/sec
ModBenchmark.java_native:·gc.alloc.rate.norm                      8192  avgt    3       4704,214 ±          0,629    B/op
ModBenchmark.java_native:·gc.churn.G1_Eden_Space                  8192  avgt    3        385,593 ±        608,549  MB/sec
ModBenchmark.java_native:·gc.churn.G1_Eden_Space.norm             8192  avgt    3       4839,677 ±       7561,740    B/op
ModBenchmark.java_native:·gc.churn.G1_Survivor_Space              8192  avgt    3          0,071 ±          2,136  MB/sec
ModBenchmark.java_native:·gc.churn.G1_Survivor_Space.norm         8192  avgt    3          0,891 ±         26,796    B/op
ModBenchmark.java_native:·gc.count                                8192  avgt    3         20,000                   counts
ModBenchmark.java_native:·gc.time                                 8192  avgt    3         20,000                       ms
ModBenchmark.korinsky                                             1024  avgt    3          0,004 ±          0,001   ms/op
ModBenchmark.korinsky:·gc.alloc.rate                              1024  avgt    3         19,533 ±          1,181  MB/sec
ModBenchmark.korinsky:·gc.alloc.rate.norm                         1024  avgt    3         96,004 ±          0,125    B/op
ModBenchmark.korinsky:·gc.churn.G1_Eden_Space                     1024  avgt    3         19,278 ±        609,177  MB/sec
ModBenchmark.korinsky:·gc.churn.G1_Eden_Space.norm                1024  avgt    3         94,644 ±       2990,667    B/op
ModBenchmark.korinsky:·gc.count                                   1024  avgt    3          1,000                   counts
ModBenchmark.korinsky:·gc.time                                    1024  avgt    3          2,000                       ms
ModBenchmark.korinsky                                             8192  avgt    3          0,099 ±          0,003   ms/op
ModBenchmark.korinsky:·gc.alloc.rate                              8192  avgt    3          0,887 ±          0,221  MB/sec
ModBenchmark.korinsky:·gc.alloc.rate.norm                         8192  avgt    3         96,816 ±         25,659    B/op
ModBenchmark.korinsky:·gc.churn.G1_Eden_Space                     8192  avgt    3          1,521 ±         48,071  MB/sec
ModBenchmark.korinsky:·gc.churn.G1_Eden_Space.norm                8192  avgt    3        166,233 ±       5252,801    B/op
ModBenchmark.korinsky:·gc.count                                   8192  avgt    3          1,000                   counts
ModBenchmark.korinsky:·gc.time                                    8192  avgt    3          1,000                       ms
ModBenchmark.scalajs                                              1024  avgt    3          0,004 ±          0,001   ms/op
ModBenchmark.scalajs:·gc.alloc.rate                               1024  avgt    3         20,919 ±          1,002  MB/sec
ModBenchmark.scalajs:·gc.alloc.rate.norm                          1024  avgt    3         96,004 ±          0,117    B/op
ModBenchmark.scalajs:·gc.churn.G1_Eden_Space                      1024  avgt    3         19,275 ±        609,057  MB/sec
ModBenchmark.scalajs:·gc.churn.G1_Eden_Space.norm                 1024  avgt    3         88,266 ±       2789,124    B/op
ModBenchmark.scalajs:·gc.count                                    1024  avgt    3          1,000                   counts
ModBenchmark.scalajs:·gc.time                                     1024  avgt    3          2,000                       ms
ModBenchmark.scalajs                                              8192  avgt    3          0,102 ±          0,006   ms/op
ModBenchmark.scalajs:·gc.alloc.rate                               8192  avgt    3          0,863 ±          0,183  MB/sec
ModBenchmark.scalajs:·gc.alloc.rate.norm                          8192  avgt    3         96,841 ±         26,443    B/op
ModBenchmark.scalajs:·gc.churn.G1_Eden_Space                      8192  avgt    3          1,521 ±         48,066  MB/sec
ModBenchmark.scalajs:·gc.churn.G1_Eden_Space.norm                 8192  avgt    3        171,315 ±       5413,391    B/op
ModBenchmark.scalajs:·gc.count                                    8192  avgt    3          1,000                   counts
ModBenchmark.scalajs:·gc.time                                     8192  avgt    3          1,000                       ms
ModPowBenchmark.java                                              1024  avgt    3          7,862 ±          0,047   ms/op
ModPowBenchmark.java:·gc.alloc.rate                               1024  avgt    3         10,502 ±          0,082  MB/sec
ModPowBenchmark.java:·gc.alloc.rate.norm                          1024  avgt    3      90976,346 ±          0,003    B/op
ModPowBenchmark.java:·gc.count                                    1024  avgt    3            ≈ 0                   counts
ModPowBenchmark.java                                              8192  avgt    3       2905,032 ±        125,210   ms/op
ModPowBenchmark.java:·gc.alloc.rate                               8192  avgt    3          0,549 ±          0,204  MB/sec
ModPowBenchmark.java:·gc.alloc.rate.norm                          8192  avgt    3    1745982,000 ±     647401,430    B/op
ModPowBenchmark.java:·gc.churn.G1_Eden_Space                      8192  avgt    3          1,319 ±         41,676  MB/sec
ModPowBenchmark.java:·gc.churn.G1_Eden_Space.norm                 8192  avgt    3    4194304,000 ±  132536041,006    B/op
ModPowBenchmark.java:·gc.count                                    8192  avgt    3          1,000                   counts
ModPowBenchmark.java:·gc.time                                     8192  avgt    3          1,000                       ms
ModPowBenchmark.java_native                                       1024  avgt    3          1,788 ±          0,125   ms/op
ModPowBenchmark.java_native:·gc.alloc.rate                        1024  avgt    3         28,249 ±          1,838  MB/sec
ModPowBenchmark.java_native:·gc.alloc.rate.norm                   1024  avgt    3      55651,260 ±         50,274    B/op
ModPowBenchmark.java_native:·gc.churn.G1_Eden_Space               1024  avgt    3         38,553 ±        609,117  MB/sec
ModPowBenchmark.java_native:·gc.churn.G1_Eden_Space.norm          1024  avgt    3      75986,249 ±    1200573,423    B/op
ModPowBenchmark.java_native:·gc.count                             1024  avgt    3          2,000                   counts
ModPowBenchmark.java_native:·gc.time                              1024  avgt    3          3,000                       ms
ModPowBenchmark.java_native                                       8192  avgt    3        840,277 ±         16,668   ms/op
ModPowBenchmark.java_native:·gc.alloc.rate                        8192  avgt    3          1,297 ±          0,213  MB/sec
ModPowBenchmark.java_native:·gc.alloc.rate.norm                   8192  avgt    3    1199770,000 ±     215800,477    B/op
ModPowBenchmark.java_native:·gc.churn.G1_Eden_Space               8192  avgt    3          1,509 ±         47,697  MB/sec
ModPowBenchmark.java_native:·gc.churn.G1_Eden_Space.norm          8192  avgt    3    1398101,333 ±   44178680,335    B/op
ModPowBenchmark.java_native:·gc.count                             8192  avgt    3          1,000                   counts
ModPowBenchmark.java_native:·gc.time                              8192  avgt    3          1,000                       ms
ModPowBenchmark.korinsky                                          1024  avgt    3          6,744 ±          0,134   ms/op
ModPowBenchmark.korinsky:·gc.alloc.rate                           1024  avgt    3          3,157 ±          0,085  MB/sec
ModPowBenchmark.korinsky:·gc.alloc.rate.norm                      1024  avgt    3      23450,592 ±        325,314    B/op
ModPowBenchmark.korinsky:·gc.churn.G1_Eden_Space                  1024  avgt    3          1,775 ±         56,074  MB/sec
ModPowBenchmark.korinsky:·gc.churn.G1_Eden_Space.norm             1024  avgt    3      13171,883 ±     416219,061    B/op
ModPowBenchmark.korinsky:·gc.count                                1024  avgt    3          1,000                   counts
ModPowBenchmark.korinsky:·gc.time                                 1024  avgt    3          1,000                       ms
ModPowBenchmark.korinsky                                          8192  avgt    3       2709,812 ±         86,265   ms/op
ModPowBenchmark.korinsky:·gc.alloc.rate                           8192  avgt    3          0,054 ±          0,001  MB/sec
ModPowBenchmark.korinsky:·gc.alloc.rate.norm                      8192  avgt    3     159494,000 ±          0,001    B/op
ModPowBenchmark.korinsky:·gc.count                                8192  avgt    3            ≈ 0                   counts
ModPowBenchmark.scalajs                                           1024  avgt    3          7,456 ±          0,094   ms/op
ModPowBenchmark.scalajs:·gc.alloc.rate                            1024  avgt    3        196,934 ±          3,403  MB/sec
ModPowBenchmark.scalajs:·gc.alloc.rate.norm                       1024  avgt    3    1617498,569 ±        244,942    B/op
ModPowBenchmark.scalajs:·gc.churn.G1_Eden_Space                   1024  avgt    3        192,653 ±        608,253  MB/sec
ModPowBenchmark.scalajs:·gc.churn.G1_Eden_Space.norm              1024  avgt    3    1582246,176 ±    4979657,994    B/op
ModPowBenchmark.scalajs:·gc.churn.G1_Survivor_Space               1024  avgt    3          0,071 ±          2,130  MB/sec
ModPowBenchmark.scalajs:·gc.churn.G1_Survivor_Space.norm          1024  avgt    3        587,471 ±      17515,535    B/op
ModPowBenchmark.scalajs:·gc.count                                 1024  avgt    3         10,000                   counts
ModPowBenchmark.scalajs:·gc.time                                  1024  avgt    3         15,000                       ms
ModPowBenchmark.scalajs                                           8192  avgt    3       3209,106 ±         84,629   ms/op
ModPowBenchmark.scalajs:·gc.alloc.rate                            8192  avgt    3         24,612 ±          0,591  MB/sec
ModPowBenchmark.scalajs:·gc.alloc.rate.norm                       8192  avgt    3   86087812,667 ±      70255,269    B/op
ModPowBenchmark.scalajs:·gc.churn.G1_Eden_Space                   8192  avgt    3         30,355 ±        479,589  MB/sec
ModPowBenchmark.scalajs:·gc.churn.G1_Eden_Space.norm              8192  avgt    3  106255701,333 ± 1678789852,745    B/op
ModPowBenchmark.scalajs:·gc.count                                 8192  avgt    3          2,000                   counts
ModPowBenchmark.scalajs:·gc.time                                  8192  avgt    3          4,000                       ms
MultiplyBenchmark.java                                            1024  avgt    3          0,002 ±          0,001   ms/op
MultiplyBenchmark.java:·gc.alloc.rate                             1024  avgt    3        404,881 ±         39,595  MB/sec
MultiplyBenchmark.java:·gc.alloc.rate.norm                        1024  avgt    3       1008,045 ±          0,085    B/op
MultiplyBenchmark.java:·gc.churn.G1_Eden_Space                    1024  avgt    3        405,011 ±          1,774  MB/sec
MultiplyBenchmark.java:·gc.churn.G1_Eden_Space.norm               1024  avgt    3       1008,386 ±         94,553    B/op
MultiplyBenchmark.java:·gc.churn.G1_Survivor_Space                1024  avgt    3          0,070 ±          2,088  MB/sec
MultiplyBenchmark.java:·gc.churn.G1_Survivor_Space.norm           1024  avgt    3          0,174 ±          5,231    B/op
MultiplyBenchmark.java:·gc.count                                  1024  avgt    3         21,000                   counts
MultiplyBenchmark.java:·gc.time                                   1024  avgt    3         21,000                       ms
MultiplyBenchmark.java                                            8192  avgt    3          0,078 ±          0,002   ms/op
MultiplyBenchmark.java:·gc.alloc.rate                             8192  avgt    3       1569,242 ±         33,227  MB/sec
MultiplyBenchmark.java:·gc.alloc.rate.norm                        8192  avgt    3     134085,527 ±          0,076    B/op
MultiplyBenchmark.java:·gc.churn.G1_Eden_Space                    8192  avgt    3       1562,008 ±          6,378  MB/sec
MultiplyBenchmark.java:·gc.churn.G1_Eden_Space.norm               8192  avgt    3     133467,529 ±       2970,603    B/op
MultiplyBenchmark.java:·gc.churn.G1_Survivor_Space                8192  avgt    3          0,003 ±          0,013  MB/sec
MultiplyBenchmark.java:·gc.churn.G1_Survivor_Space.norm           8192  avgt    3          0,274 ±          1,121    B/op
MultiplyBenchmark.java:·gc.count                                  8192  avgt    3         81,000                   counts
MultiplyBenchmark.java:·gc.time                                   8192  avgt    3         76,000                       ms
MultiplyBenchmark.java_native                                     1024  avgt    3          0,001 ±          0,001   ms/op
MultiplyBenchmark.java_native:·gc.alloc.rate                      1024  avgt    3       1439,974 ±         21,779  MB/sec
MultiplyBenchmark.java_native:·gc.alloc.rate.norm                 1024  avgt    3       1008,041 ±          0,017    B/op
MultiplyBenchmark.java_native:·gc.churn.G1_Eden_Space             1024  avgt    3       1427,205 ±        615,058  MB/sec
MultiplyBenchmark.java_native:·gc.churn.G1_Eden_Space.norm        1024  avgt    3        999,090 ±        415,671    B/op
MultiplyBenchmark.java_native:·gc.churn.G1_Survivor_Space         1024  avgt    3          0,003 ±          0,011  MB/sec
MultiplyBenchmark.java_native:·gc.churn.G1_Survivor_Space.norm    1024  avgt    3          0,002 ±          0,008    B/op
MultiplyBenchmark.java_native:·gc.count                           1024  avgt    3         74,000                   counts
MultiplyBenchmark.java_native:·gc.time                            1024  avgt    3         67,000                       ms
MultiplyBenchmark.java_native                                     8192  avgt    3          0,033 ±          0,001   ms/op
MultiplyBenchmark.java_native:·gc.alloc.rate                      8192  avgt    3       3684,998 ±        153,472  MB/sec
MultiplyBenchmark.java_native:·gc.alloc.rate.norm                 8192  avgt    3     134084,974 ±          2,135    B/op
MultiplyBenchmark.java_native:·gc.churn.G1_Eden_Space             8192  avgt    3       3684,279 ±        602,218  MB/sec
MultiplyBenchmark.java_native:·gc.churn.G1_Eden_Space.norm        8192  avgt    3     134059,820 ±      24204,771    B/op
MultiplyBenchmark.java_native:·gc.churn.G1_Survivor_Space         8192  avgt    3          0,007 ±          0,014  MB/sec
MultiplyBenchmark.java_native:·gc.churn.G1_Survivor_Space.norm    8192  avgt    3          0,267 ±          0,526    B/op
MultiplyBenchmark.java_native:·gc.count                           8192  avgt    3        191,000                   counts
MultiplyBenchmark.java_native:·gc.time                            8192  avgt    3        178,000                       ms
MultiplyBenchmark.korinsky                                        1024  avgt    3          0,002 ±          0,001   ms/op
MultiplyBenchmark.korinsky:·gc.alloc.rate                         1024  avgt    3        554,498 ±         18,388  MB/sec
MultiplyBenchmark.korinsky:·gc.alloc.rate.norm                    1024  avgt    3        976,041 ±          0,044    B/op
MultiplyBenchmark.korinsky:·gc.churn.G1_Eden_Space                1024  avgt    3        559,329 ±        608,548  MB/sec
MultiplyBenchmark.korinsky:·gc.churn.G1_Eden_Space.norm           1024  avgt    3        984,538 ±       1068,058    B/op
MultiplyBenchmark.korinsky:·gc.churn.G1_Survivor_Space            1024  avgt    3          0,003 ±          0,054  MB/sec
MultiplyBenchmark.korinsky:·gc.churn.G1_Survivor_Space.norm       1024  avgt    3          0,005 ±          0,096    B/op
MultiplyBenchmark.korinsky:·gc.count                              1024  avgt    3         29,000                   counts
MultiplyBenchmark.korinsky:·gc.time                               1024  avgt    3         27,000                       ms
MultiplyBenchmark.korinsky                                        8192  avgt    3          0,074 ±          0,001   ms/op
MultiplyBenchmark.korinsky:·gc.alloc.rate                         8192  avgt    3       2408,271 ±         17,605  MB/sec
MultiplyBenchmark.korinsky:·gc.alloc.rate.norm                    8192  avgt    3     194999,948 ±          3,582    B/op
MultiplyBenchmark.korinsky:·gc.churn.G1_Eden_Space                8192  avgt    3       2391,391 ±        619,092  MB/sec
MultiplyBenchmark.korinsky:·gc.churn.G1_Eden_Space.norm           8192  avgt    3     193632,508 ±      48890,383    B/op
MultiplyBenchmark.korinsky:·gc.churn.G1_Survivor_Space            8192  avgt    3          0,005 ±          0,015  MB/sec
MultiplyBenchmark.korinsky:·gc.churn.G1_Survivor_Space.norm       8192  avgt    3          0,435 ±          1,251    B/op
MultiplyBenchmark.korinsky:·gc.count                              8192  avgt    3        124,000                   counts
MultiplyBenchmark.korinsky:·gc.time                               8192  avgt    3        118,000                       ms
MultiplyBenchmark.scalajs                                         1024  avgt    3          0,002 ±          0,001   ms/op
MultiplyBenchmark.scalajs:·gc.alloc.rate                          1024  avgt    3        528,021 ±          1,038  MB/sec
MultiplyBenchmark.scalajs:·gc.alloc.rate.norm                     1024  avgt    3        976,041 ±          0,045    B/op
MultiplyBenchmark.scalajs:·gc.churn.G1_Eden_Space                 1024  avgt    3        539,629 ±        605,608  MB/sec
MultiplyBenchmark.scalajs:·gc.churn.G1_Eden_Space.norm            1024  avgt    3        997,502 ±       1121,041    B/op
MultiplyBenchmark.scalajs:·gc.churn.G1_Survivor_Space             1024  avgt    3          0,002 ±          0,059  MB/sec
MultiplyBenchmark.scalajs:·gc.churn.G1_Survivor_Space.norm        1024  avgt    3          0,005 ±          0,109    B/op
MultiplyBenchmark.scalajs:·gc.count                               1024  avgt    3         28,000                   counts
MultiplyBenchmark.scalajs:·gc.time                                1024  avgt    3         28,000                       ms
MultiplyBenchmark.scalajs                                         8192  avgt    3          0,137 ±          0,002   ms/op
MultiplyBenchmark.scalajs:·gc.alloc.rate                          8192  avgt    3       1551,192 ±         28,030  MB/sec
MultiplyBenchmark.scalajs:·gc.alloc.rate.norm                     8192  avgt    3     233457,606 ±          3,803    B/op
MultiplyBenchmark.scalajs:·gc.churn.G1_Eden_Space                 8192  avgt    3       1541,454 ±        600,576  MB/sec
MultiplyBenchmark.scalajs:·gc.churn.G1_Eden_Space.norm            8192  avgt    3     231995,355 ±      94457,386    B/op
MultiplyBenchmark.scalajs:·gc.churn.G1_Survivor_Space             8192  avgt    3          0,004 ±          0,012  MB/sec
MultiplyBenchmark.scalajs:·gc.churn.G1_Survivor_Space.norm        8192  avgt    3          0,601 ±          1,815    B/op
MultiplyBenchmark.scalajs:·gc.count                               8192  avgt    3         80,000                   counts
MultiplyBenchmark.scalajs:·gc.time                                8192  avgt    3         75,000                       ms
```
where
 - `java` means OpenJDK java implementation
 - `java_native` the same OpenJDK java implementation that used all JVM optimization
 - `scalajs` the base version
 - `korinsky` this code
