# Stop and remove the existing container if it exists
docker rm -f gateway-api || true

# Build the image (Dockerfile one dir up)
docker build -t gateway-api:latest -f ../Dockerfile ../

# Run the container with the .env file
docker run -d \
  --name gateway-api \
  -p 6867:8091 \
  -v /Users/grainneomalley/damo/workspace/college/gateway-api/local/CA/out/damoportal.p12:/app/damoportal.p12 \
  --network damo_network \
  gateway-api:latest