#!/bin/bash

# used for cron event. Archive log data every day.
FILE=/Users/steve/Courses/memeo/backend/log-user/memeo-user.log
if test -f "$FILE"; then
	# kill flume first
	ps -A | grep [f]lume | awk '{print $1}' | xargs kill -9 $1
	cd /Users/steve/Courses/memeo/backend/log-user
	# archive the log file and creat new one
	cat memeo-user.log | gzip > "memeo-user-"`date +"%Y-%m-%d"`".log.gz"
	rm -rf memeo-user.log
	touch memeo-user.log
	# restart flume
	cd /Users/steve/big-data/apache-flume-1.9.0-bin
	nohup bin/flume-ng agent -c conf -f conf/flume2kafka.conf -n a1 >/dev/null 2>&1 &
fi
