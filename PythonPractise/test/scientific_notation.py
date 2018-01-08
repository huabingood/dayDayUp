
# coding = UTF-8

from decimal import *

str="1,2,3,4,.123,1234.,中国,1.234567899990987654321e-1"




# 使用都好将一行数据分割成数组

myRead=open("/home/huabingood/read.txt",'r')
myWrite=open("/home/huabingood/write.txt",'w')

line = myRead.readline()
while line:
    my_list = line.split(",")
    for i in range(len(my_list)):
        # 如果字符串中含有E或者e,且包含英文dot,就认为该字符串为科学计数法的数。
        if ( my_list[i].find(".") != -1 and (my_list[i].find("E") != -1 or my_list[i].find("e") != -1) ) :
            # 指定精度
            getcontext().prec=26
            # 将科学计数法转换为小数
            # print(Decimal(my_list[i]))
            # 返回值是：Decimal('0.1234567899990987654321'),将其按逗号分割，将中间的数字字符串提取出来。
            decimalStr=repr(Decimal(my_list[i]))
            my_list[i]=decimalStr.split('\'')[1]
    line=myRead.readline()


    newLine=','.join(my_list)
    myWrite.write(newLine)
    myWrite.write("\n")

myRead.close
myWrite.close





