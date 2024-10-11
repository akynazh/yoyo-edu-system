package xdu.zh.yoyoinflux.pojo;

import com.alibaba.fastjson2.annotation.JSONType;
import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;
import xdu.zh.yoyoinflux.type.DataTypes;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

/**
 * @Author jiangzhh
 * @Description: 手环心率血氧微循环(心率、血氧、微循环)
 * @Date: Create in 22:10 2024/1/24
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Measurement(name = "health_data_heart_rate")
@Component
@JSONType(typeName = "heartRateData")
public class HeartRateData implements IData {
    private final Integer type = DataTypes.HeartRate.getType();

    @Column(tag = true)
    private String cardId;
    /**
     * 毫秒级时间戳
     */
    @Column(timestamp = true)
    private Instant time;
    /**
     * 心率
     */
    @Column
    private Double heartRate;
    /**
     * 血氧
     */
    @Column
    private Double bloodOxygen;
    /**
     * 微循环
     */
    @Column
    private Double microCirculation;

    @PostConstruct
    public void init() {
        DataFactory.register(type, this);
    }

    @Override
    public IData convFromData(Map<String, Object> data) {
        HeartRateData heartRateData = new HeartRateData();
        heartRateData.setCardId(data.get("cardId").toString());
        heartRateData.setTime(Instant.ofEpochMilli((long)data.get("time")));
        heartRateData.setHeartRate(Double.valueOf(data.get("heartRate").toString()));
        heartRateData.setBloodOxygen(Double.valueOf(data.get("bloodOxygen").toString()));
        heartRateData.setMicroCirculation(Double.valueOf(data.get("microCirculation").toString()));
        return heartRateData;
    }
}
