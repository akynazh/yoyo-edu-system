package xdu.zh.yoyoinflux.mq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import xdu.zh.yoyoinflux.pojo.IData;
import xdu.zh.yoyoinflux.util.InfluxDbUtil;

import javax.annotation.Resource;

/**
 * @author zh
 * @date 2024/2/29
 */
@Service
@Slf4j
public class DataConsumerService {
    @Resource
    private InfluxDbUtil influxDbUtil;

    @KafkaListener(topics = {"${kafka.topic.topic1}"}, groupId = "group1")
    public void consumeAddDataMessage(ConsumerRecord<String, String> consumerRecord) {
        IData iData = JSON.parseObject(consumerRecord.value(), IData.class);
        influxDbUtil.writePoint(iData);
        log.info("消费者消费 topic:{} partition:{} 的消息 -> {}", consumerRecord.topic(), consumerRecord.partition(), iData);
    }
}
