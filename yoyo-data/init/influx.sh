token="Ew2tkWHxMG48NMLxDgSmSzDD8HEBSoknHYY59u4PciQX0OOXRRQsSqlUlXGSMA5X3HHI2Aar85Tk2LrrQyW5Jw=="
org="xdu"

influx bucket create \
--org="$org" \
--token="$token" \
--name="equipments" \
--retention "3d" \
--shard-group-duration "1h"


influx bucket create \
--org="$org" \
--token="$token" \
--name="equipments-downsample" \
--retention "30d" \
--shard-group-duration "1d"


influx query \
--org="$org" \
--token="$token" '
option task = {name: "downsample_5m_precision", every: 1h, offset: 0m}
from(bucket: "equipments")
    |> range(start: -task.every)
    |> filter(fn: (r) => r._measurement == "mem" and r.host == "myHost")
    |> aggregateWindow(every: 5m, fn: mean)
    |> to(bucket: "equipments-downsample")
'


influx query \
--org="$org" \
--token="$token" '
from(bucket:"equipments") |> range(start:1706765632, stop:1706862906)
|> filter(fn: (r) => r._measurement == "health_data_heart_rate" and r.cardId == "20009100359")
|> pivot(rowKey:["_time"], columnKey: ["_field"], valueColumn: "_value")
'

influx query \
--org="$org" \
--token="$token" '
from(bucket:"equipments") |> range(start:0)
|> filter(fn: (r) => r._measurement == "health_data_heart_rate" and r.cardId == "20009100359")
|> last()
|> pivot(rowKey:["_time"], columnKey: ["_field"], valueColumn: "_value")
'