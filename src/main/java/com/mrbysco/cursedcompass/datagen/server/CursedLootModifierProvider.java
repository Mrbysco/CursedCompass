package com.mrbysco.cursedcompass.datagen.server;

import com.mrbysco.cursedcompass.CursedCompass;
import com.mrbysco.cursedcompass.loot.CursedLootModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class CursedLootModifierProvider extends GlobalLootModifierProvider {
	public CursedLootModifierProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(packOutput, lookupProvider, CursedCompass.MOD_ID);
	}

	@Override
	protected void start() {
		this.add("smelting", new CursedLootModifier(
				new LootItemCondition[]{
						AnyOfCondition.anyOf(
								LootTableIdCondition.builder(BuiltInLootTables.VILLAGE_CARTOGRAPHER.location()),
								LootTableIdCondition.builder(BuiltInLootTables.TRIAL_CHAMBERS_INTERSECTION_BARREL.location()),
								LootTableIdCondition.builder(BuiltInLootTables.STRONGHOLD_LIBRARY.location()),
								LootTableIdCondition.builder(BuiltInLootTables.ANCIENT_CITY.location()),
								LootTableIdCondition.builder(BuiltInLootTables.SHIPWRECK_MAP.location()),
								LootTableIdCondition.builder(BuiltInLootTables.SHIPWRECK_SUPPLY.location()),
								LootTableIdCondition.builder(BuiltInLootTables.SHIPWRECK_TREASURE.location())
						).build()
				})
		);
	}
}
