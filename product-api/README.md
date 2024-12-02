# Build & run product-api

## 1. Build product-api jar file

```bash
./gradlew build
```

## 2. Build product-api image, run product-api container & backing services (i.e. a pg container)

```bash
cd local
docker-compose -f docker-compose.base.yaml -f docker-compose.app.yaml down --volumes
docker-compose -f docker-compose.base.yaml -f docker-compose.app.yaml up -d --build
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
-> https://nvd.nist.gov/developers/request-an-api-key
-> 5a9e4696-44ea-48bd-96cb-1d94675b5bd8
export NVD_API_KEY=5a9e4696-44ea-48bd-96cb-1d94675b5bd8

report upgrades
