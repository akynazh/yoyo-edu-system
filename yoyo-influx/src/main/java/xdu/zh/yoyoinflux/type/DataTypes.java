package xdu.zh.yoyoinflux.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author jiangzhh
 * @Description:
 * @Date: Create in 22:11 2024/1/24
 */
@Getter
@AllArgsConstructor
public enum DataTypes {
    HeartRate(1),
    Electromyogram(2),
    BloodPressure(3),
    MuseHeadset(4),
    PadPhoto(5),
    PoseDetection(6);

    private final int type;
}
