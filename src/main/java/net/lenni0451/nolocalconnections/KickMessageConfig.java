package net.lenni0451.nolocalconnections;

import net.lenni0451.optconfig.ConfigLoader;
import net.lenni0451.optconfig.annotations.Description;
import net.lenni0451.optconfig.annotations.OptConfig;
import net.lenni0451.optconfig.annotations.Option;
import net.lenni0451.optconfig.provider.ConfigProvider;
import net.raphimc.viaproxy.util.logging.Logger;

import java.io.File;
import java.util.List;

@OptConfig(header = {
        "Configuration for the NoLocalConnections ViaProxy plugin.",
        "Used to block a Connection for Local Address Connections through ViaProxy(useful for address_port_version.viaproxy.hostname).",
        "",
        "Made by Lenni0451",
        "Source: https://github.com/ViaVersionAddons/NoLocalConnections"
})
public class KickMessageConfig {

    @Option("kick")
    @Description({
            "kick message when the user tries to join using local address such as 192.168.35.1. you can use the Decoration Codes here using Section Symbol(§)."
    })
    public static String kick = "§You can't connect to any local address.";
  
    public static void load() {
        try {
            ConfigLoader<KickMessageConfig> configLoader = new ConfigLoader<>(KickMessageConfig.class);
            configLoader.getConfigOptions().setResetInvalidOptions(true); //Reset invalid options to their default value
            configLoader.loadStatic(ConfigProvider.file(new File("kickmessage.yml")));
        } catch (Throwable t) {
            Logger.LOGGER.error("Unable to load kick message! turning off viaproxy...", t);
            System.exit(-1);
        }
    }
}
