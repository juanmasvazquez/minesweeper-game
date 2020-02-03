#!/bin/bash
clear
echo "Clean Instal Project"
/Users/juanmasvazquez/Development/apache-maven-3.6.0/bin/mvn clean install -Dmaven.test.skip
echo "Generating Docker Image and Run"
docker-compose -p minesweeper-game down
docker-compose -p minesweeper-game up --build -d
