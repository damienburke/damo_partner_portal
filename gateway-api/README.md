# 1. Build & run gateway-api

## 1.1. Build gateway-api jar file

```bash
./gradlew build
```

## 1.2. Build gateway-api image, run gateway-api container & backing services (i.e. a redis container)

```bash
cd local
docker-compose -f docker-compose.base.yaml -f docker-compose.app.yaml down --volumes
docker-compose -f docker-compose.base.yaml -f docker-compose.app.yaml up -d --build
```