#!/bin/bash

testName="Microservice test"

echo "Starting $testName..."

echo "Starting rapl.rs"
raplrs benchmark bash jmeter_start.sh

echo "Done"