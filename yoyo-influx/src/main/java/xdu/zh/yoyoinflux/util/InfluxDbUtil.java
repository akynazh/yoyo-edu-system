package xdu.zh.yoyoinflux.util;

import com.influxdb.annotations.Measurement;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import xdu.zh.yoyoinflux.pojo.DataFactory;
import xdu.zh.yoyoinflux.pojo.IData;

import java.util.List;

/**
 * @Author jiangzhh
 * @Description: influxdb util
 * @Date: Create in 21:11 2024/1/24
 */
@Slf4j
@Component
public class InfluxDbUtil {

    @Value("${influxdb.url}")
    private String INFLUXDB_URL;
    @Value("${influxdb.token}")
    private String INFLUXDB_TOKEN;
    @Value("${influxdb.bucket}")
    private String INFLUXDB_BUCKET;
    @Value("${influxdb.org}")
    private String INFLUXDB_ORG;

    public String prepareQuery(long startTime, long endTime, int type, String cardId) {
        String measurement = DataFactory.get(type).getClass().getAnnotation(Measurement.class).name();
        return String.format("from(bucket:\"%s\") " +
                        "|> range(start:%d, stop:%d) " +
                        "|> filter(fn: (r) => r._measurement == \"%s\" and r.cardId == \"%s\")" +
                        "|> pivot(rowKey:[\"_time\"], columnKey: [\"_field\"], valueColumn: \"_value\")",
                INFLUXDB_BUCKET, startTime, endTime, measurement, cardId);
    }

    public String prepareQueryLast(int type, String cardId) {
        String measurement = DataFactory.get(type).getClass().getAnnotation(Measurement.class).name();
        return String.format("from(bucket:\"%s\") " +
                        "|> range(start:0) " +
                        "|> filter(fn: (r) => r._measurement == \"%s\" and r.cardId == \"%s\")" +
                        "|> last()" +
                        "|> pivot(rowKey:[\"_time\"], columnKey: [\"_field\"], valueColumn: \"_value\")",
                INFLUXDB_BUCKET, measurement, cardId);
    }

    public <T> List<T> execQuery(String query, Class<T> cls) {
        try (InfluxDBClient client = InfluxDBClientFactory.create(INFLUXDB_URL,
                INFLUXDB_TOKEN.toCharArray(), INFLUXDB_ORG, INFLUXDB_BUCKET)) {
            return client.getQueryApi().query(query, INFLUXDB_ORG, cls);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void writePoint(T point) {
        try (InfluxDBClient client = InfluxDBClientFactory.create(INFLUXDB_URL,
                INFLUXDB_TOKEN.toCharArray(), INFLUXDB_ORG, INFLUXDB_BUCKET)) {
            try (WriteApi writeApi = client.makeWriteApi()) {
                writeApi.writeMeasurement(WritePrecision.MS, point);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void writePoints(List<T> points) {
        try (InfluxDBClient client = InfluxDBClientFactory.create(INFLUXDB_URL,
                INFLUXDB_TOKEN.toCharArray(), INFLUXDB_ORG, INFLUXDB_BUCKET)) {
            try (WriteApi writeApi = client.makeWriteApi()) {
                writeApi.writeMeasurements(WritePrecision.MS, points);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
