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

import java.time.Instant;
import java.util.Map;

/**
 * @Author jiangzhh
 * @Description: 手环血压（收缩压、舒张压）(取值范围 0 ~ 200)
 * @Date: Create in 22:11 2024/1/24
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Measurement(name = "health_data_blood_pressure")
@Component
@JSONType(typeName = "bloodPressureData")
public class BloodPressureData implements IData {
    private final Integer type = DataTypes.BloodPressure.getType();

    @Column(tag = true)
    private String cardId;
    @Column(timestamp = true)
    private Instant time;
    /**
     * 收缩压
     */
    @Column
    private Double shrink;
    /**
     * 舒张压
     */
    @Column
    private Double diastolic;

    @PostConstruct
    public void init() {
        DataFactory.register(type, this);
    }

    public BloodPressureData convFromData(Map<String, Object> data) {
        BloodPressureData bloodPressureData = new BloodPressureData();
        bloodPressureData.setCardId(data.get("cardId").toString());
        bloodPressureData.setTime(Instant.ofEpochMilli((Long)data.get("time")));
        bloodPressureData.setShrink(Double.valueOf(data.get("shrink").toString()));
        bloodPressureData.setDiastolic(Double.valueOf(data.get("diastolic").toString()));
        return bloodPressureData;
    }
}
