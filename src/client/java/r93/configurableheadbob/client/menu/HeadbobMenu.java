package r93.configurableheadbob.client.menu;

import r93.configurableheadbob.client.menu.HeadbobOptionsList;
import org.jspecify.annotations.NullMarked;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.BooleanSupplier;

import net.minecraft.client.Options;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.CommonComponents;


@NullMarked 
public class HeadbobMenu extends OptionsSubScreen {
    
    private boolean currentlySynchronizing = false;

    //Defining the sliders for the menu
    //Basic Mode
    private final OptionInstance<Integer> viewIntensitySlider = this.createSlider("configurable-headbob.arms.intensity.global", 
        Math.round(MenuValues.viewIntensity.getBasicValue() * 100.0F), value -> MenuValues.viewIntensity.setAllValues(value/ 100.0F),
        "configurable-headbob.tooltip.arms.intensity.global", MenuValues.viewIntensity::matchingValues);
    private final OptionInstance<Integer> viewScaleSlider = this.createSlider("configurable-headbob.arms.speed.global", 
        Math.round(MenuValues.viewSpeed.getBasicValue() * 100.0F), value -> MenuValues.viewSpeed.setAllValues(value/ 100.0F), 
        "configurable-headbob.tooltip.arms.speed.global", MenuValues.viewSpeed::matchingValues);
    private final OptionInstance<Integer> camIntensitySlider = this.createSlider("configurable-headbob.cam.intensity.global", 
        Math.round(MenuValues.camIntensity.getBasicValue() * 100.0F), value -> MenuValues.camIntensity.setAllValues(value/ 100.0F), 
        "configurable-headbob.tooltip.cam.intensity.global", MenuValues.camIntensity::matchingValues);
    private final OptionInstance<Integer> camScaleSlider = this.createSlider("configurable-headbob.cam.speed.global", 
        Math.round(MenuValues.camSpeed.getBasicValue() * 100.0F), value -> MenuValues.camSpeed.setAllValues(value/ 100.0F), 
        "configurable-headbob.tooltip.cam.speed.global", MenuValues.camSpeed::matchingValues);
    
    //Advanced Mode
    //This is such an ugly block of code...
    private final OptionInstance<Integer> viewIntensityHorizSlider = this.createSlider("configurable-headbob.arms.intensity.horiz", 
        Math.round(MenuValues.viewIntensity.transHorizontal * 100.0F), value -> MenuValues.viewIntensity.transHorizontal = value/ 100.0F,"configurable-headbob.tooltip.horiz");
    private final OptionInstance<Integer> viewIntensityVertSlider = this.createSlider("configurable-headbob.arms.intensity.vert", 
        Math.round(MenuValues.viewIntensity.transVertical * 100.0F), value -> MenuValues.viewIntensity.transVertical = value/ 100.0F,"configurable-headbob.tooltip.vert");
    private final OptionInstance<Integer> viewIntensityRollSlider = this.createSlider("configurable-headbob.arms.intensity.roll", 
        Math.round(MenuValues.viewIntensity.rotRoll * 100.0F), value -> MenuValues.viewIntensity.rotRoll = value/ 100.0F,"configurable-headbob.tooltip.roll");
    private final OptionInstance<Integer> viewIntensityPitchSlider = this.createSlider("configurable-headbob.arms.intensity.pitch", 
        Math.round(MenuValues.viewIntensity.rotPitch * 100.0F), value -> MenuValues.viewIntensity.rotPitch = value/ 100.0F,"configurable-headbob.tooltip.pitch");

