package xdu.zh.yoyoinflux.controller

import org.mockito.Mockito
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll
import xdu.zh.yoyoinflux.mq.DataProducerService
import xdu.zh.yoyoinflux.pojo.BloodPressureData
import xdu.zh.yoyoinflux.pojo.DataFactory
import xdu.zh.yoyoinflux.pojo.ElectromyogramData
import xdu.zh.yoyoinflux.pojo.HeartRateData
import xdu.zh.yoyoinflux.pojo.MuseHeadsetData
import xdu.zh.yoyoinflux.pojo.PadPhotoData
import xdu.zh.yoyoinflux.pojo.PoseDetectionData

import xdu.zh.yoyoinflux.util.InfluxDbUtil

/**
 * @author jiangzhh
 * @date 2024/3/13
 */
class EquipmentControllerTest extends Specification {

    @Shared
    def dataFactory = Mockito.mockStatic(DataFactory.class)
    @Shared
    def controller = new EquipmentController()
    @Shared
    def data1 = new HashMap<>()
    @Shared
    def data2 = new HashMap<>()
    @Shared
    def data3 = new HashMap<>()
    @Shared
    def data4 = new HashMap<>()
    @Shared
    def data5 = new HashMap<>()
    @Shared
    def data6 = new HashMap<>()


    def setup() {
        dataFactory.when(() -> DataFactory.get(1)).thenReturn(new HeartRateData())
        dataFactory.when(() -> DataFactory.get(2)).thenReturn(new ElectromyogramData())
        dataFactory.when(() -> DataFactory.get(3)).thenReturn(new BloodPressureData())
        dataFactory.when(() -> DataFactory.get(4)).thenReturn(new MuseHeadsetData())
        dataFactory.when(() -> DataFactory.get(5)).thenReturn(new PadPhotoData())
        dataFactory.when(() -> DataFactory.get(6)).thenReturn(new PoseDetectionData())
        controller.dataProducerService = Mock(DataProducerService.class)

        data1.put("cardId", 123)
        data1.put("time", 123L)
        data1.put("heartRate", 123)
        data1.put("bloodOxygen", 123)
        data1.put("microCirculation", 123)

        data2.put("cardId", 123)
        data2.put("time", 121233L)
        data2.put("above", 123.123)
        data2.put("below", 123.123)

        data3.put("cardId", 123)
        data3.put("time", 123L)
        data3.put("shrink", 12.3123)
        data3.put("diastolic", 120.3)

        data4.put("cardId", 123)
        data4.put("time", 123L)
        data4.put("leftHead", 123)
        data4.put("rightHead", 123)
        data4.put("leftEar", 123)
        data4.put("rightEar", 123)

        data5.put("cardId", 123)
        data5.put("time", 123L)
        data5.put("imagePath", "/pasdf/23er.jpg")
        data5.put("remark", "dasfa")

        data6.put("cardId", 123)
        data6.put("time", 123L)
        data6.put("result", 2)
    }

    @Unroll
    def "test add data type=#type, data=#data -> rc=#rc"() {
        when:
        def res = controller.add(type, data as Map<String, Object>)

        then:
        res.obj.type == rc
        res.code == 200

        where:
        type | data  || rc
        1    | data1 || 1
        2    | data2 || 2
        3    | data3 || 3
        4    | data4 || 4
        5    | data5 || 5
        6    | data6 || 6
    }

    @Unroll
    def "test query type=#type, cls=#cls, data=#data -> obj=#obj"() {
        given:
        def startTime = 1613257324
        def endTime = 1613257324
        def cardId = "cardId"
        controller.influxDbUtil = Mock(InfluxDbUtil.class) {
            1 * execQuery(_, cls) >> [data]
            1 * prepareQuery(*_) >> "query"
        }

        when:
        def result = controller.query(type, startTime, endTime, cardId)
        then:
        result.code == 200
        result.obj == obj

        where:
        type | cls                      | data  || obj
        1    | HeartRateData.class      | data1 || [data1]
        2    | ElectromyogramData.class | data2 || [data2]
        3    | BloodPressureData.class  | data3 || [data3]
        4    | MuseHeadsetData.class    | data4 || [data4]
        5    | PadPhotoData.class       | data5 || [data5]
        6    | PoseDetectionData.class  | data6 || [data6]
    }

    @Unroll
    def "test queryLast type=#type, cls=#cls, data=#data -> retType=#retType"() {
        given:
        def cardId = "cardId"
        controller.influxDbUtil = Mock(InfluxDbUtil.class) {
            1 * execQuery(_, cls) >> [data]
            1 * prepareQueryLast(*_) >> "query"
        }

        when:
        def result = controller.queryLast(type, cardId)
        then:
        result.code == 200
        result.obj.getType() == retType

        where:
        type | cls                      | data  || retType
        1    | HeartRateData.class      | new HeartRateData() || 1
        2    | ElectromyogramData.class | new ElectromyogramData() || 2
        3    | BloodPressureData.class  | new BloodPressureData() || 3
        4    | MuseHeadsetData.class    | new MuseHeadsetData() || 4
        5    | PadPhotoData.class       | new PadPhotoData() || 5
        6    | PoseDetectionData.class  | new PoseDetectionData() || 6
    }
}
