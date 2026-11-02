package r93.configurableheadbob.client.mixin;

import r93.configurableheadbob.client.menu.MenuValues;
import org.jspecify.annotations.NullMarked;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.util.Mth;


@NullMarked 
@Mixin(GameRenderer.class) 
public class ViewbobOverrideMixin {
    
    //Intercept the call for the camera's headbobbing
    @Redirect(
    method = "renderLevel",
    at = @At(
        value = "INVOKE",
        target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lnet/minecraft/client/renderer/state/level/CameraRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;)V"
        )
    )
    private void interceptCamHeadbob(GameRenderer theRenderer, CameraRenderState currentRenderState, PoseStack thePoseStack) {
        overrideHeadbob(currentRenderState, thePoseStack, MenuValues.camIntensity, MenuValues.camSpeed);
    }

    //Intercept the call for the viewmodel's headbobbing
    @Redirect(
    method = "renderItemInHand",
    at = @At(
        value = "INVOKE",
        target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lnet/minecraft/client/renderer/state/level/CameraRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;)V"
        )
    )
    private void interceptViewHeadbob(GameRenderer theRenderer, CameraRenderState currentRenderState, PoseStack thePoseStack) {
        overrideHeadbob(currentRenderState, thePoseStack, MenuValues.viewIntensity, MenuValues.viewSpeed);
    }

    @Unique
    private void overrideHeadbob(CameraRenderState currentRenderState, PoseStack thePoseStack, MenuValues.SliderComponents bobIntensity, MenuValues.SliderComponents bobSpeedScale) {

        // Early exit. Mirrors vanilla behavior for non-players
        if (!currentRenderState.entityRenderState.isPlayer) {
            return;
        } 
        

        // Grab values to start scaling
        float walkDistance = currentRenderState.entityRenderState.backwardsInterpolatedWalkDistance;
        float currentBobIntensity = currentRenderState.entityRenderState.bob;
        
        //Phase Components
        //float bobPhase = walkDistance * bobSpeedScale.getBasicValue() * (float) Math.PI;
        float bobPhaseHoriz = walkDistance * bobSpeedScale.transHorizontal * (float) Math.PI;
        float bobPhaseVert = walkDistance * bobSpeedScale.transVertical * (float) Math.PI;
        float bobPhaseRoll = walkDistance * bobSpeedScale.rotRoll * (float) Math.PI;
        float bobPhasePitch = walkDistance * bobSpeedScale.rotPitch * (float) Math.PI;

        //Amplitude components
        //float bobAmplitude = currentBobIntensity * bobIntensity.getBasicValue();
        float bobAmplitudeHoriz = currentBobIntensity * bobIntensity.transHorizontal;
        float bobAmplitudeVert = currentBobIntensity * bobIntensity.transVertical;
        float bobAmplitudeRoll = currentBobIntensity * bobIntensity.rotRoll;
        float bobAmplitudePitch = currentBobIntensity * bobIntensity.rotPitch;

        /*
         I had to have this bit of math explained to me because it's trying to match what MC does for its default values
         
         Translation (Horizontal) = sin(Phase) X Amplitude X 0.5F
         Translation (Vertical) = -abs(cos(Phase)) X Amplitude)
         Roll = sin(Phase) X Amplitude * 3.0F
         Pitch = abs(cos(Phase - 0.2F) X Amplitude) * 5.0F
         
         The math (including its constants) match vanilla's headbobbing math
         */
        float horizMove;
        float vertMove;
        float rollRot;
        float pitchRot;
        //Horizontal Component
        if (bobSpeedScale.transHorizontal == 0.0F) {
            horizMove = 0.0F;
        } else {
            horizMove = Mth.sin(bobPhaseHoriz) * bobAmplitudeHoriz * 0.5F;
        }

        //Vertical Component
        if (bobSpeedScale.transVertical == 0.0F) {
            vertMove = 0.0F;
        } else {
            vertMove = -Mth.abs(Mth.cos(bobPhaseVert) * bobAmplitudeVert);
        }

        //Roll Component
        if (bobSpeedScale.rotRoll == 0.0F) {
            rollRot = 0.0F;
        } else {
            rollRot = Mth.sin(bobPhaseRoll) * bobAmplitudeRoll * 3.0F;
        }

        //Pitch Component
        if (bobSpeedScale.rotPitch == 0.0F) {
            pitchRot = 0.0F;
        } else {
            pitchRot = Mth.abs(Mth.cos(bobPhasePitch - 0.2F) * bobAmplitudePitch) * 5.0F;
        }

        thePoseStack.translate(horizMove,vertMove, 0.0F);
        thePoseStack.rotateDegrees(Axis.ZP, rollRot);
        thePoseStack.rotateDegrees(Axis.XP, pitchRot);


    }
}
