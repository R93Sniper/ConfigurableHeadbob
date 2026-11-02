package r93.configurableheadbob.client.menu;

import org.jspecify.annotations.NullMarked;
import java.util.Objects;
import java.util.HashMap;
import java.util.Map;


import net.minecraft.client.Options;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.CommonComponents;


@NullMarked 
public class HeadbobMenu extends OptionsSubScreen {
    
    private boolean currentlySynchronizing = false;

    //Defining the sliders for the menu
    //Basic Mode
    private final OptionInstance<Integer> viewIntensitySlider = this.createSlider("configurable-headbob.arms.intensity.global", 
        Math.round(MenuValues.viewIntensity.getBasicValue() * 100.0F), value -> MenuValues.viewIntensity.setAllValues(value/ 100.0F), "configurable-headbob.tooltip.arms.intensity.global");
    private final OptionInstance<Integer> viewScaleSlider = this.createSlider("configurable-headbob.arms.speed.global", 
        Math.round(MenuValues.viewSpeed.getBasicValue() * 100.0F), value -> MenuValues.viewSpeed.setAllValues(value/ 100.0F), "configurable-headbob.tooltip.arms.speed.global");
    private final OptionInstance<Integer> camIntensitySlider = this.createSlider("configurable-headbob.cam.intensity.global", 
        Math.round(MenuValues.camIntensity.getBasicValue() * 100.0F), value -> MenuValues.camIntensity.setAllValues(value/ 100.0F), "configurable-headbob.tooltip.cam.intensity.global");
    private final OptionInstance<Integer> camScaleSlider = this.createSlider("configurable-headbob.cam.speed.global", 
        Math.round(MenuValues.camSpeed.getBasicValue() * 100.0F), value -> MenuValues.camSpeed.setAllValues(value/ 100.0F), "configurable-headbob.tooltip.cam.speed.global");
    
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
    
    //Hash Maps for hiding/showing fields depending on what mode we're in
    private final Map<ContainerObjectSelectionList.Entry<?>, Integer> basicRows = new HashMap<>();
    private final Map<ContainerObjectSelectionList.Entry<?>, Integer> advRows = new HashMap<>();

    public HeadbobMenu(Screen parentScreen, Options currentSetting){
        super(parentScreen, currentSetting, Component.translatable("configurable-headbob.settings.title"));
    }

