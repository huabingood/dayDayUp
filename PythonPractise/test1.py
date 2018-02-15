
str="1,2,3,4,123.45678999999E10"
my_tuple=str.split(",")
my_str=""
for i in range(len(my_tuple)):
    if(my_tuple[i].find("e") or my_tuple[i].find("E")):
        # my_tuple[i]=format(my_tuple[i],'.10f')
        my_tuple[i]="%.10f" %float(my_tuple[i])
        print(my_tuple[i])
    # print(type(my_tuple[i]))


my_str=','.join(tuple(my_tuple))
print(my_str)