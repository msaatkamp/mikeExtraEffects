/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.mikeretriever.extraeffects.shoulderEffects

import com.cobblemon.mod.common.api.pokemon.effect.ShoulderEffect
import com.cobblemon.mod.common.pokemon.Pokemon
import com.mikeretriever.extraeffects.statusEffects.ShoulderStatusEffect
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import java.time.Instant
import java.util.UUID

class DolphinGraceEffect : ShoulderEffect {

    private val lastTimeUsed: MutableMap<UUID, Long> = mutableMapOf()
    private val cooldown: Int = 120 // 2 minutes in seconds
    private val buffName: String = "Dolphin Grace"
    private val buffDurationSeconds: Int = 300

    override fun applyEffect(pokemon: Pokemon, player: ServerPlayerEntity, isLeft: Boolean) {
        val effect = player.statusEffects.filterIsInstance<DolphinGraceShoulderStatusEffect>().firstOrNull()
        val lastTimeUse = lastTimeUsed[pokemon.uuid]
        val currentTime = Instant.now().epochSecond
        val timeDiff = if (lastTimeUse != null) currentTime - lastTimeUse else Long.MAX_VALUE

        if (effect != null) {
            effect.pokemonIds.add(pokemon.uuid)
        }
        if (effect == null){
            if(lastTimeUse?.let {it > currentTime} == true) {
                lastTimeUsed[pokemon.uuid] = Instant.now().epochSecond
            }
            if (timeDiff >= cooldown) {
                lastTimeUsed[pokemon.uuid] = Instant.now().epochSecond + buffDurationSeconds

                player.addStatusEffect(
                    DolphinGraceShoulderStatusEffect(
                        mutableListOf(pokemon.uuid),
                        buffName,
                        buffDurationSeconds
                    )
                )

                player.sendMessage(Text.literal("$buffName effect applied from ${pokemon.species.name}."))
            } else {
                player.sendMessage(Text.literal("$buffName effect is still on cooldown for ${cooldown - timeDiff} seconds."))
            }

        }
    }

    override fun removeEffect(pokemon: Pokemon, player: ServerPlayerEntity, isLeft: Boolean) {
        val effect = player.statusEffects.filterIsInstance<DolphinGraceShoulderStatusEffect>().firstOrNull()
        val lastTimeUse = lastTimeUsed[pokemon.uuid]
        val currentTime = Instant.now().epochSecond

        if(lastTimeUse?.let {it > currentTime} == true) {
            lastTimeUsed[pokemon.uuid] = Instant.now().epochSecond
        }
        if (effect != null) {
            effect.pokemonIds.remove(pokemon.uuid)
        }
    }
 
    class DolphinGraceShoulderStatusEffect(pokemonIds: MutableList<UUID>, buffName: String, duration: Int) : ShoulderStatusEffect(pokemonIds, StatusEffects.DOLPHINS_GRACE, duration * 20, buffName ) {}

}
