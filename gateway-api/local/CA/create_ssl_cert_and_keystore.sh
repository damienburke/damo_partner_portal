#!/bin/bash

rm -Rf out/*

echo "Step 1: Generating private key..."
# Generate a 2048-bit RSA private key. It is NOT encrypted, and will be secured inside the JKS
# This private key will be used to create the CSR and the certificate
openssl genrsa -out ./out/damoportal.key 2048

echo "Step 2: Generating CSR (Certificate Signing Request)..."
# A CSR is used to request a certificate from a Certificate Authority (CA)
openssl req -new -key ./out/damoportal.key -out ./out/damoportal.csr -subj "/C=IR/ST=Sligo/L=RossesPoint/O=DamosPartnerPortal/OU=IT Department/CN=damoportal/emailAddress=admin@DamoPortal"

echo "Step 3: Generating self-signed certificate..."
# Using the private key and CSR generate a self-signed certificate
# In a production scenario, you'd submit this CSR to a trusted Certificate Authority (CA), but here we'll self-sign it for local testing.
openssl x509 -req -in ./out/damoportal.csr -signkey ./out/damoportal.key -out ./out/damoportal.crt -days 3650 \
  -extfile <(printf "subjectAltName=DNS:damoportal")

echo "Step 4: Creating .p12 keystore..."
# The .p12 keystore contains both the private key and the certificate. To be imported into gateway-api
openssl pkcs12 -export -out ./out/damoportal.p12 -inkey ./out/damoportal.key -in ./out/damoportal.crt -name "gateway" -password pass:damo123

# Step 5: Import the Certificate into macOS Keychain
echo "Step 5: Importing certificate into macOS Keychain..."
# This step adds the certificate to the macOS System Keychain, making it trusted for local/browsers.
# The `-d` flag marks it as a trusted root certificate.
security add-trusted-cert -d -r trustRoot -k /Library/Keychains/System.keychain ./out/damoportal.crt

chmod 777 ./out/*

# Script completion message
echo "SSL certificate, .p12 keystore, and macOS keychain import steps completed."