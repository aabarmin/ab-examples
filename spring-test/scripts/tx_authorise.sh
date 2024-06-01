#!/bin/zsh

curl -X POST -H "Content-Type: application/json" -v -d '{"fromId": 1, "toId": 2, "amount": {"amount": 10, "currency": "USD"}}' http://localhost:8080/transactions