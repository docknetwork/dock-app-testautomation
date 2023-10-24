#!/bin/bash

gradle_file="../dock-app/android/app/build.gradle"
build_number=$(grep -A1 'versionCode [0-9]*' "$gradle_file" | awk '/versionCode/ { code=$2 } /versionName/ { print code }')
version_name=$(grep -o 'versionName "[^"]*"' "$gradle_file" | awk -F'"' '{print $2}')

echo "BUILD_NUMBER=$version_name($build_number)" >> "$GITHUB_ENV"