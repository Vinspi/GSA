#! /bin/bash
echo $1
cp -r $1 ./ROOT.war && mv ./ROOT.war $2