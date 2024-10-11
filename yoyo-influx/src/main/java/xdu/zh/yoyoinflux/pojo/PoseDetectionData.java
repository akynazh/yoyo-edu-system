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
 * @Description: 3D 姿态检测（专注度结果）(取值范围 1，2，3，4 (分别代表不专注，正常，专注，高度集中))
 * @Date: Create in 22:12 2024/1/24
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Measurement(name = "health_data_pose_detection")
@Component
@JSONType(typeName = "poseDetectionData")
public class PoseDetectionData implements IData{
    private final Integer type = DataTypes.PoseDetection.getType();

    @Column(tag = true)
    private String cardId;
    @Column(timestamp = true)
    private Instant time;
    /**
     * 专注度结果
     */
    @Column
    private Integer result;

    @PostConstruct
    public void init() {
        DataFactory.register(type, this);
    }

    @Override
    public IData convFromData(Map<String, Object> data) {
        PoseDetectionData poseDetectionData = new PoseDetectionData();
        poseDetectionData.setCardId(data.get("cardId").toString());
        poseDetectionData.setTime(Instant.ofEpochMilli((Long)data.get("time")));
        poseDetectionData.setResult(Integer.valueOf(data.get("result").toString()));
        return poseDetectionData;
    }
}
