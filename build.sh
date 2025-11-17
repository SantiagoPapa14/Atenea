#!/bin/bash

FX=/usr/lib/jvm/java-24.0.2-openjfx/lib

mkdir -p out

javac \
  --module-path "$FX" \
  --add-modules javafx.controls,javafx.fxml \
  -d out \
  $(find src -name "*.java")

