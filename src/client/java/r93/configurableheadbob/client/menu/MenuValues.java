package r93.configurableheadbob.client.menu;
import org.jspecify.annotations.NullMarked;

@NullMarked 
public class MenuValues {
    
    //Slider Values
    public static final SliderComponents viewIntensity = new SliderComponents();
    public static final SliderComponents viewSpeed = new SliderComponents();
    public static final SliderComponents camIntensity = new SliderComponents();
    public static final SliderComponents camSpeed = new SliderComponents();

    //Boolean Values
    public static boolean advMode = false;


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
    }


}
