#!/usr/bin/env bash

# 数组的声明
myArray=("yhb" "hyw" 1)
# 数组单个元素的调用
echo "${myArray[1]}"
# 数组变量的修改
myArray[1]="zjq"
echo "${myArray[1]}"

## 数组的遍历--方式一
echo "通过遍历数组元素直接访问"
for i in ${myArray[@]}
do
    echo $i
done

## 数组的便利--方式二
echo "通过便利数组下标访问："
for (( i=0;i<${#myArray[@]};i++ ))
do
    echo "${myArray[$i]}"
done

