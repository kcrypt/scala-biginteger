#!/bin/sh -e

# To prevent publish the same artifact at scala-native I've used this hack :(
sbt clean test \
  ++2.11.12 bigintegerNative/publishSigned bigintegerJS/publishSigned bigintegerJVM/publishSigned \
  ++2.12.11 bigintegerJS/publishSigned bigintegerJVM/publishSigned \
  ++2.13.2 bigintegerJS/publishSigned bigintegerJVM/publishSigned
