# 1. Build & run gateway-api

## 1.1. Build gateway-api jar file and a docker image

```bash
./gradlew jibDockerBuild
```

## 1.2. Run gateway-api container & backing services (i.e. a redis container)

```bash
cd local
docker-compose -f docker-compose.base.yaml -f docker-compose.app.yaml down --volumes
docker-compose -f docker-compose.base.yaml -f docker-compose.app.yaml up -d --build
```


