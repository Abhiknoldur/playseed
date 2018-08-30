ps -ef | grep seed-1.0-SNAPSHOT | grep -v grep | awk '{print $2}' | xargs kill
rm -v !("seed-1.0-SNAPSHOT.zip")
unzip seed-1.0-SNAPSHOT.zip
./seed-1.0-SNAPSHOT/bin/seed &
rm -rf seed-1.0-SNAPSHOT.zip