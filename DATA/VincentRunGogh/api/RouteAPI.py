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
    routeId: int
    positionList: List[Position]

class ResponseDto(BaseModel):
    status: int
    message: str
    data: dict

class DataSaveRouteResponseDto(BaseModel):
    centerLat: float
    centerLng: float
    distance: int

class Route(Model):
    routeId: Optional[str]
    positionList: List[Position]

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
async def create_art_route(request: DataSaveRouteRequestDto):

    route = Route(routeId=str(request.routeId), positionList=request.positionList)
    await mongodb.engine.save(route)
    print(f"{route.routeId}으로 생성되었습니다.")

    response_data = {
        "status": 200,
        "message": "루트를 성공적으로 저장했습니다.",
        "data": {
            "centerLat": 0.0,
            "centerLng": 0.0,
            "distance": 0
        }
    }
    return response_data