
# coding = UTF-8

import re
from decimal import *

str="1,2,3,4,.123,1234.,中国,1.234567899990987654321e-1"

my_list = str.split(",")

# 方法一：
# 将科学计数法的字符串拼接为普通数字的字符串
# def splitJoin(strIn):
#     strOut=""
#     scientific_str=re.split("\.|E|e",strIn) # 将科学计数法的数按照小数点,E,e进行分割
#     param2len=len(scientific_str[2])
#     param3len=int(scientific_str[3])
#
#     if (scientific_str[0] == '0'):  # 判断第一个数是否为0
#         if(scientific_str[3].find('-') != -1):  # 判断阶码的符号，来决定向上拼接还是向下拼接
#             if(param3len>param2len): # 0.123E5
#                 strOut=int(scientific_str[2])+'0'*(param3len-param2len)
#             elif (param3len<param2len):  # 0.01234E2
#                 isZero=int(scientific_str[2][0:param3len-1])
#                 if (isZero != '0'):   # 0.123E2
#                     strOut=isZero+'.'+scientific_str[2][param3len-1:]
#                 else: # 0.000123E2
#                     strOut="0."+scientific_str[2][param2len-param3len-1]
#             else : # 0.12E2
#                 strOut=scientific_str[2]
#         else : # 0.123E-2
#             strOut="0."+'0'*param3len+scientific_str[2]
#
#     else: # 12.2345E2
#         if (param2len>param3len):
#             strOut=scientific_str[1]+scientific_str[2][0:]



# 方法二：
# 不在乎16位后的精度损失，强转
# def convert2float(strIn):
#     strOut=''



for i in range(len(my_list)):
    if ( my_list[i].find(".") != -1 and (my_list[i].find("E") != -1 or my_list[i].find("e") != -1) ) :    # 如果字符串中含有E或者e,且包含英文dot,就认为该字符串为科学计数法的数。

        getcontext().prec=26
        my_list[i]=Decimal(my_list[i])


        print(my_list[i])




