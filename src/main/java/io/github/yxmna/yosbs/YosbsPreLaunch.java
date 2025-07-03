package io.github.yxmna.yosbs;

import io.github.yxmna.yosbs.core.DefaultConfigSynchronizer;
import io.github.yxmna.yosbs.util.Logger;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import org.apache.logging.log4j.LogManager;

import java.nio.file.Path;

public final class YosbsPreLaunch implements PreLaunchEntrypoint {

    public static final Logger LOG = new Logger(LogManager.getLogger("yosbs"), "[YOSBS] ");

    @Override
    public void onPreLaunch() {
        LOG.info("Initializing");

        Path runDir    = FabricLoader.getInstance().getGameDir();                // …/.minecraft
        Path configDir = FabricLoader.getInstance().getConfigDir();              // …/.minecraft/config

        new DefaultConfigSynchronizer(LOG, runDir, configDir).apply();
    }
}