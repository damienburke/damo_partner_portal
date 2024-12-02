# Build & run product-api

## 1. Build product-api jar file and a docker image

```bash
./gradlew jibDockerBuild
```

## 2. Run product-api container & backing services (i.e. a pg container)

```bash
cd local
docker-compose -f docker-compose.base.yaml -f docker-compose.app.yaml down --volumes
docker-compose -f docker-compose.base.yaml -f docker-compose.app.yaml up -d --build
```

# Security scan
```bash
./gradlew snyk-test
```

- db encryption
- > https://dev.to/cristiancfm/encrypting-postgresql-database-columns-in-spring-boot-jpa-entities-4915

- app db encryption
- apply the Principle of Least Privilege while benefiting from the convenience of Flyway managing your schemas.

Test
Wrong password?
drop db
flyway dbuser

security scans
```bash
export SNYK_TOKEN=e6d7383b-5086-4a16-9efb-4296259b22d5
```