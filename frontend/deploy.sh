#!/bin/sh

nginx -s stop

git pull

npm i

npm run build

cp -r ./build/* /var/www/zipline/html/

nginx
