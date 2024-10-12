# NoLocalConnections
An example ViaProxy plugin denying connections to local servers.\
This plugin is only needed if you want to host a public SRV mode proxy instance.

## SRV ViaProxy
### Running ViaProxy in SRV mode
> [!CAUTION]
> This Option is meaningless because there is no proper authentication method for 1.19.4+ as OpenAuthMod discontinued and because ViaProxy was open source for 1 year is easier to use on client without their help. 
use this option if you want to.

To use the SRV mode in ViaProxy you have to start it through the config.\
The config:
```
# ViaProxy configuration file

# The address ViaProxy should listen for connections.
# This option is not reloadable.
bind-address: 0.0.0.0:25568 # will translate to viaproxy.0-0-0-0.nip.io:25568

# The address of the server ViaProxy should connect to.
target-address: 0.0.0.0:25565 # This option is ignored as client handles it like <target-address>_<target-port>.viaproxy.<bind-address>.

# The version ViaProxy should translate to. (See ViaProxy GUI for a list of versions)
target-version: Auto Detect (1.7+ servers) # This option is ignored as client handles it like <target-address>_<target-port>_<target_version>.viaproxy.<bind-address>.

# The connect timeout for backend server connections in milliseconds.
connect-timeout: 8000

# Proxy Online Mode allows you to see skins on online mode servers and use the signed chat features.
# Enabling Proxy Online Mode requires your client to have a valid minecraft account.
proxy-online-mode: false

# The authentication method to use for joining the target server.
# none: No authentication (Offline mode)
# openauthmod: Requires the OpenAuthMod client mod (https://modrinth.com/mod/openauthmod)
# account: Use an account for joining the target server. (Has to be configured in ViaProxy GUI)
auth-method: OPENAUTHMOD # OPTIONAL.

# The GUI account list index (0 indexed) of the account if the auth method is set to account.
minecraft-account-index: 0

# Use BetaCraft authentication for classic servers.
# Enabling BetaCraft Auth allows you to join classic servers which have online mode enabled.
betacraft-auth: false

# URL of a SOCKS(4/5)/HTTP(S) proxy which will be used for backend server connections. Leave empty to connect directly.
# Supported formats:
# - type://address:port
# - type://username:password@address:port
backend-proxy-url: ''

# Send HAProxy protocol messages to the target server.
backend-haproxy: false

# Read HAProxy protocol messages from client connections.
frontend-haproxy: false

# Enables sending signed chat messages on >= 1.19 servers.
chat-signing: true

# The threshold for packet compression. Packets larger than this size will be compressed. (-1 to disable)
compression-threshold: 256

# Enabling this will allow you to ping <= b1.7.3 servers. This may cause issues with servers that block too frequent connections.
allow-beta-pinging: false

# Enabling this will prevent getting disconnected from the server when a packet translation error occurs and instead only print the error in the console.
# This may cause issues depending on the type of packet which failed to translate.
ignore-protocol-translation-errors: false

# Enabling this will suppress client protocol errors to prevent lag when ViaProxy is getting spammed with invalid packets.
# This may cause issues with debugging client connection issues because no error messages will be printed.
suppress-client-protocol-errors: false

# Allow <= 1.6.4 clients to connect through ViaProxy to the target server. (No protocol translation or packet handling)
allow-legacy-client-passthrough: false

# Allow additional information like player ip, player uuid to be passed through to the backend server.
# This is typically used by proxies like BungeeCord and requires support from the backend server as well.
bungeecord-player-info-passthrough: false

# Custom MOTD to send when clients ping the proxy. Leave empty to use the target server's MOTD.
custom-motd: ''

# URL of a resource pack which clients can optionally download when connecting to the server. Leave empty to disable.
# Example: http://example.com/resourcepack.zip
resource-pack-url: ''

# Allows clients to specify a target server and version using wildcard domains.
# none: No wildcard domain handling.
# public: Public wildcard domain handling. Intended for usage by external clients. (Example: address_port_version.viaproxy.127.0.0.1.nip.io)
# internal: Internal wildcard domain handling. Intended for local usage by custom clients. (Example: original-handshake-address\7address:port\7version\7classic-mppass)
wildcard-domain-handling: PUBLIC

# Enables handling and rewriting of Simple Voice Chat mod packets.
simple-voice-chat-support: false

# Accepts resource packs from the server without showing a prompt to the client.
# This is required for servers that require a resource pack, but the client can't load it due to version differences.
fake-accept-resource-packs: false
```

