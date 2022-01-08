/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.core.loot;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.Consumer;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

public final class LootHandler {

	public static void lootLoad(ResourceLocation id, Consumer<LootPool> addPool) {
		String prefix = "minecraft:chests/";
		String name = id.toString();

		if (name.startsWith(prefix)) {
			String file = name.substring(name.indexOf(prefix) + prefix.length());
			switch (file) {
				case "abandoned_mineshaft":
				case "desert_pyramid":
				case "jungle_temple":
				case "simple_dungeon":
				case "spawn_bonus_chest":
				case "stronghold_corridor":
					addPool.accept(getInjectPool(file));
					break;
				case "village/village_temple":
				case "village/village_toolsmith":
				case "village/village_weaponsmith":
					addPool.accept(getInjectPool("village_chest"));
					break;
				default:
					break;
			}
		}
	}

	public static LootPool getInjectPool(String entryName) {
		return LootPool.lootPool()
				.add(getInjectEntry(entryName, 1))
				.setBonusRolls(UniformGenerator.between(0, 1))
				.build();
	}

	private static LootPoolEntryContainer.Builder<?> getInjectEntry(String name, int weight) {
		ResourceLocation table = prefix("inject/" + name);
		return LootTableReference.lootTableReference(table)
				.setWeight(weight);
	}

}
