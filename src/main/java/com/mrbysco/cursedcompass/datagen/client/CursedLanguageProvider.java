package com.mrbysco.cursedcompass.datagen.client;

import com.mrbysco.cursedcompass.CursedCompass;
import com.mrbysco.cursedcompass.registry.CursedRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class CursedLanguageProvider extends LanguageProvider {

	public CursedLanguageProvider(PackOutput packOutput) {
		super(packOutput, CursedCompass.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		add("itemGroup.cursedcompass", "Cursed Compass");

		addItem(CursedRegistry.CURSED_COMPASS, "Compass");
	}
}
