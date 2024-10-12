# NoLocalConnections
An example ViaProxy plugin denying connections to local servers.\
This plugin is only needed if you want to host a public SRV mode proxy instance.

## SRV ViaProxy
### Running ViaProxy in SRV mode

To use the SRV mode in ViaProxy you have to start it through the config.\
Here are The config options require to make a Public SRV mode.
```
bind-address: 0.0.0.0:25568 # will translate to viaproxy.0-0-0-0.nip.io:25568
target-address: 127.0.0.1 # unused on srv mode.
target-version: 1.8.x # unused on srv mode.
auth-method: OpenAuthMod
wildcard-domain-handling: PUBLIC
```

You have to replace ``0.0.0.0:<bind port>`` with the port you want ViaProxy to run on.\
The ``auth-method: openauthmod`` is optional and enables the [OpenAuthMod](https://github.com/RaphiMC/OpenAuthMod) authentication. for 1.19.4 users, see the caution below.\
The ``target-address`` and ``target-version`` are required for ViaProxy to start but don't do anything in SRV mode.
> [!CAUTION]
> warning: auth-method: openauthmod will be not functional for 1.7-1.7.10, Vanilla 1.8-1.19.3, 1.19.4-1.21.1, and Bedrock Edition.\
> Reason: 1.7-1.7.10: OpenAuthMod is only available for 1.8.
> Bedrock: Forge or Fabric don't support Bedrock Edition.\
> 1.19.4+: OpenAuthMod discontinued before release of 1.19.4. use this option if you want to.
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
