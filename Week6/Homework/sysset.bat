echo "creating directory"

mkdir C:\data\rs1
mkdir C:\data\rs2
mkdir C:\data\rs3

echo "Creating replicas"

start mongod --replSet m101 --logpath 1.log --dbpath C:\data\rs1 --port 27017 --smallfiles --oplogSize 64
start mongod --replSet m101 --logpath 2.log --dbpath C:\data\rs2 --port 27018 --smallfiles --oplogSize 64
start mongod --replSet m101 --logpath 3.log --dbpath C:\data\rs3 --port 27019 --smallfiles --oplogSize 64
