package com.mikeretriever.extraeffects;

import dev.architectury.platform.forge.EventBuses
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import com.mikeretriever.extraeffects.effects.*
import com.cobblemon.mod.common.api.pokemon.effect.ShoulderEffectRegistry

@Mod("cobblemonmikeeffects")
@Mod.EventBusSubscriber(modid = "cobblemon", bus = Mod.EventBusSubscriber.Bus.MOD)
class CobblemonMikeExtraEffects {

    init {
        with(thedarkcolour.kotlinforforge.forge.MOD_BUS) {
            EventBuses.registerModEventBus("cobblemonmikeeffects", this)
            addListener(this@CobblemonMikeExtraEffects::initialize)
            addListener(this@CobblemonMikeExtraEffects::serverInit)
        }
    }

    private val effectsToRegister = listOf(
        "light_source" to LightSourceEffect::class.java,
        "slow_fall" to SlowFallEffect::class.java,
        "haste" to HasteEffect::class.java,
        "water_breathing" to WaterBreathingEffect::class.java,
        "saturation" to SaturationEffect::class.java,
        "speed" to SpeedEffect::class.java,
        "dolphin_grace" to DolphinGraceEffect::class.java,
        "conduit" to ConduitEffect::class.java,
        "invisibility" to InvisibilityEffect::class.java,
        "jump_boost" to JumpBoostEffect::class.java,
        "night_vision" to NightVisionEffect::class.java,
        "regeneration" to RegenerationEffect::class.java,
        "resistance" to ResistanceEffect::class.java,
        "strength" to StrengthEffect::class.java,
        "fire_resistance" to FireResistanceEffect::class.java,
        "health_boost" to HealthBoostEffect::class.java
    )

    private fun registerEffects() {
        for (effects in effectsToRegister) {
            val (effectName, effectClass) = effects
            val existingEffect = ShoulderEffectRegistry.get(effectName)
            System.out.println("Cobblemon Mike Effects trying to register: $effectName");

            if (existingEffect != null) {
                System.out.println("Cobblemon Mike Effects found that already exists in the register: $effectName");

                ShoulderEffectRegistry.unregister(effectName)
            }
            ShoulderEffectRegistry.register(effectName, effectClass)
        }
    }

    fun serverInit(event: FMLDedicatedServerSetupEvent) {
        System.out.println("Cobblemon Mike Extra Effects Server Phase /r/n Cobblemon Mike Extra Effects Server Phase /r/n Cobblemon Mike Extra Effects Server Phase /r/n Cobblemon Mike Extra Effects Server Phase /r/n Cobblemon Mike Extra Effects Server Phase /r/n Cobblemon Mike Extra Effects Server Phase /r/n Cobblemon Mike Extra Effects Server Phase /r/n ");
        registerEffects();
    }

    fun initialize(event: FMLCommonSetupEvent) {
        System.out.println("Cobblemon Mike Extra Effects Init. /r/n Cobblemon Mike Extra Effects Init. /r/nCobblemon Mike Extra Effects Init. /r/nCobblemon Mike Extra Effects Init. /r/nCobblemon Mike Extra Effects Init. /r/nCobblemon Mike Extra Effects Init. /r/n Cobblemon Mike Extra Effects Init. /r/n Cobblemon Mike Extra Effects Init. /r/nCobblemon Mike Extra Effects Init. /r/nCobblemon Mike Extra Effects Init. /r/nCobblemon Mike Extra Effects Init. /r/nCobblemon Mike Extra Effects Init. /r/n Cobblemon Mike Extra Effects Init. /r/n Cobblemon Mike Extra Effects Init. /r/nCobblemon Mike Extra Effects Init. /r/nCobblemon Mike Extra Effects Init. /r/nCobblemon Mike Extra Effects Init. /r/nCobblemon Mike Extra Effects Init. /r/n Cobblemon Mike Extra Effects Init. /r/n Cobblemon Mike Extra Effects Init. /r/nCobblemon Mike Extra Effects Init. /r/nCobblemon Mike Extra Effects Init. /r/nCobblemon Mike Extra Effects Init. /r/nCobblemon Mike Extra Effects Init. /r/n Cobblemon Mike Extra Effects Init. /r/n Cobblemon Mike Extra Effects Init. /r/nCobblemon Mike Extra Effects Init. /r/nCobblemon Mike Extra Effects Init. /r/nCobblemon Mike Extra Effects Init. /r/nCobblemon Mike Extra Effects Init. /r/n");

    }
}
