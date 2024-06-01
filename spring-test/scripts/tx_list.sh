#!/bin/zsh

curl -X GET -H "Content-Type: application/json" http://localhost:8080/transactions | jq