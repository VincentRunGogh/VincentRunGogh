# 베이스 이미지로 Python 3.12을 사용
FROM python:3.12-slim

# 작업 디렉토리 설정
WORKDIR /app

# 필요한 라이브러리들을 설치하기 위해 requirements.txt 복사
COPY requirements.txt .

# 패키지 설치
RUN pip install --no-cache-dir -r requirements.txt

# FastAPI 애플리케이션 코드를 복사
COPY . .

# Uvicorn을 사용해 FastAPI 서버 실행
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]
