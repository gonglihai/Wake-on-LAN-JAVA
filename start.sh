#!/bin/bash

function changeWorkspace(){
  cd $(dirname $0)
  echo "change workspase: `pwd`"
}

changeWorkspace
/usr/bin/java Wol

# crontab -e 每周1至周5 上午9:00, 网络唤醒办公电脑
# 0 9 * * 1,2,3,4,5 /workspase/wol/start.sh
