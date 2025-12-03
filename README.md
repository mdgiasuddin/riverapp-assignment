#### Kafka: Crate Topic

``kafka-topics --bootstrap-server kafka1:9092 --create --topic test01 --partitions 3``

#### Kafka: Show the Topic List

``kafka-topics --bootstrap-server kafka1:9092 --list``

#### Kafka: Describe the Topic

``kafka-topics --bootstrap-server kafka1:9092 --describe --topic test01``

#### Kafka: Delete a Topic

``kafka-topics --bootstrap-server kafka1:9092 --delete --topic test01``