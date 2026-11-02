package r93.configurableheadbob.client.menu;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import net.fabricmc.loader.api.FabricLoader;
import r93.configurableheadbob.ConfigurableHeadbob;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;



@NullMarked 
public class MenuValues {
    
    //Slider Values
    public static final SliderComponents viewIntensity = new SliderComponents();
    public static final SliderComponents viewSpeed = new SliderComponents();
    public static final SliderComponents camIntensity = new SliderComponents();
    public static final SliderComponents camSpeed = new SliderComponents();

    //Boolean Values
    public static boolean advMode = false;

    //Content needed for saving config
    private static final Map<String, SliderComponents> COMPONENT_GROUPS = Map.of(
        "viewIntensity", viewIntensity,
        "viewSpeed", viewSpeed,
        "camIntensity", camIntensity,
        "camSpeed", camSpeed);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create(); //Set pretty is for readability, not necessary but helpful.
    private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("configurable-headbob.json");

    //Actually saving the config
    public static void save(){
        JsonObject savedSettings = new JsonObject();

        savedSettings.addProperty("advMode", advMode);

        for (var group: COMPONENT_GROUPS.entrySet()){
            savedSettings.add(group.getKey(), GSON.toJsonTree(group.getValue()));
        }
    
        //Basic exception handling, since IOExceptions are a thing.
        try{
            Files.createDirectories(CONFIG_FILE.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_FILE)) {
                GSON.toJson(savedSettings, writer);
            }
        } catch (IOException exception) {
            ConfigurableHeadbob.LOGGER.error("Settings could not be saved!", exception);

        }
    }

    public static void load() {
        if (!Files.exists(CONFIG_FILE)) {
            return;
        }

        try (Reader reader = Files.newBufferedReader(CONFIG_FILE)) {
            JsonObject savedSettings = JsonParser.parseReader(reader).getAsJsonObject();

            boolean loadedAdvMode = advMode;

            @Nullable
            JsonElement modeValue = savedSettings.get("advMode");

            if (modeValue != null) {
                if (!modeValue.isJsonPrimitive() || !modeValue.getAsJsonPrimitive().isBoolean()) {
                    throw new JsonParseException("advMode must be a boolean");
                }

                loadedAdvMode = modeValue.getAsBoolean();
            }

            var loadedGroups = new HashMap<String, SliderComponents>();

            for (var group : COMPONENT_GROUPS.entrySet()) {
                @Nullable
                JsonElement groupData = savedSettings.get(group.getKey());

                if (groupData == null) {
                    continue;
                }

                if (!groupData.isJsonObject()){
                    throw new JsonParseException("Component group must be an object: " + group.getKey());
                }

                SliderComponents loadedGroup = GSON.fromJson(groupData, SliderComponents.class);

                loadedGroups.put(group.getKey(), loadedGroup);
            }

            // Apply settings only after the entire file has been read.
            for (var group : COMPONENT_GROUPS.entrySet()) {
                @Nullable
                SliderComponents loadedGroup = loadedGroups.get(group.getKey());

                if (loadedGroup != null) {
                    group.getValue().copyValues(loadedGroup);
                }
            }

            advMode = loadedAdvMode;
        } catch (IOException | JsonParseException | IllegalStateException exception) {
            ConfigurableHeadbob.LOGGER.error("Settings could not be loaded; keeping current values", exception);
        }
    }

    //Helper Class to make it easier to deal with the split components of the sliders
    public static final class SliderComponents{
        public float transHorizontal = 1.0F;
        public float transVertical = 1.0F;
        public float rotRoll = 1.0F;
        public float rotPitch = 1.0F;
        private float basicVal = 1.0F;

        public void setAllValues(float newVal){
            transHorizontal = newVal;
            transVertical = newVal;
            rotRoll = newVal;
            rotPitch = newVal;
            basicVal = newVal;
        }

        public float getBasicValue(){
            if (matchingValues())
                return transHorizontal;
            return basicVal;
        }

        public boolean matchingValues(){
            return (transHorizontal == transVertical && transHorizontal == rotRoll && transHorizontal == rotPitch);
        }

        private static float validateValue(float theValue){
            if (!Float.isFinite(theValue)){
                return 1.0F;
            }

            float newValue = Math.clamp(theValue, 0.0F, 2.0F);
            return Math.round(newValue * 100.0F) / 100.0F;
        }

        public void copyValues(SliderComponents savedComponent){
            transHorizontal = validateValue(savedComponent.transHorizontal);
            transVertical = validateValue(savedComponent.transVertical);
            rotRoll = validateValue(savedComponent.rotRoll);
            rotPitch = validateValue(savedComponent.rotPitch);
            basicVal = validateValue(savedComponent.basicVal);
        }
    }


}
