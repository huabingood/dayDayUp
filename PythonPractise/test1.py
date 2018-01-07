str="1,2,3,4,abc,123.456789E4"
my_tuple=str.split(",")
my_str=""
for i in range(len(my_tuple)):
    if(my_tuple[i].find("e") or my_tuple[i].find("E")):
        my_tuple[i]=format(my_tuple[i],'.8f')


my_str=','.join(tuple(my_tuple))
print(my_str)