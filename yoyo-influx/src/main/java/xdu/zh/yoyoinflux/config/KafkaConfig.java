package xdu.zh.yoyoinflux.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zh
 * @date 2024/2/29
 */
@Configuration
public class KafkaConfig {

    @Value("${kafka.topic.topic1}")
    String topic1;
    @Value("${kafka.topic.topic2}")
    String topic2;

    /**
     * 通过注入一个 NewTopic 类型的 Bean 来创建 topic，如果 topic 已存在，则会忽略。
     */
    @Bean
    public NewTopic topic1() {
        return new NewTopic(topic1, 2, (short) 1);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic(topic2, 1, (short) 1);
    }
}