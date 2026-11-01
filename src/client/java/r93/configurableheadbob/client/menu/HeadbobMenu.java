package r93.configurableheadbob.client.menu;

import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;

public class HeadbobMenu extends OptionsSubScreen {
    
    public HeadbobMenu(Screen parentScreen, Options currentSetting){
        super(parentScreen, currentSetting, Component.translatable("configurable-headbob.settings"));
    }

    @Override 
    protected void addOptions(){
        return;
    }
}
