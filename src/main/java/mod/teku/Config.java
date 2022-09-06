package mod.teku;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Config extends CommandBase {

    public static int swapFrom;
    public static int swapTo;

    JsonObject config;

    private final File file = new File(Loader.instance().getConfigDir(), "swordswap.json");

    public Config() {
        try {
            if (this.file.createNewFile()) {
                this.config = new JsonObject();
            } else {
                this.config = (new JsonParser()).parse(FileUtils.readFileToString(this.file)).getAsJsonObject();
            }
        } catch (Exception e) {
            this.config = new JsonObject();
        }
        swapFrom = initInteger("swapFrom", 1);
        swapTo = initInteger("swapTo", 4);
        saveConfig();
    }

    public int initInteger(String name, int defaultValue) {
        if (!this.config.has(name)) {
            this.config.addProperty(name, defaultValue);
            return defaultValue;
        }
        return this.config.get(name).getAsInt();
    }

    public void setValue(String name, int value) {
        this.config.addProperty(name, value);
        saveConfig();
    }

    public void saveConfig() {
        try {
            FileUtils.writeStringToFile(this.file, this.config.toString(), "utf-8");
        } catch (IOException e) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Error saving config. This should not happen!"));
        }
    }

// COMMAND

    @Override
    public String getCommandName() {
        return "swapconfig";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName() + " <from/to> <value>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            error(getCommandUsage(sender));
        } else if (args.length == 1) {
            switch (args[0]) {
                case "from":
                    error("Missing argument! Usage: " + "/" + getCommandName() + " from <1-9>");
                    return;
                case "to":
                    error("Missing argument! Usage: " + "/" + getCommandName() + " to <1-9>");
                    return;
            }
            error("Usage: " + getCommandUsage(sender));
        } else {
            int value;
            if (StringUtils.isNumeric(args[1]) && Integer.parseInt(args[1]) <= 9) {
                switch (args[0]) {
                    case "from":
                        value = Integer.parseInt(args[1]);
                        swapFrom = value;
                        setValue("swapFrom", value);
                        info("Set Swap From value to " + value);
                        return;
                    case "to":
                        value = Integer.parseInt(args[1]);
                        swapTo = value;
                        setValue("swapTo", value);
                        info("Set Swap To value to " + value);
                        return;
                }
                error("Invalid argument! Usage: " + getCommandUsage(sender));
            } else {
                error("Invalid argument! Usage: " + getCommandUsage(sender));
            }
        }
    }
    private void error(String message) {
        (Minecraft.getMinecraft()).thePlayer.addChatMessage(new ChatComponentText(ChatFormatting.RED + message));
    }

    private void info(String message) {
        (Minecraft.getMinecraft()).thePlayer.addChatMessage(new ChatComponentText(ChatFormatting.GREEN + message));
    }

    public boolean canCommandSenderUseCommand(ICommandSender p_canCommandSenderUseCommand_1_) {
        return true;
    }

    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos block) {
        if (args.length == 0)
            return Arrays.asList("from", "to");
        if (args.length == 1)
            return getListOfStringsMatchingLastWord(args, "from", "to");
        return Collections.emptyList();
    }

}

