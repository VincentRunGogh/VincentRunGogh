from fastapi import FastAPI
import uvicorn
from api.RouteAPI import api_router  # routeAPI 파일에서 라우터를 가져옵니다.
from fastapi.middleware.cors import CORSMiddleware
from db import mongodb


app = FastAPI()

@app.on_event("startup")
def on_app_start():
	mongodb.connect()

@app.on_event("shutdown")
async def on_app_shutdown():
	mongodb.close()

# CORS 설정
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # 모든 도메인 허용 ("*" 대신 특정 도메인만 허용하려면 ["http://example.com"] 처럼 설정)
    allow_credentials=True,
    allow_methods=["*"],  # 모든 HTTP 메서드 허용 (GET, POST, PUT, DELETE 등)
    allow_headers=["*"],  # 모든 헤더 허용
)

# "/api/v1"를 기본 경로로 설정하여 라우터 등록
app.include_router(api_router, prefix="/api/v1")



if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000, log_level="info")
