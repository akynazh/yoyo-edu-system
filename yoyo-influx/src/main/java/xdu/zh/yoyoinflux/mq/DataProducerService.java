package xdu.zh.yoyoinflux.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import xdu.zh.yoyoinflux.pojo.IData;

/**
 * @author zh
 * @date 2024/2/29
 */
@Service
@Slf4j
public class DataProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public DataProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${kafka.topic.topic1}")
    String topicAddData;

    @Value("${kafka.topic.topic2}")
    String topicSetCache;

    public void sendAddDataMessage(IData data) {
        kafkaTemplate.send(topicAddData, JSON.toJSONString(data, SerializerFeature.WriteClassName));
    }
}