    private final OptionInstance<Integer> viewScaleHorizSlider = this.createSlider("configurable-headbob.arms.speed.horiz", 
        Math.round(MenuValues.viewSpeed.transHorizontal * 100.0F), value -> MenuValues.viewSpeed.transHorizontal = value/ 100.0F,"configurable-headbob.tooltip.horiz");
    private final OptionInstance<Integer> viewScaleVertSlider = this.createSlider("configurable-headbob.arms.speed.vert", 
        Math.round(MenuValues.viewSpeed.transVertical * 100.0F), value -> MenuValues.viewSpeed.transVertical = value/ 100.0F,"configurable-headbob.tooltip.vert");
    private final OptionInstance<Integer> viewScaleRollSlider = this.createSlider("configurable-headbob.arms.speed.roll", 
        Math.round(MenuValues.viewSpeed.rotRoll * 100.0F), value -> MenuValues.viewSpeed.rotRoll = value/ 100.0F,"configurable-headbob.tooltip.roll");
    private final OptionInstance<Integer> viewScalePitchSlider = this.createSlider("configurable-headbob.arms.speed.pitch", 
        Math.round(MenuValues.viewSpeed.rotPitch * 100.0F), value -> MenuValues.viewSpeed.rotPitch = value/ 100.0F,"configurable-headbob.tooltip.pitch");
    
    private final OptionInstance<Integer> camIntensityHorizSlider = this.createSlider("configurable-headbob.cam.intensity.horiz", 
        Math.round(MenuValues.camIntensity.transHorizontal * 100.0F), value -> MenuValues.camIntensity.transHorizontal = value/ 100.0F,"configurable-headbob.tooltip.horiz");
    private final OptionInstance<Integer> camIntensityVertSlider = this.createSlider("configurable-headbob.cam.intensity.vert", 
        Math.round(MenuValues.camIntensity.transVertical * 100.0F), value -> MenuValues.camIntensity.transVertical = value/ 100.0F,"configurable-headbob.tooltip.vert");
    private final OptionInstance<Integer> camIntensityRollSlider = this.createSlider("configurable-headbob.cam.intensity.roll", 
        Math.round(MenuValues.camIntensity.rotRoll * 100.0F), value -> MenuValues.camIntensity.rotRoll = value/ 100.0F,"configurable-headbob.tooltip.roll");
    private final OptionInstance<Integer> camIntensityPitchSlider = this.createSlider("configurable-headbob.cam.intensity.pitch", 
        Math.round(MenuValues.camIntensity.rotPitch * 100.0F), value -> MenuValues.camIntensity.rotPitch = value/ 100.0F,"configurable-headbob.tooltip.pitch");    
    
    private final OptionInstance<Integer> camScaleHorizSlider = this.createSlider("configurable-headbob.cam.speed.horiz", 
        Math.round(MenuValues.camSpeed.transHorizontal * 100.0F), value -> MenuValues.camSpeed.transHorizontal = value/ 100.0F,"configurable-headbob.tooltip.horiz");
    private final OptionInstance<Integer> camScaleVertSlider = this.createSlider("configurable-headbob.cam.speed.vert", 
        Math.round(MenuValues.camSpeed.transVertical * 100.0F), value -> MenuValues.camSpeed.transVertical = value/ 100.0F,"configurable-headbob.tooltip.vert");
    private final OptionInstance<Integer> camScaleRollSlider = this.createSlider("configurable-headbob.cam.speed.roll", 
        Math.round(MenuValues.camSpeed.rotRoll * 100.0F), value -> MenuValues.camSpeed.rotRoll = value/ 100.0F,"configurable-headbob.tooltip.roll");
    private final OptionInstance<Integer> camScalePitchSlider = this.createSlider("configurable-headbob.cam.speed.pitch", 
        Math.round(MenuValues.camSpeed.rotPitch * 100.0F), value -> MenuValues.camSpeed.rotPitch = value/ 100.0F,"configurable-headbob.tooltip.pitch");
    

    public HeadbobMenu(Screen parentScreen, Options currentSetting){
        super(parentScreen, currentSetting, Component.translatable("configurable-headbob.settings.title"));
    }

