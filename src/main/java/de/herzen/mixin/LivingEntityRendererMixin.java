package de.herzen.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void herzen$addHearts(LivingEntity entity, LivingEntityRenderState state,
                                  float partialTick, CallbackInfo ci) {
        // nur Spieler, und nur wenn überhaupt ein Nametag angezeigt wird
        if (!(entity instanceof Player player) || state.nameTag == null) {
            return;
        }

        float hearts = (player.getHealth() + player.getAbsorptionAmount()) / 2.0f;
        String zahl = (hearts == Math.floor(hearts))
                ? String.valueOf((int) hearts)
                : String.format("%.1f", hearts);

        state.nameTag = state.nameTag.copy()
                .append(Component.literal(" \u2764 " + zahl).withStyle(ChatFormatting.RED));
    }
}
