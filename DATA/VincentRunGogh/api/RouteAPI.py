from fastapi import APIRouter
from odmantic import Model
from pydantic import BaseModel
from typing import List
from db import mongodb
from typing import Optional

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

class ResponseDto(BaseModel):
    status: int
    message: str
    data: dict

class DataSaveRouteResponseDto(BaseModel):
    centerLat: float
    centerLng: float
    distance: int

# Route 모델에서 positionList를 List[dict]로 변경
class Route(Model):
    positionList: List[dict]

@api_router.post("/rootings/art", response_model=ResponseDto)
async def create_art_route(request: ArtRouteRequest):
    response_data = {
        "status": 200,
        "message": "루트화에 성공했습니다.",
        "data": {
            "positionList": request.positionList
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