    @Override
    protected void addContents(){
       
        // Basic / Advanced mode switcher button
        Button modeSelect = Button.builder(Component.literal("Uninitalized"), this::switchMode).width(150).build();
        modeSelect.setTooltip(Tooltip.create(Component.translatable("configurable-headbob.tooltip.mode")));
        updateModeButton(modeSelect); //Ensure the text is correct when initialized

        // Reset button
        Button resetButton = Button.builder(Component.translatable("configurable-headbob.reset"), clickedButton -> resetToDefault()).build();
        resetButton.setTooltip(Tooltip.create(Component.translatable("configurable-headbob.tooltip.reset")));

        //Cache the global toggle override so we can add a tooltip to it
        AbstractWidget globalToggle = options.bobView().createButton(options, 0, 0, 150, enabled -> {
            updateModeButton(modeSelect);
            addOptions();
            }
        );
        globalToggle.setTooltip(Tooltip.create(Component.translatable("configurable-headbob.tooltip.global")));

        //This creates the permanent list using the custom OptionsList we made.
        //Doing it this way works around the hurdles created by obfuscation-based access
        HeadbobOptionsList optionsList = new HeadbobOptionsList(Objects.requireNonNull(this.minecraft), this.width, this, globalToggle, modeSelect, resetButton);
        this.list = this.layout.addToContents(optionsList);
        addOptions();

    }

    @Override 
    protected void addOptions(){
        HeadbobOptionsList optionsList = (HeadbobOptionsList)Objects.requireNonNull(this.list, "Initialize list before adding options");
        syncAllOptions();
        optionsList.resetToFirstRow(); //Adds the Global toggle and Mode selection button

        if (options.bobView().get()){
            if (MenuValues.advMode){
                // Sliders in advanced mode
                addHeader(optionsList, "configurable-headbob.arms.intensity.global","configurable-headbob.tooltip.arms.intensity.global");
                optionsList.addBig(viewIntensityHorizSlider);
                optionsList.addBig(viewIntensityVertSlider);
                optionsList.addBig(viewIntensityRollSlider);
                optionsList.addBig(viewIntensityPitchSlider);
                addHeader(optionsList, "configurable-headbob.cam.intensity.global","configurable-headbob.tooltip.cam.intensity.global");
                optionsList.addBig(camIntensityHorizSlider);
                optionsList.addBig(camIntensityVertSlider);
                optionsList.addBig(camIntensityRollSlider);
                optionsList.addBig(camIntensityPitchSlider);
                addHeader(optionsList, "configurable-headbob.arms.speed.global","configurable-headbob.tooltip.arms.speed.global");
                optionsList.addBig(viewScaleHorizSlider);
                optionsList.addBig(viewScaleVertSlider);
                optionsList.addBig(viewScaleRollSlider);
                optionsList.addBig(viewScalePitchSlider);
                addHeader(optionsList, "configurable-headbob.cam.speed.global","configurable-headbob.tooltip.cam.speed.global");
                optionsList.addBig(camScaleHorizSlider);
                optionsList.addBig(camScaleVertSlider);
                optionsList.addBig(camScaleRollSlider);
                optionsList.addBig(camScalePitchSlider);
            } else {
                // Sliders in basic mode
                optionsList.addSmall(viewIntensitySlider, viewScaleSlider);
                optionsList.addSmall(camIntensitySlider, camScaleSlider);
            }
        }

        optionsList.addLastRow(); //Adds the reset button
    }

    @Override 
    public void removed(){
        super.removed();
        //Save settings when we close the menu
        MenuValues.save();
    }

    /*
    ==================================================================================================
     Helper methods beyond this point
    ==================================================================================================
    */

    private OptionInstance<Integer> createSlider(String sliderName, int theValue, Consumer<Integer> onChange, String tooltipKey){
        //We split this up into an Overload so we only need to worry about synchronization on the basic sliders.
        //It's kinda for laziness, but also thats 16 less lines of code to change :)
        return createSlider(sliderName, theValue, onChange, tooltipKey, () -> true);
    } 

    private OptionInstance<Integer> createSlider(String sliderName, int theValue, Consumer<Integer> onChange, String tooltipKey, BooleanSupplier matchingValues){
        return new OptionInstance<Integer>(sliderName, OptionInstance.cachedConstantTooltip(Component.translatable(tooltipKey)), (caption,value) -> pickSliderKey(caption, value, matchingValues.getAsBoolean()), 
            new OptionInstance.IntRange(0, 200), theValue, value ->  {if (!currentlySynchronizing){onChange.accept(value);}});
    } 