You have to replace ``<bind port>`` with the port you want ViaProxy to run on.\
The ``auth-method: openauthmod`` is optional and enables the [OpenAuthMod](https://github.com/RaphiMC/OpenAuthMod) authentication.\
The ``target-address`` and ``target-version`` are required for ViaProxy to start but don't do anything in SRV mode.

### Connecting to ViaProxy in SRV mode
To connect to ViaProxy in SRV mode you need a domain pointing to the IP of the server running ViaProxy.\
The subdomain ``viaproxy.`` is required for it to work.\
For local connections you can use ``viaproxy.127-0-0-1.nip.io``.

To connect to a server through ViaProxy you have to prepend the ``server ip``, ``server port`` and ``version`` to your domain:
````
address_port_version.viaproxy.hostname
````

For example to connect to ``lenni0451.net:39999`` with the version ``C0.30-CPE`` you can use:
````
lenni0451.net_39999_c0.30-cpe.viaproxy.127-0-0-1.nip.io
````

If the domain of a server contains an underscore you currently can't connect to it since ViaProxy uses the underscore as a separator.

#### Geyser - usage for Geyser-ViaProxy
Requires a Geyser-Viaproxy installed on server side.
join with `address_port_version.viaproxy.hostname` on Bedrock Client Side. 
#### Geyser - usage for GeyserConnect
use a public geyserconnect.
This does not require Geyser-Viaproxy on Server Side.
find a existing instance.
join with `address_port_version.viaproxy.hostname` on GeyserConnect. (the Bedrock/Geyser Server requires Geyser-Viaproxy installed on server side.)
## How to make a ViaProxy plugin
### viaproxy.yml
ViaProxy plugins require a ``viaproxy.yml`` file in the root of the plugin jar.\
It defines the name, version, author, main class and minimum supported ViaProxy version of the plugin:
````yaml
name: "CoolPlugin"
version: "1.0.0"
author: "ExampleDude"
main: "com.exampledude.coolplugin.Main"
min-version: "3.0.0"
````

### Plugin Main
The plugin main must extend ``ViaProxyPlugin`` and implement the ``onEnable`` method:
````java
public class Main extends ViaProxyPlugin {
    @Override
    public void onEnable() {
    }
}
````

There is an optional ``registerTransformers`` method that can be implemented to register class transformers **for the own plugin classes**:
````java
@Override
public void registerTransformers(final TransformerManager transformerManager) {
    transformerManager.addTransformer("com.exampledude.coolplugin.Transformer");
}
````

Check out the [ClassTransform](https://github.com/Lenni0451/ClassTransform/#usage) documentation for more information on how to use class transformers.

### Events
The only real way to interact with ViaProxy is through events.\
ViaProxy uses [LambdaEvents](https://github.com/Lenni0451/LambdaEvents) for event handling.\
You can register a listener using the ``register()`` method in the ``PluginManager.EVENT_MANAGER``:
````java
PluginManager.EVENT_MANAGER.register(this);
````

ViaProxy currently (at the time of writing) has the following events:
 - Client2ProxyChannelInitializeEvent
 - Client2ProxyHandlerCreationEvent
 - ClientLoggedInEvent
 - ConnectEvent
 - ConsoleCommandEvent
 - FillPlayerDataEvent
 - GetDefaultPortEvent
 - PostOptionsParseEvent
 - PreConnectEvent
 - PreOptionsParseEvent
 - ProtocolHackInitEvent
 - Proxy2ServerChannelInitializeEvent
 - Proxy2ServerHandlerCreationEvent
 - ProxyStartEvent
 - ProxyStopEvent
 - ResolveSrvEvent
 - ViaLoadingEvent

Some events have pre- and post-states and some events are cancellable.

To listen to an event you have to add the ``@EventHandler`` annotation to the handler method and put the event as the only parameter:
````java
@EventHandler
public void onPreConnect(final PreConnectEvent event) {
}
````

### ViaVersion API
Since ViaProxy uses ViaVersion for its protocol handling, the entire ViaVersion API is available.

### Loading the plugin
To load plugins just put the plugin jars in the ``plugins`` folder and ViaProxy will load them on startup.
