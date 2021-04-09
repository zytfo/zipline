#!/bin/sh

killall node

git pull

npm i

npm run build

nohup npm start &

