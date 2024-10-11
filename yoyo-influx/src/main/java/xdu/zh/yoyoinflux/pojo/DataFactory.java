package xdu.zh.yoyoinflux.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author jiangzhh
 * @Description:
 * @Date: Create in 22:52 2024/1/26
 */
public class DataFactory {
    private static final Map<Integer, IData> DATA_REGISTERS = new HashMap<>();

    public static void register(Integer type, IData iData) {
        if (null != type) {
            DATA_REGISTERS.put(type, iData);
        }
    }

    public static IData get(Integer type) {
        return DATA_REGISTERS.get(type);
    }
}
