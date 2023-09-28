package com.louisweigel.toggletoggle.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class ToggleToggleClient implements ClientModInitializer {
    private static KeyBinding sprintKeyBinding;
    private static KeyBinding sneakKeyBinding;

    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        sprintKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.toggletoggle.sprint",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                "category.toggletoggle.default"
        ));

        sneakKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.toggletoggle.sneak",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                "category.toggletoggle.default"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (sprintKeyBinding.wasPressed()) {
                assert client.player != null;

                SimpleOption<Boolean> option = client.options.getSprintToggled();

                option.setValue(!option.getValue());
                client.player.sendMessage(Text.literal(String.format("Sprinting is now set to \"%s\"", (option.getValue()) ? "toggle" : "hold")), false);
            }

            while (sneakKeyBinding.wasPressed()) {
                assert client.player != null;

                SimpleOption<Boolean> option = client.options.getSneakToggled();

                option.setValue(!option.getValue());
                client.player.sendMessage(Text.literal(String.format("Sneaking is now set to \"%s\"", (option.getValue()) ? "toggle" : "hold")), false);
            }
        });
    }
}