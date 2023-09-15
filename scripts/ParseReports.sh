#!/bin/bash

# Directory containing your XML files
xml_directory="./build/reports/tests/test/xml/"

all_results=""
for ((i=1; i<=10; i=i+1))
do
    all_results+="test report"$'\n'
done

# for xml_file in "$xml_directory"/*.xml; do
#   xml_report=$(<"$xml_file")

#   total_tests=$(echo "$xml_report" | grep -o 'tests="[0-9]*"' | cut -d'"' -f2 | head -n 1)
#   failures=$(echo "$xml_report" | grep -o 'failures="[0-9]*"' | cut -d'"' -f2 | head -n 1)
#   test_name=$(echo "$xml_report" | grep -o '<testcase name="[^"]*"' | cut -d'"' -f2 | head -n 1)
#   duration=$(echo "$xml_report" | grep -o 'time="[0-9.]*"' | cut -d'"' -f2 | head -n 1)

#   # Use regex to extract numeric value from "failures"
#   failures=$(echo "$failures" | grep -o '[0-9]*')

#   if [ $failures -gt 0 ]; then
#     result_line="$test_name: Number of Tries - $total_tests, Failures - $failures, Duration - $duration secons"
#     all_results="$all_results$result_line"$'\n'
#     # echo "XML File: $xml_file"
#     # echo "Test Name: $test_name"
#     # echo "Number of Retries: $total_tests"
#     # echo "Failures: $failures"
#     # echo "Duration: $duration seconds"
#     # echo "------------------------"
#   fi
# done

echo "ALL_FAILED_TESTS=$all_results" >> "$GITHUB_ENV"