package mod.teku;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.Display;

@Mod(modid = SwordSwap.MODID, version = SwordSwap.VERSION)
public class SwordSwap {
    public static final String MODID = "autogg";
    public static final String VERSION = "1.0";

    private Minecraft mc = Minecraft.getMinecraft();

    private final KeyBinding swordSwap = new KeyBinding("Sword Swap", 0, "Pit Addons");

    @EventHandler
    public void init(FMLInitializationEvent event) {
        new Config();
        ClientCommandHandler.instance.registerCommand(new Config());
        MinecraftForge.EVENT_BUS.register(this);
        ClientRegistry.registerKeyBinding(swordSwap);
    }

    @SubscribeEvent
    public void onGuiScreenLoad(GuiOpenEvent event) {
        Display.setTitle("Minecraft - " + Minecraft.getMinecraft().getSession().getUsername());
    }

    private boolean swapping = false;
    int initialLocation = -1;

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (!swordSwap.isPressed()) return;
        initialLocation = mc.thePlayer.inventory.currentItem;
    }

    @SubscribeEvent
    public void onKeyHold(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null) return;

        if (swordSwap.isKeyDown()) {
            swapping = true;
            if (Minecraft.getMinecraft().thePlayer.inventory.currentItem == Config.swapFrom - 1) {
                Minecraft.getMinecraft().thePlayer.inventory.currentItem = Config.swapTo - 1;
            } else {
                Minecraft.getMinecraft().thePlayer.inventory.currentItem = Config.swapFrom - 1;
            }
        } else {
            if (swapping) mc.thePlayer.inventory.currentItem = initialLocation;
            swapping = false;
        }
    }
}
