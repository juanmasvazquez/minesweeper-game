#!/bin/bash
clear
echo "Clean Instal Project"
mvn clean install -Dmaven.test.skip
echo "Generating Docker Image and Run"
docker-compose -p minesweeper-game down
docker-compose -p minesweeper-game up --build -d
