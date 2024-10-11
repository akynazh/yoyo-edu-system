package xdu.zh.yoyoinflux.pojo;

import com.alibaba.fastjson2.annotation.JSONType;

import java.util.Map;

/**
 * @Author jiangzhh
 * @Description:
 * @Date: Create in 22:52 2024/1/26
 */
@JSONType(seeAlso = {HeartRateData.class, BloodPressureData.class, ElectromyogramData.class,
        MuseHeadsetData.class, PadPhotoData.class, PoseDetectionData.class})
public interface IData {
    IData convFromData(Map<String, Object> data);
}
