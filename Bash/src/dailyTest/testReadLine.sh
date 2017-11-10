#!/usr/bin/env bash

filePath=/home/huabingood/abc.txt

while read line
do
    echo ${line}

done < ${filePath}



