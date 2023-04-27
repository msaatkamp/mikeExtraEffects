/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.mikeretriever.extraeffects.statusEffects

import com.cobblemon.mod.common.util.DataKeys.POKEMON_UUID
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import java.util.UUID


abstract class ShoulderStatusEffect(
    internal val pokemonIds: MutableList<UUID>,
    private val effect: StatusEffect,
    private var effectDurationSeconds: Int,
    private val buffName: String
) : StatusEffectInstance(effect, effectDurationSeconds, 0, false, false, false) {
    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        super.writeNbt(nbt)
        nbt.putInt("id", -999)
        return nbt
    }

    override fun update(entity: LivingEntity, overwriteCallback: Runnable?): Boolean {
        entity as ServerPlayerEntity

        val hasShoulderedPokemon = isShoulderedPokemon(entity.shoulderEntityLeft) || isShoulderedPokemon(entity.shoulderEntityRight)

        if(duration % 20 == 0) {
            entity.sendMessage(Text.literal("Keeping effect: $buffName. StillInShoulder? $hasShoulderedPokemon"))
        }

        if(duration == 200 && duration % 20 === 0 ) {
            entity.sendMessage(Text.literal("$buffName will be worn out in ${duration / 20} seconds.."))
        }

        if(duration == 20*290) {
            entity.sendMessage(Text.literal("Your $buffName is being removed early."))
            entity.activeStatusEffects.remove(this.effect)
        }

        if (duration == 20) { // Last Sec Warning
            entity.sendMessage(Text.literal("Your $buffName is fading out."))
        }

        if (!hasShoulderedPokemon) {
            entity.sendMessage(Text.literal("Trying to remove effect: $buffName"))
            if (duration >= 0) {
                duration = 0
                entity.sendMessage(Text.literal("Starting to remove . . . ."))
            }
        }

        return super.update(entity, overwriteCallback)
    }

    private fun isShoulderedPokemon(shoulderEntity: NbtCompound): Boolean {
        val pokemonNBT = shoulderEntity.getCompound("Pokemon")
        return pokemonNBT.containsUuid(POKEMON_UUID) && pokemonNBT.getUuid(POKEMON_UUID) in pokemonIds
    }
}