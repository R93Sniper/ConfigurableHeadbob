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
    private void overrideHeadbob(CameraRenderState currentRenderState, PoseStack thePoseStack, float bobIntensity, float bobSpeedScale) {

        // Early exits
        if (!currentRenderState.entityRenderState.isPlayer) {
            // Mirror vanilla behavior for non-players
            return;
        } else if (bobIntensity == 0) {
            // Intensity 0 means off
            return;
        } else if (bobSpeedScale == 0) {
            // Speed Scale of 0 is off as well
            return;
        }

        // Grab values to start scaling
        float walkDistance = currentRenderState.entityRenderState.backwardsInterpolatedWalkDistance;
        float currentBobIntensity = currentRenderState.entityRenderState.bob;
        float bobPhase = walkDistance * bobSpeedScale * (float) Math.PI;
        float bobAmplitude = currentBobIntensity * bobIntensity;

        /*
         I had to have this bit of math explained to me because it's trying to match what MC does for its default values
         
         Translation (Horizontal) = sin(Phase) X Amplitude X 0.5F
         Translation (Vertical) = -abs(cos(Phase)) X Amplitude)
         Roll = sin(Phase) X Amplitude * 3.0F
         Pitch = abs(cos(Phase - 0.2F) X Amplitude) * 5.0F
         
         The math (including its constants) match vanilla's headbobbing math
         */
        float horizMove = Mth.sin(bobPhase) * bobAmplitude * 0.5F;
        float vertMove = -Mth.abs(Mth.cos(bobPhase) * bobAmplitude);
        float rollRot = Mth.sin(bobPhase) * bobAmplitude * 3.0F;
        float pitchRot = Mth.abs(Mth.cos(bobPhase - 0.2F) * bobAmplitude ) * 5.0F;

        thePoseStack.translate(horizMove,vertMove, 0.0F);
        thePoseStack.rotateDegrees(Axis.ZP, rollRot);
        thePoseStack.rotateDegrees(Axis.XP, pitchRot);


    }
}
