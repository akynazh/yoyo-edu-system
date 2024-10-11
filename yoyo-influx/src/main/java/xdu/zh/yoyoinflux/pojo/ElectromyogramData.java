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
 * @Description: 手环肌电（上方肌电、下方肌电）(取值范围 500 ~ 2500)
 * @Date: Create in 22:10 2024/1/24
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Measurement(name = "health_data_electromyogram")
@Component
@JSONType(typeName = "electromyogramData")
public class ElectromyogramData implements IData{
    private final Integer type = DataTypes.Electromyogram.getType();

    @Column(tag = true)
    private String cardId;
    @Column(timestamp = true)
    private Instant time;
    /**
     * 上方肌电
     */
    @Column
    private Double above;
    /**
     * 下方肌电
     */
    @Column
    private Double below;

    @PostConstruct
    public void init() {
        DataFactory.register(type, this);
    }

    @Override
    public IData convFromData(Map<String, Object> data) {
        ElectromyogramData electromyogramData = new ElectromyogramData();
        electromyogramData.setCardId(data.get("cardId").toString());
        electromyogramData.setTime(Instant.ofEpochMilli((Long)data.get("time")));
        electromyogramData.setAbove(Double.valueOf(data.get("above").toString()));
        electromyogramData.setBelow(Double.valueOf(data.get("below").toString()));
        return electromyogramData;
    }
}
