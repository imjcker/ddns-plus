# DDNS 
this is a simple ddns for cloudflare.

run this program as a stand alone spring boot. 

or run it in docker container:

```shell

docker run -d --name ddns --restart always imjcker/ddns:latest

```

optional environment variables:

  X-AUTH-EMAIL: cloudflare account email  
  X-AUTH-KEY: auth key  
  CONTENT-TYPE: application/json  
  DDNS-TYPE: A, CNAME.  
  DDNS-NAME: Your domain  
  DDNS-TTL: ttl in seconds  
  DDNS-PROXIED: is proxy  
  
  