    @Override 
    protected void addOptions(){
        OptionsList optionsList = Objects.requireNonNull(this.list, "Initialize list before adding options"); //This gets VSC to shut up about nullability
        basicRows.clear();
        advRows.clear();

        Button modeSelect = Button.builder(Component.literal("Uninitalized"), this::switchMode).width(150).build();
        modeSelect.setTooltip(Tooltip.create(Component.translatable("configurable-headbob.tooltip.mode")));
        updateModeButton(modeSelect); //Ensure the text is correct when initialized

        Button resetButton = Button.builder(Component.translatable("configurable-headbob.reset"), clickedButton -> resetToDefault()).build();

        //Cache the global toggle override so we can add a tooltip to it
        AbstractWidget globalToggle = options.bobView().createButton(options, 0, 0, 150, enabled -> {
            updateModeButton(modeSelect);
            toggleSliders(optionsList);
            }
        );

        globalToggle.setTooltip(Tooltip.create(Component.translatable("configurable-headbob.tooltip.global")));

        optionsList.addSmall(globalToggle, modeSelect);
        
        // Sliders in basic mode
        int basicIndex = optionsList.children().size();
        optionsList.addSmall(viewIntensitySlider, viewScaleSlider);
        optionsList.addSmall(camIntensitySlider, camScaleSlider);

        //Mark the location of the Basic Mode sliders in the Hash Map
        for (ContainerObjectSelectionList.Entry<?> basicRow : optionsList.children().subList(basicIndex, optionsList.children().size())){
            basicRows.put(basicRow, basicRow.getHeight());
        }

        // Sliders in advanced mode
        int advIndex = optionsList.children().size();             
        //optionsList.addHeader(Component.translatable("configurable-headbob.arms.intensity.global"));
        //optionsList.addSmall(viewIntensityHorizSlider, viewIntensityVertSlider);
        //optionsList.addSmall(viewIntensityRollSlider, viewIntensityPitchSlider);
        addHeader(optionsList,"configurable-headbob.arms.intensity.global", "configurable-headbob.tooltip.arms.intensity.global");
        optionsList.addBig(viewIntensityHorizSlider);
        optionsList.addBig(viewIntensityVertSlider);
        optionsList.addBig(viewIntensityRollSlider);
        optionsList.addBig(viewIntensityPitchSlider);
        //optionsList.addHeader(Component.translatable("configurable-headbob.cam.intensity.global"));
        //optionsList.addSmall(camIntensityHorizSlider, camIntensityVertSlider);
        //optionsList.addSmall(camIntensityRollSlider, camIntensityPitchSlider);
        addHeader(optionsList,"configurable-headbob.cam.intensity.global", "configurable-headbob.tooltip.cam.intensity.global");
        optionsList.addBig(camIntensityHorizSlider);
        optionsList.addBig(camIntensityVertSlider);
        optionsList.addBig(camIntensityRollSlider);
        optionsList.addBig(camIntensityPitchSlider);
        //optionsList.addHeader(Component.translatable("configurable-headbob.arms.speed.global"));
        //optionsList.addSmall(viewScaleHorizSlider, viewScaleVertSlider);
        //optionsList.addSmall(viewScaleRollSlider, viewScalePitchSlider);
        addHeader(optionsList,"configurable-headbob.arms.speed.global", "configurable-headbob.tooltip.arms.speed.global");
        optionsList.addBig(viewScaleHorizSlider);
        optionsList.addBig(viewScaleVertSlider);
        optionsList.addBig(viewScaleRollSlider);
        optionsList.addBig(viewScalePitchSlider);
        //optionsList.addHeader(Component.translatable("configurable-headbob.cam.speed.global"));
        //optionsList.addSmall(camScaleHorizSlider, camScaleVertSlider);
        //optionsList.addSmall(camScaleRollSlider, camScalePitchSlider);
        addHeader(optionsList,"configurable-headbob.cam.speed.global", "configurable-headbob.tooltip.cam.speed.global");
        optionsList.addBig(camScaleHorizSlider);
        optionsList.addBig(camScaleVertSlider);
        optionsList.addBig(camScaleRollSlider);
        optionsList.addBig(camScalePitchSlider);

        //Mark the location of the Advanced Mode sliders in the Hash Map
        for (ContainerObjectSelectionList.Entry<?> advRow : optionsList.children().subList(advIndex, optionsList.children().size())){
            advRows.put(advRow, advRow.getHeight());
        }

        //Add the reset button and then toggle the appropriate components
        optionsList.addBig(resetButton);
        resetButton.setTooltip(Tooltip.create(Component.translatable("configurable-headbob.tooltip.reset")));
        syncAllOptions();
        toggleSliders(optionsList);
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

    private OptionInstance<Integer> createSlider(String sliderName, int theValue, OptionInstance.ValueUpdateListener<Integer> onChange, String tooltipKey){
        return new OptionInstance<Integer>(sliderName, OptionInstance.cachedConstantTooltip(Component.translatable(tooltipKey)), (caption,value) -> pickSliderKey(caption, value, true), 
            new OptionInstance.IntRange(0, 200), theValue, value ->  {if (!currentlySynchronizing){onChange.valueChanged(value);}});
    } 

    private void toggleSliders(OptionsList theList){
        boolean bobbingActive = options.bobView().get();

        setRowVisibility(basicRows, bobbingActive && !MenuValues.advMode);
        setRowVisibility(advRows, bobbingActive && MenuValues.advMode);
        theList.setScrollAmount(0.0);
    }
    
    private void setRowVisibility(Map<ContainerObjectSelectionList.Entry<?>,Integer> currentRows, boolean visibility){
        
        //Iterate over every key value pair in the map
        for (var savedRow : currentRows.entrySet()){
            var menuRow = savedRow.getKey(); 
            if (visibility){
                menuRow.setHeight(savedRow.getValue()); //Restore the saved height of the item
            } else {
                menuRow.setHeight(0); //Set item height to 0, effectively making it invisible
            }

            //Update the current widget's state
            for (var child : menuRow.children()){
                if (child instanceof AbstractWidget currentWidget){
                    currentWidget.visible = visibility;
                    currentWidget.active = visibility;
                }
            }
        }
    }

    private void switchMode(Button modeButton){
        
        MenuValues.advMode = !MenuValues.advMode;
        updateModeButton(modeButton);
        
        syncAllOptions();
        OptionsList optionsList = Objects.requireNonNull(this.list, "Initialize list before adding options");
        toggleSliders(optionsList);
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

        syncAllOptions();
    }

    private void syncComponentGroup(MenuValues.SliderComponents currentGroup, OptionInstance<Integer> basic, OptionInstance<Integer> horizontal, 
        OptionInstance<Integer> vertical, OptionInstance<Integer> roll, OptionInstance<Integer> pitch, String translationKey){
        
        //Create a list of the components and values
        var sliderComponent = java.util.List.of(basic, horizontal, vertical, roll, pitch);
        var sliderValue = java.util.List.of(currentGroup.getBasicValue(), currentGroup.transHorizontal, currentGroup.transVertical, currentGroup.rotRoll, currentGroup.rotPitch);
        
        //Synchronize a single component group for a slider
        for (int i = 0; i<sliderComponent.size(); i++) {
            var currentSlider = sliderComponent.get(i);
            currentSlider.set(Math.round(sliderValue.get(i) * 100.0F));
            resetOption(currentSlider);
        }

        //Synchronize the state of the basic sliders. Needed for notifying users when advanced settings have been used
        OptionsList optionsList = Objects.requireNonNull(this.list, "Initialize the list before refreshing slider labels");
        AbstractWidget basicWidget = Objects.requireNonNull(optionsList.findOption(basic), "Initialize the list before refreshing slider labels");
        basicWidget.setMessage(pickSliderKey(Component.translatable(translationKey), basic.get(), currentGroup.matchingValues()));

    
    }

    private void syncAllOptions(){
        currentlySynchronizing = true;

        //Synchronize every slider with their values
        try {
            syncComponentGroup(MenuValues.viewIntensity, viewIntensitySlider, viewIntensityHorizSlider, viewIntensityVertSlider, viewIntensityRollSlider, viewIntensityPitchSlider,"configurable-headbob.arms.intensity.global");
            syncComponentGroup(MenuValues.viewSpeed, viewScaleSlider, viewScaleHorizSlider, viewScaleVertSlider, viewScaleRollSlider, viewScalePitchSlider,"configurable-headbob.arms.speed.global");
            syncComponentGroup(MenuValues.camIntensity, camIntensitySlider, camIntensityHorizSlider, camIntensityVertSlider, camIntensityRollSlider, camIntensityPitchSlider,"configurable-headbob.cam.intensity.global");
            syncComponentGroup(MenuValues.camSpeed, camScaleSlider, camScaleHorizSlider, camScaleVertSlider, camScaleRollSlider, camScalePitchSlider,"configurable-headbob.cam.speed.global");
        } finally {
                currentlySynchronizing = false;
            }
        
    }
    
    private void addHeader(OptionsList optionsList, String labelKey, String tooltipKey){
        optionsList.addHeader(Component.translatable(labelKey));

        ContainerObjectSelectionList.Entry<?> header = optionsList.children().getLast();
        Component tooltipText = Component.translatable(tooltipKey).append("\n\n").append(Component.translatable("configurable-headbob.tooltip.values")); // Append the value defs

        for(var child : header.children()){
            if(child instanceof AbstractWidget widget){
                widget.setTooltip(Tooltip.create(tooltipText));
            }
        }
    }

}
