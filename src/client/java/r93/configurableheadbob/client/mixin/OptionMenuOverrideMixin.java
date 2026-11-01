package r93.configurableheadbob.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.AccessibilityOptionsScreen;
import net.minecraft.network.chat.Component;

import r93.configurableheadbob.client.menu.HeadbobMenu;

@Mixin(targets = "net.minecraft.client.gui.components.OptionsList$Entry")
public class OptionMenuOverrideMixin {
    
    /*
    Going through the accessibility options to find + replace the view bob option.
    Man, The really long class path names feel incredibly weird, but thats correct!
    */
    @Redirect(
        method = "small(Lnet/minecraft/client/Options;Lnet/minecraft/client/OptionInstance;Lnet/minecraft/client/OptionInstance;Lnet/minecraft/client/gui/screens/options/OptionsSubScreen;)Lnet/minecraft/client/gui/components/OptionsList$Entry;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/OptionInstance;createButton(Lnet/minecraft/client/Options;)Lnet/minecraft/client/gui/components/AbstractWidget;"
        )
    )
    private static AbstractWidget replaceHeadbobButton(OptionInstance<?> currentInstance, Options currentOption, 
        Options rowFactory, OptionInstance<?> firstOption, OptionInstance<?> secondOption, OptionsSubScreen owningScreen){

        //Go through all the accessibility options and find the view bob button so we can replace it.
        if (owningScreen instanceof AccessibilityOptionsScreen && currentInstance == currentOption.bobView()){
            System.out.println("Grabbed Headbobbing Options button for replacement");
            AbstractWidget configureViewbobButton = Button.builder(Component.translatable("configurable-headbob.settings.title"), 
                clickedButton -> new HeadbobMenu(owningScreen, currentOption) ).width(150).build();
            return configureViewbobButton;}
        else {
            System.out.println("Sticking to Vanilla Behavior");
            return currentInstance.createButton(currentOption);
        }
    }

}
