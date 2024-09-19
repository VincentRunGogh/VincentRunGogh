from fastapi import FastAPI
import uvicorn
from api.RouteAPI import api_router  # routeAPI 파일에서 라우터를 가져옵니다.

app = FastAPI()

# "/api/v1"를 기본 경로로 설정하여 라우터 등록
app.include_router(api_router, prefix="/api/v1")

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000, log_level="info")