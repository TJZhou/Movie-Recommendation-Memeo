#!/bin/bash

cd /Users/steve/Courses/memeo/backend/log-user
cat memeo-user.log | gzip > "memeo-user-"`date +"%Y-%m-%d"`".log.gz"
rm -rf memeo-user.log
