# coding = UTF-8

from decimal import *

str1="hyw,1.23456e10"

my_list = str1.split(",")
for i in range(len(my_list)):
    # 如果字符串中含有E或者e,且包含英文dot,就认为该字符串为科学计数法的数。
    if ( my_list[i].find(".") != -1 and (my_list[i].find("E") != -1 or my_list[i].find("e") != -1) ) :
        # 指定精度
        getcontext().prec=50
        # 将科学计数法转换为小数
        # print(Decimal(my_list[i]))
        # 返回值是：Decimal('0.1234567899990987654321'),将其按逗号分割，将中间的数字字符串提取出来。
        print(Decimal(my_list[i]))

        my_list[i]=str(Decimal(my_list[i]))


newLine=','.join(my_list)

print (str1)
print("----------------")
print (newLine)

