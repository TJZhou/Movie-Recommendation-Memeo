#!/bin/bash

# if directory exists then do backup otherwise create new directory
if [ -d "/var/www/memeo" ]; then 
    tar -czvf /var/www/memeo-frontend-backup.tar.gz /var/www/memeo
else  
    mkdir /var/www/memeo
fi
rm -rf /var/www/memeo/*