from fastapi import APIRouter
from pydantic import BaseModel
from typing import List

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

# POST 요청을 JSON 형태로 받기
@api_router.post("/rootings/art")
async def create_art_route(request: ArtRouteRequest):
    print(request)
    return {
        "positionList": request.positionList,
        "leftLat": request.leftLat,
        "leftLng": request.leftLng,
        "rightLat": request.rightLat,
        "rightLng": request.rightLng
    }
