package r93.configurableheadbob.client.menu;

import java.util.Objects;
import org.jspecify.annotations.NullMarked;

import net.minecraft.client.Options;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;


@NullMarked 
public class HeadbobMenu extends OptionsSubScreen {
    
    //Defining the sliders for the menu
    private final OptionInstance<Integer> viewIntensitySlider = this.createSlider("configurable-headbob.settings.arms.intensity.global", 100, value -> {});
    private final OptionInstance<Integer> viewScaleSlider = this.createSlider("configurable-headbob.settings.arms.speed.global", 100, value -> {});
    private final OptionInstance<Integer> camIntensitySlider = this.createSlider("configurable-headbob.settings.cam.intensity.global", 100, value -> {});
    private final OptionInstance<Integer> camScaleSlider = this.createSlider("configurable-headbob.settings.cam.speed.global", 100, value -> {});



    public HeadbobMenu(Screen parentScreen, Options currentSetting){
        super(parentScreen, currentSetting, Component.translatable("configurable-headbob.settings"));
    }

    @Override 
    protected void addOptions(){
        OptionsList optionsList = Objects.requireNonNull(this.list, "Initialize list before adding options"); //This gets VSC to shut up about nullability

        Button modeSelect = Button.builder(Component.translatable("configurable-headbob.settings.mode").append(" ").append(Component.translatable("configurable-headbob.settings.mode.basic")), 
            clickedButton ->{}).width(150).build();

        optionsList.addSmall(options.bobView().createButton(options),modeSelect);
        
        //Simple spacer for visual separation. 
        //optionsList.addHeader(Component.empty());
        //optionsList.children().getLast().setHeight(8); 
        //TODO: Figure out how to make this work properly

        //Sliders in basic mode
        optionsList.addSmall(viewIntensitySlider, viewScaleSlider);
        optionsList.addSmall(camIntensitySlider, camScaleSlider);
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


}
