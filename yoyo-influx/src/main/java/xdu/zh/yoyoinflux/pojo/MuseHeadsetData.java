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
 * @Description: 头戴(MUSE)（左前额、右前额、左耳后、右耳后）(取值范围 -1000 ~ 1000)
 * @Date: Create in 22:11 2024/1/24
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Measurement(name = "health_data_muse_headset")
@Component
@JSONType(typeName = "museHeadsetData")
public class MuseHeadsetData implements IData {
    private final Integer type = DataTypes.MuseHeadset.getType();

    @Column(tag = true)
    private String cardId;
    @Column(timestamp = true)
    private Instant time;
    /**
     * 左前额
     */
    @Column
    private Double leftHead;
    /**
     * 右前额
     */
    @Column
    private Double rightHead;
    /**
     * 左耳后
     */
    @Column
    private Double leftEar;
    /**
     * 右耳后
     */
    @Column
    private Double rightEar;

    @PostConstruct
    public void init() {
        DataFactory.register(type, this);
    }

    @Override
    public IData convFromData(Map<String, Object> data) {
        MuseHeadsetData museHeadsetData = new MuseHeadsetData();
        museHeadsetData.setCardId(data.get("cardId").toString());
        museHeadsetData.setTime(Instant.ofEpochMilli((Long)data.get("time")));
        museHeadsetData.setLeftHead(Double.valueOf(data.get("leftHead").toString()));
        museHeadsetData.setRightHead(Double.valueOf(data.get("rightHead").toString()));
        museHeadsetData.setLeftEar(Double.valueOf(data.get("leftEar").toString()));
        museHeadsetData.setRightEar(Double.valueOf(data.get("rightEar").toString()));
        return museHeadsetData;
    }
}
