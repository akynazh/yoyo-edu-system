package xdu.zh.yoyoinflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import xdu.zh.yoyoinflux.mq.DataProducerService;
import xdu.zh.yoyoinflux.pojo.DataFactory;
import xdu.zh.yoyoinflux.pojo.IData;
import xdu.zh.yoyoinflux.pojo.Result;
import xdu.zh.yoyoinflux.type.Status;
import xdu.zh.yoyoinflux.util.InfluxDbUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @Author jiangzhh
 * @Description:
 * @Date: Create in 18:07 2024/1/24
 */
@RestController
@Slf4j
@RequestMapping("/e")
public class EquipmentController {
    @Resource
    private InfluxDbUtil influxDbUtil;
    @Resource
    private DataProducerService dataProducerService;


    /**
     * 插入一条数据
     *
     * @param type 数据类型
     * @param data 数据体
     * @return rest 结果
     */
    @PostMapping("/add/{type}")
    public Result add(@PathVariable Integer type, @RequestBody Map<String, Object> data) {
        // 工厂+策略模式，根据数据类型，构建对应的数据 POJO
        IData iData = DataFactory.get(type).convFromData(data);
        // 发送一条新增数据的消息
        dataProducerService.sendAddDataMessage(iData);
        // 若无异常则直接返回成功，若有异常将被全局异常处理器捕获
        return Result.success(iData);
    }

    /**
     * 执行查询
     *
     * @param type      数据类型
     * @param startTime 起始时间，秒级时间戳
     * @param endTime   结束时间，秒级时间戳
     * @param cardId    用户 id
     * @return Result
     */
    @GetMapping("/query/{type}")
    public Result query(@PathVariable Integer type, @RequestParam Long startTime, @RequestParam Long endTime,
                        @RequestParam String cardId) {
        String query = influxDbUtil.prepareQuery(startTime, endTime, type, cardId);
        Class<? extends IData> cls = DataFactory.get(type).getClass();
        List<? extends IData> data = influxDbUtil.execQuery(query, cls);
        return Result.success(data);
    }

    /**
     * 执行最新查询
     *
     * @param type   数据类型
     * @param cardId 用户 id
     * @return Result
     */
    @GetMapping("/queryLast/{type}")
    public Result queryLast(@PathVariable Integer type, @RequestParam String cardId) {
        List<? extends IData> data = influxDbUtil.execQuery(influxDbUtil.prepareQueryLast(type, cardId),
                DataFactory.get(type).getClass());
        if (CollectionUtils.isEmpty(data)) {
            return Result.result(Status.NOT_FOUND);
        }

        IData lastData = data.get(0);
        return Result.success(lastData);
    }
}
