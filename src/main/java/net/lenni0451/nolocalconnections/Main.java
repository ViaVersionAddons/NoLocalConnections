package net.lenni0451.nolocalconnections;

import net.lenni0451.lambdaevents.EventHandler;
import net.raphimc.viaproxy.plugins.PluginManager;
import net.raphimc.viaproxy.plugins.ViaProxyPlugin;
import net.raphimc.viaproxy.plugins.events.PreConnectEvent;

import java.net.InetAddress;

public class Main extends ViaProxyPlugin {

    @Override
    public void onEnable() {
        PluginManager.EVENT_MANAGER.register(this);
    }

    @EventHandler
    public void onEvent(PreConnectEvent event) {
        InetAddress betterAddress = event.getServerAddress().toSocketAddress().getAddress();
        if (this.isLocal(betterAddress)) event.setCancelMessage("§CYou can't connect to any local address");
    }

    private boolean isLocal(final InetAddress addr) {
        String hostAddress = addr.getHostAddress();
        return addr.isAnyLocalAddress()
                || addr.isLoopbackAddress()
                || hostAddress.endsWith(".0")
                || hostAddress.endsWith(".255")
                || hostAddress.startsWith("192.168.")
                || hostAddress.startsWith("172.16.")
                || hostAddress.startsWith("172.17.")
                || hostAddress.startsWith("172.18.")
                || hostAddress.startsWith("172.19.")
                || hostAddress.startsWith("172.20.")
                || hostAddress.startsWith("172.21.")
                || hostAddress.startsWith("172.22.")
                || hostAddress.startsWith("172.23.")
                || hostAddress.startsWith("172.24.")
                || hostAddress.startsWith("172.25.")
                || hostAddress.startsWith("172.26.")
                || hostAddress.startsWith("172.27.")
                || hostAddress.startsWith("172.28.")
                || hostAddress.startsWith("172.29.")
                || hostAddress.startsWith("172.30.")
                || hostAddress.startsWith("172.31.")
                || hostAddress.startsWith("10.");
    }

}
