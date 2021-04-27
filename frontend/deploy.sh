#!/bin/sh

git pull

npm i

npm run build

cp -r ./build/* /var/www/zipline/html/

systemctl restart nginx
