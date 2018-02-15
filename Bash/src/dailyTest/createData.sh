#!/usr/bin/env bash

name=("hyw" "yhb" "YHB" "XXT" "xxt" "zjq" "haha" "hehe" "abc")
my_clasa=(1.1 1.2 1.3 1.4 1.5 1.6 1.7 1.8 1.9)

for (( i=0;i<1000000;i++ ))
do
    my_random=$(( $RANDOM%9 ))
    echo -e "$i\t${name[my_random]}\t${my_clasa[my_random]}">>~/abc.txt
done