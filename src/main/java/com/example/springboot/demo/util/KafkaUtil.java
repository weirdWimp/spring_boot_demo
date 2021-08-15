package com.example.springboot.demo.util;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

/**
 * @author guo
 * @date 2021/8/14
 */
public class KafkaUtil {

    public static void main(String[] args) {
        System.out.println("pid:" + getPid());
        KafkaProducer<String, String> producer = createProducer();
        for (int i = 0; i < 10; i++) {
            String time = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            String message = "message at " + time;

            System.out.println("message: " + message);
            ProducerRecord<String, String> record = new ProducerRecord<>("test", message);
            producer.send(record, (r, e) -> {
                if (r != null) {
                    System.out.printf("topic:%s, partition:%s, offset:%s\n", r.topic(), r.partition(), r.offset());
                }

                if (e != null) {
                    e.printStackTrace();
                }
            });

            threadSleep(Duration.ofMinutes(10));
        }
        producer.flush();
    }

    /**
     * 请注意 kafka broker 的 hostname, 创建 Producer 时，会首先访问指定的 bootstrap.servers 192.168.31.188，获取集群的元数据信息，
     * 获取到集群的 broker 的 Node(hostName,port)等信息，192.168.31.188 对应局域网内的一台机器，hostname 为 guo-lenovo,
     * 请确保该 hostname 在访问的客户端机器上能正确映射和访问 (ping guo-lenovo), 如果连接不上，请配置 hosts， 如 192.168.31.188 guo-lenovo
     *
     * @return
     */
    private static KafkaProducer<String, String> createProducer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "192.168.31.188:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("acks", "all");
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        return producer;
    }

    public static void threadSleep(Duration duration) {
        try {
            long millis = duration.toMillis();
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    public static int getPid() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String name = runtime.getName(); // format: "pid@hostname"
        try {
            return Integer.parseInt(name.substring(0, name.indexOf('@')));
        } catch (Exception e) {
            return -1;
        }
    }
}
