#!/usr/bin/env bash -ex

cf create-space numbers

cf target -s numbers

cf create-service p-service-registry trial service-registry

cf service service-registry

while ! cf service service-registry | grep status | grep 'create succeeded'; do
    sleep 1
done

cf add-network-policy numbers-client --destination-app numbers-service --protocol tcp --port 8080

./mvnw clean package

cf push -f manifest.yml

curl https://user:password@numbers-client.cfapps.io