package me.jellysquid.mods.sodium.mixin.features.options;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Util;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow
    @Final
    private static Logger LOGGER;
    @Unique
    long timerthing = 0;

    @Inject(method="render", at=@At("HEAD"))
    private void athead(boolean tick, CallbackInfo ci) {
        this.timerthing = Util.getMeasuringTimeNano();
    }

    @Inject(method="render", at=@At("TAIL"))
    private void atret(boolean tick, CallbackInfo ci) {
        long elapsed = Util.getMeasuringTimeNano() - this.timerthing;
        if (elapsed > 1 * 1_000_000_000)
        {
            LOGGER.info("YO! FYI fam, tick was long ayo {}", elapsed);
        }

    }
}
