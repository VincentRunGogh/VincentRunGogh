import requests
import json
from pyspark.sql import SparkSession
from pyspark.sql import types

spark = SparkSession.builder.appName("RoadDataProcessing").getOrCreate()

# 도로 데이터 설정하기
def get_road_data(min_lat, min_lng, max_lat, max_lng, region):
    overpass_url = "http://overpass-api.de/api/interpreter"
    overpass_query = f"""
    [out:json];
    (
      way["highway"~"footway|pedestrian|path|living_street|residential|track|service"]({min_lat},{min_lng},{max_lat},{max_lng});
    );
    out body;
    >;
    out skel qt;
    """
    response = requests.get(overpass_url, params={'data': overpass_query})

    if response.status_code == 200:
        data = response.json()
        return data, region
    else:
        print(f"에러코드: {response.status_code}")
        return None, region


# 도로 데이터 처리 함수
def process_road_data(data, region_name):
    nodes = {}
    ways = {}
    rows = []

    # print(data)
    if data:
        for element in data['elements']:
            if element['type'] == 'node':
                nodes[element['id']] = (element['lat'],element['lon'])
            elif element['type'] == 'way':
                ways[element['id']] = element['nodes']

        for way_id, node_ids in ways.items():
            for node_id in node_ids:
                if node_id in nodes:
                    lat,lng = nodes[node_id]
                    rows.append({
                        "way_id": str(way_id),
                        "node_id": str(node_id),
                        "lat": lat,
                        "lng": lng,
                        "region": region_name
                    })
    # spark df로 변환 처리
    schema = types.StructType([
        types.StructField("way_id", types.StringType(), True),
        types.StructField("node_id", types.StringType(), True),
        types.StructField("lat", types.DoubleType(), True),
        types.StructField("lng", types.DoubleType(), True),
        types.StructField('region', types.StringType(), True)
    ])
    # Spark DataFrame으로 변환
    df = spark.createDataFrame(rows, schema=schema)
    return df


# 우선 대전 데이터만 설정해두기
min_lat = 36.2717
min_lng = 127.2966
max_lat = 36.4334
max_lng = 127.4877
region = "Daejeon"

# 도로 데이터 가져오기
road_data,region = get_road_data(min_lat, min_lng, max_lat, max_lng, region)

# 도로 데이터를 Spark DataFrame으로 반환받기
road_df = process_road_data(road_data,region)

# 데이터 확인
if road_df:
    # road_df.show(5)
    # 추가 중복 제거 전처리
    pedestrian_df = road_df.dropDuplicates()
    # 기존 파티션 수 확인
    num_partitions = pedestrian_df.rdd.getNumPartitions()
    # print(f"기존 파티션 수: {num_partitions}")
    pedestrian_df.write.option("header", "true").csv("./pedestrian_daejeon")

    loaded_df = spark.read.option("header","true").csv("pedestrian_daejeon")
    # loaded_df.show(10)



