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
 * @Description: pad 照片(路径、备注)
 * @Date: Create in 22:11 2024/1/24
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Measurement(name = "health_data_pad_photo")
@Component
@JSONType(typeName = "padPhotoData")
public class PadPhotoData implements IData {
    private final Integer type = DataTypes.PadPhoto.getType();

    @Column(tag = true)
    private String cardId;
    @Column(timestamp = true)
    private Instant time;
    /**
     * 路径
     */
    @Column
    private String imagePath;
    /**
     * 备注
     */
    @Column
    private String remark;

    @PostConstruct
    public void init() {
        DataFactory.register(type, this);
    }

    @Override
    public IData convFromData(Map<String, Object> data) {
        PadPhotoData padPhotoData = new PadPhotoData();
        padPhotoData.setCardId(data.get("cardId").toString());
        padPhotoData.setTime(Instant.ofEpochMilli((Long)data.get("time")));
        padPhotoData.setImagePath(data.get("imagePath").toString());
        padPhotoData.setRemark(data.get("remark").toString());
        return padPhotoData;
    }
}
