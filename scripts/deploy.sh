unzip seed-1.0-SNAPSHOT.zip 
withEnvy(['BUILD_ID=dontkill']){
sh "nohup ./seed-1.0-SNAPSHOT/bin/seed &"	
}

