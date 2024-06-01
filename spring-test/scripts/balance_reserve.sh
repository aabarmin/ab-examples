#!/bin/zsh

curl -X POST -H "Content-Type: application/json" -v -d '{"amount": 10, "currency": "USD"}' http://localhost:9090/balance/10/reserve