package mod.nital.handlers;

import mod.nital.config.SwapConfig;
import mod.nital.util.Timer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SwapLogic {

    private static final Minecraft mc = Minecraft.getMinecraft();

    private static int initialLocation = -1;
    private boolean swapping = false;

    private final Timer swapDelay = new Timer();

    @SubscribeEvent
    public void onKeyHold(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (mc.thePlayer == null || mc.theWorld == null) return;
        if (mc.currentScreen != null) return;
        if (!SwapConfig.toggle) return;

        if (SwapConfig.swapMode == 1 || SwapConfig.swapBind.isActive()) {
            if (!swapping) {
                updateInitialLocation(true);
            }

            swapping = true;
            if (Minecraft.getMinecraft().thePlayer.inventory.currentItem == SwapConfig.swapFrom - 1 && swapDelay.hasReached(SwapConfig.delay)) {
                Minecraft.getMinecraft().thePlayer.inventory.currentItem = SwapConfig.swapTo - 1;
                swapDelay.reset();
            } else if (swapDelay.hasReached(SwapConfig.delay)) {
                Minecraft.getMinecraft().thePlayer.inventory.currentItem = SwapConfig.swapFrom - 1;
                swapDelay.reset();
            }
        }

        if (SwapConfig.swapMode != 1 && !SwapConfig.swapBind.isActive() && swapping) {
            updateInitialLocation(false);
            swapping = false;
        }
    }

    public static void updateInitialLocation(boolean enabled) {
        if (enabled) initialLocation = mc.thePlayer.inventory.currentItem;
        else mc.thePlayer.inventory.currentItem = initialLocation;
    }
}
