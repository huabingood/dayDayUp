#!/usr/bin/env bash

filePath=/home/huabingood/abc.txt
function a(){
while read line
do
    echo ${line}

done < ${filePath}    # 注意这里的符号，是<，表示读取，千万不要写成追加了。
}


function b(){
    for (( i=0;i<10;i++ ))
    do
        if [ $i -eq 5 ] ;then
            echo $i
            break
        fi

        # 添加一层循环，看看break跳出的是那一层循环
        for (( j=0;i<20;j++))
        do
            echo "我是J:$j"
            if [ $j -eq 4 ];then
                break 1
                # break 2
            fi
        done
        echo $i
    done
}
# b

function c(){
    cmd=`ls /opt`
    echo $cmd
    echo "+++++++++++++++++++++++++++"
    for i in $cmd
    do
        echo $i
    done
}
c


