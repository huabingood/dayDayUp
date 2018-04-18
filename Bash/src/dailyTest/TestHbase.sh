#!/usr/bin/env bash

status=$(hbase shell -n 'list')
echo $?
echo $status