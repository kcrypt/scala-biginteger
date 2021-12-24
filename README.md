# BigInteger for scala

This is highly optimized implementation of BigInteger on scala for scala, scala-js and scala-native.

You can use it as
```
libraryDependencies += "pt.kcry" %%% "biginteger" % "x.x.x"
```
The latest version is ![maven-central]

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

This fork also fixed bugs:
 - prevented incorrect hashcode in highly concurrent environment
 - reset hashcode after `uncache()`
 - `nextProbablePrime` returns not the next prime number, just a prime number that is bigger than specified

For compare performance I've included to benchmarks BigInteger implementation from OpenJDK 14,
but I haven't used any part of inside the code.

Benchmarks:
 - `java` means OpenJDK java implementation
 - `java_native` the same OpenJDK java implementation that used all JVM optimization
 - `scalajs` the base version that is used in ScalaJS and Scala-Native
 - `korinsky` this code

All benchmarks was performed on `JDK 14.0.1, Java HotSpot(TM) 64-Bit Server VM, 14.0.1+7` at `Intel® Core™ i7-8700B`.

Short summary:
 - JVM native implementation faster at all benchmarks
 - `divRem` faster for about 5% than JVM version and 10% than ScalaJS version,
 - `modPow` faster for about 15% than JVM or ScalaJS implementation, and reduce sweepings
   for about 50% when compare with JVM implementation, and 50 time when compare with ScalaJS implementation,
 - `nextProbablePrime` and `isProbablePrime` 30 time faster for integers for 1024 bit length. 

Full version also available as [jmh-result.json](jmh-result.json)
 or via [JMH Visualizer](https://jmh.morethan.io/?gist=70a1ff7e2e4fa8b0102219e99f64b00a).

[maven-central]: https://img.shields.io/maven-central/v/pt.kcry/biginteger_2.13?style=flat-square
