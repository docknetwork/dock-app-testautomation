#!/bin/bash

# Get the passed build number which is the first argument, replace the space in the string with an underscore and store in $directory_name

directory_name=$(echo $1 | sed 's/ /_/g')

echo "DIRECTORY_NAME=$directory_name" >> "$GITHUB_ENV"