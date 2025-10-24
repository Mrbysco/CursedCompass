package com.mrbysco.cursedcompass.datagen.client;

import com.mrbysco.cursedcompass.CursedCompass;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class CursedItemModelProvider extends ItemModelProvider {
	public CursedItemModelProvider(PackOutput output, ExistingFileHelper helper) {
		super(output, CursedCompass.MOD_ID, helper);
	}

	@Override
	protected void registerModels() {

		for (int i = 0; i < 32; i++) {
			StringBuilder s = new StringBuilder("cursed_compass_");
			if (i < 10) s.append(0);
			String name = s.append(i).toString();
			getBuilder(name)
					.parent(getExistingFile(mcLoc("item/generated")))
					.texture("layer0", ResourceLocation.withDefaultNamespace("item/compass_" + (i < 10 ? "0" : "") + i));
		}
	}
}
