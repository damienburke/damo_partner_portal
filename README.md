# Build & Run Damo Partner Portal

## 1. Prerequisites

## 1.1 Software

* `java21` - to build applications
* `docker` - to build image
* `docker-compose` - to run containers

## 1.2 System

1. create `damo_network` - to allow the containers communicate on the same network

```bash
if ! docker network inspect damo_network > /dev/null 2>&1; then
    docker network create damo_network
else
    echo "Network damo_network already exists."
fi

```

2. Hosts override - to avoid working with the localhost domain. Make things more realistic and avoid potentially
   breaking other localhost applications, e.g. HSTS

```bash
sudo bash -c 'echo "127.0.0.1 damoportal" >> /etc/hosts'
```

3. Clear host cache
   Via *chrome://net-internals/#dns*, or just restart browser.

## 2. Build & Run locally

Note, don't attempt to run build (i.e. the tests) at same time as app is running locally, as ports (currently) clash.

## 2.1 product-api

[See product-api README](product-api/README.md)

## 2.2 gateway-api

[See gateway-api README](gateway-api/README.md)

## 3. Open & Explore the Site!

```html
https://damoportal:6867/portal/products
```

## 3. Testing

The following Testing will run as part of the build:

* Unit Testing
* Integration Testing
* Static Application Security Testing (SAST) - snyk
* Software Composition Analysis (SCA) - snyk

### 3.1 DAST

Dynamic Application Security Testing (DAST) is long-running, and is run separate.

```bash
cd gateway-api/zap
docker-compose down --volumes
docker-compose up -d
```

### 3.2 TLS Testing

```bash
cd gateway-api/testssl
docker-compose down --volumes
docker-compose up -d
```

