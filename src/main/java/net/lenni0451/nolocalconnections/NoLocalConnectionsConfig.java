package net.lenni0451.nolocalconnections;

import net.lenni0451.optconfig.ConfigLoader;
import net.lenni0451.optconfig.annotations.Description;
import net.lenni0451.optconfig.annotations.OptConfig;
import net.lenni0451.optconfig.annotations.Option;
import net.lenni0451.optconfig.provider.ConfigProvider;
import net.raphimc.viaproxy.util.logging.Logger;

import java.io.File;

@OptConfig(header = {
        "Configuration for the NoLocalConnections ViaProxy plugin.",
        "Used to block connections to local addresses through ViaProxy (useful for public SRV proxies).",
        "",
        "Made by Lenni0451",
        "Source: https://github.com/ViaVersionAddons/NoLocalConnections"
})
public class NoLocalConnectionsConfig {

    @Option("KickMessage")
    @Description({
            "Kick message when the user tries to connect local addresses such as 192.168.35.1",
            "You can use color codes here using section symbol (§)."
    })
    public static String kickMessage = "§cYou can't connect to any local address.";

    public static void load(final File dataFolder) {
        try {
            ConfigLoader<NoLocalConnectionsConfig> configLoader = new ConfigLoader<>(NoLocalConnectionsConfig.class);
            configLoader.getConfigOptions().setResetInvalidOptions(true); //Reset invalid options to their default value
            configLoader.loadStatic(ConfigProvider.file(new File(dataFolder, "config.yml")));
        } catch (Throwable t) {
            Logger.LOGGER.error("Unable to load config! Shutting down...", t);
            System.exit(-1);
        }
    }
}
