# Build & run product-api

## 1. Build product-api jar file and a docker image

```bash
./gradlew jibDockerBuild
```

## 2. Run product-api container & backing services (i.e. a pg container)

```bash
cd local
docker-compose -f docker-compose.base.yaml -f docker-compose.app.yaml down --volumes
docker-compose -f docker-compose.base.yaml -f docker-compose.app.yaml up -d
```

# Security scan

## Prerequisites
Obtain a token from [here](https://docs.snyk.io/snyk-api/rest-api/authentication-for-api/revoke-and-regenerate-a-snyk-api-token)

## Set env var
```bash
export SNYK_TOKEN=XXXXXXXXXXXXXX
```

## Run scan
```bash
./gradlew build
```

or 

```bash
./gradlew snyk-test
```

