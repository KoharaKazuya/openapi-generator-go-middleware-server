#!/bin/sh

set -eu

version="$1"

replace() {
  temp="$(mktemp)"
  cat "$2" | sed "s/0.0.0-SNAPSHOT/$1/g" >> "$temp"
  mv "$temp" "$2"
}

replace "$version" './pom.xml'
replace "$version" './Dockerfile'
