package mod.nital;

import mod.nital.config.SwapConfig;
import mod.nital.handlers.SwapLogic;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(name = SwordSwap.NAME, modid = SwordSwap.MODID, version = SwordSwap.VERSION, useMetadata = true)
public class SwordSwap {

    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";
    public static final String MODID = "@ID@";

    public static SwapConfig config;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        config = new SwapConfig();
        MinecraftForge.EVENT_BUS.register(new SwapLogic());
    }
}
