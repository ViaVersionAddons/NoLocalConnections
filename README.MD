# NoLocalConnections
An example ViaProxy plugin denying connections to local servers.\
This plugin is only needed if you want to host a public SRV mode proxy instance.

## SRV ViaProxy
### Running ViaProxy in SRV mode
To use the SRV mode in ViaProxy you have to start it through the command line.\
The command line arguments are as follows:
````bash
java -jar ViaProxy.jar -ba 0.0.0.0 -bp <bind port> -srv -oam_auth -a0 -v1.8.x
````

You have to replace ``<bind port>`` with the port you want ViaProxy to run on.\
The ``-oam_auth`` argument is optional and enables the [OpenAuthMod](https://github.com/RaphiMC/OpenAuthMod) authentication.\
The ``-a0`` and ``-v1.8.x`` arguments are required for ViaProxy to start but don't do anything in SRV mode.

### Connecting to ViaProxy in SRV mode
To connect to ViaProxy in SRV mode you need a domain pointing to the IP of the server running ViaProxy.\
The subdomain ``viaproxy.`` is required for it to work.\
For local connections you can use ``viaproxy.127-0-0-1.nip.io``.

To connect to a server through ViaProxy you have to prepend the ``server ip``, ``server port`` and ``version`` to your domain:
````
<ip>_<port>_<version>.viaproxy.<domain>
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
