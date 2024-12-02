# Build & Run Damo Partner Portal

## 1. Prerequisites

## 1.1 Software

* docker - to build image
* docker-compose - to run containers
* java21 - to build applications

## 1.2 System

1. create damo_network - to allow the containers communicate on the same network

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

## 2. Build

## 2.1 product-api
[See product-api README](product-api/README.md)

## 2.2 gateway-api
[See gateway-api README](gateway-api/README.md)

## 3. Open & Explore the Site!
```html
https://damoportal:6867/portal/products
```