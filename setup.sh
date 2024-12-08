docker exec -it pulsar_c35 bin/pulsar-admin topics create-partitioned-topic  persistent://public/default/orders-topic --partitions 1
docker exec -it pulsar_c35 bin/pulsar-admin topics create-partitioned-topic  persistent://public/default/users-topic  --partitions 1
docker exec -it pulsar_c35 bin/pulsar-admin topics create-partitioned-topic  persistent://public/default/items-topic  --partitions 1

docker exec -it pulsar_c35 bin/pulsar-admin topics create-subscription --subscription users-sub persistent://public/default/users-topic
docker exec -it pulsar_c35 bin/pulsar-admin topics create-subscription --subscription items-sub persistent://public/default/items-topic
docker exec -it pulsar_c35 bin/pulsar-admin topics create-subscription --subscription orders-sub persistent://public/default/orders-topic

docker exec -it pulsar_c35 bin/pulsar-admin topics list public/default
docker exec -it pulsar_c35 pulsar-admin topics subscriptions persistent://public/default/users-topic