    private void switchMode(Button modeButton){
        MenuValues.advMode = !MenuValues.advMode;
        updateModeButton(modeButton);
        addOptions();
    }

    private void updateModeButton(Button modeButton){
        boolean buttonActive = options.bobView().get();
        modeButton.active = buttonActive;
        Component modeText;

        if (!buttonActive){
            modeText = CommonComponents.OPTION_OFF;
        } else if (MenuValues.advMode){
            modeText = Component.translatable("configurable-headbob.mode.advanced");
        } else {
            modeText = Component.translatable("configurable-headbob.mode.basic");
        }

        modeButton.setMessage(Component.translatable("configurable-headbob.mode").append(" ").append(modeText));
    }

    private Component pickSliderKey(Component caption, int theValue, boolean matchingValues) {
    Component theSliderKey;

    if (!matchingValues) {
        theSliderKey = Component.translatable("configurable-headbob.mode.custom");
    } else if (theValue == 0) {
        theSliderKey = CommonComponents.OPTION_OFF;
    } else {
        theSliderKey = Component.literal(String.valueOf(theValue / 100.0));
    }

    return caption.copy().append(": ").append(theSliderKey);
}

    private void resetToDefault(){
        
        //Ensure each of the components get reset to their default values.
        var sliderComponentGroups = java.util.List.of(MenuValues.viewIntensity,MenuValues.viewSpeed,MenuValues.camIntensity,MenuValues.camSpeed);

        for (var compGroup : sliderComponentGroups){
            compGroup.setAllValues(1.0F);
        }

        addOptions();
    }

    private void syncComponentGroup(MenuValues.SliderComponents currentGroup, OptionInstance<Integer> basic, OptionInstance<Integer> horizontal, 
        OptionInstance<Integer> vertical, OptionInstance<Integer> roll, OptionInstance<Integer> pitch){
        
        //Create a list of the components and values
        var sliderComponent = java.util.List.of(basic, horizontal, vertical, roll, pitch);
        var sliderValue = java.util.List.of(currentGroup.getBasicValue(), currentGroup.transHorizontal, currentGroup.transVertical, currentGroup.rotRoll, currentGroup.rotPitch);
        
        //Synchronize a single component group for a slider
        for (int i = 0; i<sliderComponent.size(); i++) {
            var currentSlider = sliderComponent.get(i);
            currentSlider.set(Math.round(sliderValue.get(i) * 100.0F));
        }    
    }

    private void syncAllOptions(){
        currentlySynchronizing = true;

        //Synchronize every slider with their values
        try {
            syncComponentGroup(MenuValues.viewIntensity, viewIntensitySlider, viewIntensityHorizSlider, viewIntensityVertSlider, viewIntensityRollSlider, viewIntensityPitchSlider);
            syncComponentGroup(MenuValues.viewSpeed, viewScaleSlider, viewScaleHorizSlider, viewScaleVertSlider, viewScaleRollSlider, viewScalePitchSlider);
            syncComponentGroup(MenuValues.camIntensity, camIntensitySlider, camIntensityHorizSlider, camIntensityVertSlider, camIntensityRollSlider, camIntensityPitchSlider);
            syncComponentGroup(MenuValues.camSpeed, camScaleSlider, camScaleHorizSlider, camScaleVertSlider, camScaleRollSlider, camScalePitchSlider);
        } finally {
                currentlySynchronizing = false;
            }
        
    }
    
    private void addHeader(HeadbobOptionsList optionsList, String labelKey, String tooltipKey){
        /*
        For some self reference on the StringWidget's values
        (0,0) - Initial Coordinates
        (310,20) - Width and Height. Matches MC's standard
        this.font - Minecraft's font renderer
         */
        StringWidget theHeader = new StringWidget(0,0,310,20, Component.translatable(labelKey),this.font).alignCenter();
        Component tooltipText = Component.translatable(tooltipKey).append("\n\n").append(Component.translatable("configurable-headbob.tooltip.values")); // Append the value defs

       theHeader.setTooltip(Tooltip.create(tooltipText));
       optionsList.addBig(theHeader);
    }

}