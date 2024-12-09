The results/testreport.html is commited to source control, to allow viewing the latest results.

# Scan Types Info
* https://www.zaproxy.org/docs/docker/baseline-scan/
* https://www.zaproxy.org/docs/docker/full-scan/

# Setup
## Handle damoportal not being visible 

The ZAP container cannot of course see http://damoportal:6867. 
Therefore, we configure it to hit the gateway-api directly, i.e. https://gateway-api:8091
I just added the x509 cert to the zap container trusted certs.
Also, the Docker network allows us do this (i.e. allows the zap container call https://gateway-api:8091).