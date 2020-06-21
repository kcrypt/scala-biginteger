#!/bin/sh -e

sbt clean test ++2.11.12 biginteger/publishLocal ++2.12.11 biginteger/publishLocal ++2.13.2 biginteger/publishLocal
