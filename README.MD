# NoLocalConnections
An example ViaProxy plugin denying connections to local servers.\
This plugin is only needed if you want to host a public SRV mode proxy instance.

## SRV ViaProxy
### Running ViaProxy in SRV mode
To use the SRV mode in ViaProxy you have to change some config options.
```yaml
bind-address: 0.0.0.0:25568 # will translate to viaproxy.0-0-0-0.nip.io:25568
target-address: 127.0.0.1 # unused in srv mode
target-version: 1.8.x # unused in srv mode
auth-method: OpenAuthMod
wildcard-domain-handling: PUBLIC
```
You have to replace `0.0.0.0:25568` with the address and port you want ViaProxy to run on.\
The `auth-method: openauthmod` is optional and enables [OpenAuthMod](https://github.com/RaphiMC/OpenAuthMod) authentication. Check the caution below.\
The `target-address` and `target-version` are required for ViaProxy to start but don't do anything in SRV mode.

> <b>[CAUTION]</b>\
> `auth-method: openauthmod` will not work for 1.7-1.7.10, 1.19.4+, Vanilla and Bedrock Edition.\
> Reason:\
> 1.7-1.7.10: OpenAuthMod is only available for 1.8+.\
> Bedrock: Forge and Fabric don't support Bedrock Edition.\
> 1.19.4+: OpenAuthMod was discontinued before release of 1.19.4.

### Connecting to ViaProxy in SRV mode
To connect to ViaProxy in SRV mode you need a domain pointing to the IP of the server running ViaProxy.\
The subdomain `viaproxy.` is required for it to work.\
For local connections you can use `viaproxy.127-0-0-1.nip.io`.

To connect to a server through ViaProxy you have to prepend the `server ip`, `server port` and `version` to your domain:
```
address_port_version.viaproxy.hostname
```

For example to connect to `lenni0451.net:39999` with the version `C0.30-CPE` you can use:
```
lenni0451.net_39999_c0.30-cpe.viaproxy.127-0-0-1.nip.io
```

If the domain of a server contains an underscore you currently can't connect to it since ViaProxy uses the underscore as a separator.
