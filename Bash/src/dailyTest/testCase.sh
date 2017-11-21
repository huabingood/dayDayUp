#!/usr/bin/env bash

# read -p的命令在IDEA中存在写问题
read -p "请输入您的名字：" name

case ${name} in
    "yhb")
        echo "hyw"
        ;;
    "hyw")
        echo "yhb"
        ;;
       * )   # 这个表示适配其他一切没有适配到的输入
        echo "You input ware wrong!"
        ;;
esac