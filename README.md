# yoyo-edu-system

1. `docker-compose up -d`
2. run `yoyo-data/init/influx.sh` in container: `yoyo-influxdb`
3. run module `yoyo-flux`
4. run `yoyo-data/init/user.sh`
5. run `yoyo-data/gen_data.py`
6. visit `http://localhost:63342/yoyo-edu-system/yoyo-view/index.html?id=20009100359`
