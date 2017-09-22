#!/bin/bash

# the env parameters
indexOfDate=3  # initial value is 0
checkPoint=2  # initial value is -1
rerunDate=("20170728" "20170729" "20170730") # must use right rerun day
today=$(date +"%Y%m%d")
logPath="/home/huabingood/hyw.log"

# write error log
function writeErrLog(){
    errorLog="${today}\t$1\n"
    # echo -e ${errorLog}
    echo -e ${errorLog} >> ${logPath}
}


# chek the index for control
# if indexOfDate minus checkPoint equals 1,that is right else it's false
# if indexOfDate equals the rerunDate length ,exit
function checkResult(){
    # lengthOfrerunDate=$(( ${#rerunDate[@]} -1 ))
    if [ ${#rerunDate[@]} -eq ${indexOfDate} ]
    then
        exit
    fi

    result=$(( indexOfDate - checkPoint))
    if [ ${result} -ne 1 ]
    then
        writeErrLog "The indexOfdate minus checkpoin is't 1;
        The function alterParameters shoule be wrong! And "
        exit
    fi

}


# run the command to rerun the data
function rerunData(){
    # TODO  # drop the path of newuser on the hdfs
    # TODO  # ruren the script dependes on the given date
    # this is test
    echo "Today rerun the date is ${rerunDate[${indexOfDate}]}"
}

# alter the indexOfDate and rerundate; let them add 1
function alterParameters(){
    # regretful the command sed can't alter parameters itself
    indexOfDateAlter=$(( indexOfDate + 1 ))
    checkPointAlter=$(( checkPoint + 1 ))
    sed -i "s@indexOfDate=${indexOfDate}@indexOfDate=${indexOfDateAlter}@g" $0
    sed -i "s@checkPoint=${checkPoint}@checkPoint=${checkPointAlter}@g" $0
}


# test and run
# writeErrLog "you are wrong!"
# echo "${#rerunDate[@]}"
checkResult
rerunData
alterParameters


# hahah
# cesshi dierci