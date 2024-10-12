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
        "Configuration for the MultiLaunch ViaProxy plugin.",
        "Used to launch a server jar alongside ViaProxy.",
        "",
        "Made by Lenni0451",
        "Source: https://github.com/ViaVersionAddons/ViaProxyMultiLaunch"
})
public class MultiLaunchConfig {

    @Option("ServerJar")
    @Description({
            "kick message when the user tries to join using local address such as 192.168.35.1. you can use the Decoration Codes here using Section Symbol(§)."
    })
    public static String kick = "";
  
    public static void load() {
        try {
            ConfigLoader<MultiLaunchConfig> configLoader = new ConfigLoader<>(MultiLaunchConfig.class);
            configLoader.getConfigOptions().setResetInvalidOptions(true); //Reset invalid options to their default value
            configLoader.loadStatic(ConfigProvider.file(new File("multilaunch.yml")));
        } catch (Throwable t) {
            Logger.LOGGER.error("Unable to load kick message! turning off viaproxy...", t);
            System.exit(-1);
        }
    }
}
