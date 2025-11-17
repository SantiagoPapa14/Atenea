#!/bin/bash

FX=/usr/lib/jvm/java-24.0.2-openjfx/lib

java \
  --module-path "$FX" \
  --add-modules javafx.controls,javafx.fxml \
  -cp out \
  app.Main

