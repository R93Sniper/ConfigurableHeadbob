package r93.configurableheadbob.client.menu;

import java.util.Objects;
import org.jspecify.annotations.NullMarked;

import net.minecraft.client.Options;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.CommonComponents;


@NullMarked 
public class HeadbobMenu extends OptionsSubScreen {
    
    //Button related bools
    private boolean advMode = false;

    //Defining the sliders for the menu
    private final OptionInstance<Integer> viewIntensitySlider = this.createSlider("configurable-headbob.settings.arms.intensity.global", 100, value -> {});
    private final OptionInstance<Integer> viewScaleSlider = this.createSlider("configurable-headbob.settings.arms.speed.global", 100, value -> {});
    private final OptionInstance<Integer> camIntensitySlider = this.createSlider("configurable-headbob.settings.cam.intensity.global", 100, value -> {});
    private final OptionInstance<Integer> camScaleSlider = this.createSlider("configurable-headbob.settings.cam.speed.global", 100, value -> {});



    public HeadbobMenu(Screen parentScreen, Options currentSetting){
        super(parentScreen, currentSetting, Component.translatable("configurable-headbob.settings.title"));
    }

    @Override 
    protected void addOptions(){
        OptionsList optionsList = Objects.requireNonNull(this.list, "Initialize list before adding options"); //This gets VSC to shut up about nullability

        Button modeSelect = Button.builder(Component.literal("Uninitalized"), this::switchMode).width(150).build();
        updateModeButton(modeSelect); //Ensure the text is correct when initialized

        optionsList.addSmall(options.bobView().createButton(options, 0, 0, 150, enabled -> {
            updateModeButton(modeSelect); 
            toggleSliders(optionsList);
            }),modeSelect);
        
        //Simple spacer for visual separation. 
        //optionsList.addHeader(Component.empty());
        //optionsList.children().getLast().setHeight(8); 
        //TODO: Figure out how to make this work properly

        //Sliders in basic mode
        optionsList.addSmall(viewIntensitySlider, viewScaleSlider);
        optionsList.addSmall(camIntensitySlider, camScaleSlider);

        toggleSliders(optionsList);
        //Sliders only exist if view bob is enabled in the first place
        if (advMode) {
            System.out.println("Advanced Options pending!");}
    
    }

    /*
    ==================================================================================================
     Helper methods beyond this point
    ==================================================================================================
    */

    private OptionInstance<Integer> createSlider(String sliderName, int theValue, OptionInstance.ValueUpdateListener<Integer> onChange){
        return new OptionInstance<Integer>(sliderName, OptionInstance.noTooltip(), (caption,value) -> Component.literal(caption.getString() + ": " + (value / 100.0)), 
            new OptionInstance.IntRange(0, 200), theValue, onChange);
    } 

    private void toggleSliders(OptionsList theList){
        boolean bobbingActive = options.bobView().get();

        //Iterate through the basic sliders to toggle their visibility based on if headbobbing is enabled
        OptionInstance<?>[] basicSliders = {viewIntensitySlider,viewScaleSlider,camIntensitySlider,camScaleSlider};
        for (OptionInstance<?> i:basicSliders){
            AbstractWidget currentWidget = Objects.requireNonNull(theList.findOption(i),"Initialize sliders before adding options");
            currentWidget.visible = bobbingActive;
            currentWidget.active = bobbingActive;
        }

    } 

    private void switchMode(Button modeButton){
        advMode = !advMode;
        updateModeButton(modeButton);
    }

    private void updateModeButton(Button modeButton){
        boolean buttonActive = options.bobView().get();
        modeButton.active = buttonActive;
        Component modeText;

        if (!buttonActive){
            modeText = CommonComponents.OPTION_OFF;
        } else if (advMode){
            modeText = Component.translatable("configurable-headbob.settings.mode.advanced");
        } else {
            modeText = Component.translatable("configurable-headbob.settings.mode.basic");
        }

        modeButton.setMessage(Component.translatable("configurable-headbob.settings.mode").append(" ").append(modeText));
    }

    

}
