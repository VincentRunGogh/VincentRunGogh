from fastapi import APIRouter
from odmantic import Model
from pydantic import BaseModel
from typing import List
from db import mongodb
from bson import ObjectId

from pySpark.KdTreeMatchingRoute import matching_user_path_to_roads
from pySpark.PedestrianRoad import road_data_processing
from pySpark.RouteTotalDistance import calculate_total_distance
api_router = APIRouter()

# Position 모델 정의
class Position(BaseModel):
    lat: float
    lng: float

# 전체 Request DTO 모델 정의
class ArtRouteRequest(BaseModel):
    positionList: List[Position]
    leftLat: float  # 좌상단 위도
    leftLng: float  # 좌상단 경도
    rightLat: float  # 우하단 위도
    rightLng: float  # 우하단 경도

class DataSaveRouteRequestDto(BaseModel):
    positionList: List[Position]

class DataSaveDrawingDetailRequestDto(BaseModel):
    positionList: List[Position]

class DataDrawingRouteRequestDto(BaseModel):
    drawingDetailList: List[str]

class ResponseDto(BaseModel):
    status: int
    message: str
    data: dict

class DataSaveRouteResponseDto(BaseModel):
    centerLat: float
    centerLng: float
    distance: int

# Route 모델
class Route(Model):
    positionList: List[dict]

# DrawingDetail 모델
class DrawingDetail(Model):
    positionList: List[dict]

## 루트 값 추출: 공통 함수(추후 리팩토링 진행)
def generate_route(user_drawn_path,leftLat, leftLng, rightLat, rightLng):
    nearest_roads_df = road_data_processing(leftLat,leftLng,rightLat,rightLng,  "Daejeon")
    route_coords = matching_user_path_to_roads(user_drawn_path, nearest_roads_df)
    position_list = [Position(lat=coord[0], lng=coord[1]) for coord in route_coords]
    return position_list

def calculate_center_and_distance(route):
    # route가 객체가 아닌 dict 형태이므로 each["lat"]으로 접근
    lats = [each["lat"] for each in route.positionList]
    lngs = [each["lng"] for each in route.positionList]


    center_lat = sum(lats)/len(lats)
    center_lng = sum(lngs)/len(lngs)

    route_list = []
    for i in range(len(lats)):
        route_list.append((lats[i],lngs[i]))

    distance = calculate_total_distance(route_list)
    return center_lat, center_lng, distance

@api_router.post("/rootings/art", response_model=ResponseDto)
async def create_art_route(request: ArtRouteRequest):
    # 요청에서 position_list 받기
    # print(ArtRouteRequest.leftLat)
    user_drawn_path = [(each.lat, each.lng) for each in request.positionList]
    position_list = generate_route(user_drawn_path,request.leftLat,request.leftLng,request.rightLat,request.rightLng)

    response_data = {
        "status": 200,
        "message": "루트화에 성공했습니다.",
        "data": {
            "positionList": position_list
        }
    }
    return response_data

@api_router.post("/rootings", response_model=ResponseDto)
async def save_route(request: DataSaveRouteRequestDto):
    # Position 객체를 dict로 변환
    position_list_dicts = [position.dict() for position in request.positionList]

    # Route 모델에 positionList를 설정하여 생성
    route = Route(positionList=position_list_dicts)
    await mongodb.engine.save(route)
    print("생성되었습니다.")
    # route의 정보를 계산한 값 넣기
    center_lat, center_lng, distance = calculate_center_and_distance(route)

    # 저장된 route의 ID를 응답 데이터로 포함
    response_data = {
        "status": 200,
        "message": "루트를 성공적으로 저장했습니다.",
        "data": {
            "routeId": str(route.id),  # route.id는 MongoDB에서 자동 생성된 ObjectId입니다.
            "centerLat": center_lat,
            "centerLng": center_lng,
            "distance": distance
        }
    }
    return response_data

@api_router.post("/rootings/drawings", response_model=ResponseDto)
async def save_drawing_detail(request: DataSaveDrawingDetailRequestDto):
    # Position 객체를 dict로 변환
    position_list_dicts = [position.dict() for position in request.positionList]

    # Route 모델에 positionList를 설정하여 생성
    drawingDetail = DrawingDetail(positionList=position_list_dicts)
    await mongodb.engine.save(drawingDetail)
    print("생성되었습니다.")

    # 저장된 route의 ID를 응답 데이터로 포함
    response_data = {
        "status": 200,
        "message": "루트를 성공적으로 저장했습니다.",
        "data": {
            "routeId": str(drawingDetail.id),  # drawingDetail.id는 MongoDB에서 자동 생성된 ObjectId입니다.
            "centerLat": 0.0,
            "centerLng": 0.0,
            "distance": 0
        }
    }
    return response_data

@api_router.post("/rootings/drawings/routes", response_model=ResponseDto)
async def save_drawing_to_route(request: DataDrawingRouteRequestDto):

    print("테스트 ")

    id_list = request.drawingDetailList

    # 문자열을 ObjectId로 변환
    object_ids = [ObjectId(id_str) for id_str in id_list]

    # $in 연산자를 사용하여 해당 id들을 가진 모든 문서를 검색
    results = await mongodb.engine.find(DrawingDetail, {"_id": {"$in": object_ids}})

    # 드로잉 디테일 리스트를 통해서 드로잉을 만들어서 루트 만들기
    #results 활용
    # 테스트용 Position 객체 생성
    positionList = []

    test_position = Position(lat=0.0, lng=0.0)  # 예시로 lat, lng 값을 지정

    # Position 객체를 dict로 변환하여 positionList에 추가
    positionList.extend([test_position.dict()])

    # 루트 만든 것 저장
    route = Route(positionList=positionList)
    await mongodb.engine.save(route)
    print("생성되었습니다.")

    # 저장된 route의 ID를 응답 데이터로 포함
    response_data = {
        "status": 200,
        "message": "루트를 성공적으로 저장했습니다.",
        "data": {
            "routeId": str(route.id),  # route.id는 MongoDB에서 자동 생성된 ObjectId입니다.
            "centerLat": 0.0,
            "centerLng": 0.0,
            "distance": 0
        }
    }
    return response_data