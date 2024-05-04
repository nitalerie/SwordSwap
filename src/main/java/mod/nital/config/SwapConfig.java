package mod.nital.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Dropdown;
import cc.polyfrost.oneconfig.config.annotations.KeyBind;
import cc.polyfrost.oneconfig.config.annotations.Slider;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.core.OneKeyBind;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.libs.universal.UKeyboard;
import mod.nital.SwordSwap;
import mod.nital.handlers.SwapLogic;

public class SwapConfig extends Config {

    @Switch(name = "Toggle", description = "Enables/Disables Swapping", size = 2)
    public static boolean toggle = false;

    @KeyBind(name = "Keybind", description = "Key that will be used to toggle sword swapping")
    public static OneKeyBind swapBind = new OneKeyBind(UKeyboard.KEY_NONE);

    @Dropdown(name = "Swap Mode", description = "Swapping Mode:\n\nHold: Hold the key set below to swap (need to have it toggled)\nToggle: Toggle swapping using the keybind set below", subcategory = "Swap Settings", options = {"Hold", "Toggle"})
    public static int swapMode = 0;

    @Slider(name = "Delay", description = "Self-explanatory", subcategory = "Swap Settings", min = 0f, max = 1000f, step = 50)
    public static int delay = 0;

    @Slider(name = "Swap From", description = "Self-explanatory", subcategory = "Swap Settings", min = 1f, max = 9f, step = 1)
    public static int swapFrom = 1;

    @Slider(name = "Swap To", description = "Self-explanatory", subcategory = "Swap Settings", min = 1f, max = 9f, step = 1)
    public static int swapTo = 4;

    public SwapConfig() {
        super(new Mod(SwordSwap.NAME, ModType.UTIL_QOL), SwordSwap.MODID + ".json");
        initialize();

        registerKeyBind(swapBind, () -> {
            if (swapMode == 1) {
                toggle = !toggle;
                SwapLogic.updateInitialLocation(toggle);
            }
        });
    }
}

