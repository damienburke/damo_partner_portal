

## System Setup

1. create damo_network
```bash
docker create network damo_network
```

2. Hosts override:

```bash
sudo bash -c 'echo "127.0.0.1 damoportal" >> /etc/hosts'
```

3. Clear host cache
```bash
chrome://net-internals/#dns
```
, or restart browser..
