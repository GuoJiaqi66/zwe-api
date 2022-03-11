#!/bin/bash
echo "publish----------"

process_id=`ps -ef | grep wiki.jar | grep -v grep |awk '{print $2}'`
if [ $process_id ] ; then
sudo kill -9 $process_id
fi

source /etc/profile
nohup java -jar -Dspring.profiles.active=prod ~/zwe/zwe-api/zwe-api.jar > ~/zwe/zwe-api/consoleMsg.log 2>&1 &

echo "end publish"
