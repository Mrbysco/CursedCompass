package com.mrbysco.cursedcompass.datagen;

import com.mrbysco.cursedcompass.datagen.client.CursedItemModelProvider;
import com.mrbysco.cursedcompass.datagen.client.CursedLanguageProvider;
import com.mrbysco.cursedcompass.datagen.server.CursedEntityTypeTagsProvider;
import com.mrbysco.cursedcompass.datagen.server.CursedLootModifierProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CursedDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeClient()) {
			generator.addProvider(true, new CursedLanguageProvider(packOutput));
			generator.addProvider(true, new CursedItemModelProvider(packOutput, helper));
		}

		if (event.includeServer()) {
			generator.addProvider(true, new CursedEntityTypeTagsProvider(packOutput, lookupProvider, helper));
			generator.addProvider(true, new CursedLootModifierProvider(packOutput, lookupProvider));
		}
	}
}
