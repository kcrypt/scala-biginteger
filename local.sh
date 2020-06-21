#!/bin/sh -e

sbt clean test \
  ++2.11.12 bigintegerNative/publishLocal bigintegerJS/publishLocal bigintegerJVM/publishLocal \
  ++2.12.11 bigintegerJS/publishLocal bigintegerJVM/publishLocal \
  ++2.13.2 bigintegerJS/publishLocal bigintegerJVM/publishLocal
