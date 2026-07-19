package com.mrbysco.cursedcompass.datagen;

import com.mrbysco.cursedcompass.datagen.client.CursedLanguageProvider;
import com.mrbysco.cursedcompass.datagen.client.CursedModelProvider;
import com.mrbysco.cursedcompass.datagen.server.CursedEntityTypeTagsProvider;
import com.mrbysco.cursedcompass.datagen.server.CursedLootModifierProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class CursedDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(true, new CursedLanguageProvider(packOutput));
		generator.addProvider(true, new CursedModelProvider(packOutput));

		generator.addProvider(true, new CursedEntityTypeTagsProvider(packOutput, lookupProvider));
		generator.addProvider(true, new CursedLootModifierProvider(packOutput, lookupProvider));

	}
}
