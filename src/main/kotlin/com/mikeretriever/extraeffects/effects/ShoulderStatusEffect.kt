/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.mikeretriever.extraeffects.effects

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
    private val effectDurationSeconds: Int,
    private val buffName: String
) : StatusEffectInstance(effect, effectDurationSeconds, 0, true, true, false) {
    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        super.writeNbt(nbt)
        nbt.putInt("id", -999)
        return nbt
    }

    override fun update(entity: LivingEntity, overwriteCallback: Runnable?): Boolean {
        entity as ServerPlayerEntity

        val hasShoulderedPokemon = isShoulderedPokemon(entity.shoulderEntityLeft) || isShoulderedPokemon(entity.shoulderEntityRight)

        if(duration == 200 && duration % 20 === 0 ) {
            entity.sendMessage(Text.literal("$buffName will be worn out in ${duration / 20} seconds.."))
        }

        if (duration == 20) { // Last Sec Warning
            entity.sendMessage(Text.literal("Your $buffName is fading out."))
        }
        if (!hasShoulderedPokemon) {
            if (duration >= 0) {
                entity.sendMessage(Text.literal("Your pokemon $buffName was removed."))
            }
        }

        return super.update(entity, overwriteCallback)
    }

    private fun isShoulderedPokemon(shoulderEntity: NbtCompound): Boolean {
        val pokemonNBT = shoulderEntity.getCompound("Pokemon")
        return pokemonNBT.containsUuid(POKEMON_UUID) && pokemonNBT.getUuid(POKEMON_UUID) in pokemonIds
    }
}
