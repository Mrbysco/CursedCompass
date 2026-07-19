package com.mrbysco.cursedcompass.datagen.client;

import com.mrbysco.cursedcompass.CursedCompass;
import com.mrbysco.cursedcompass.client.CursedCompassAngle;
import com.mrbysco.cursedcompass.registry.CursedRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;

import java.util.List;

public class CursedModelProvider extends ModelProvider {
	public CursedModelProvider(PackOutput output) {
		super(output, CursedCompass.MOD_ID);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		generateCompass(itemModels, CursedRegistry.CURSED_COMPASS.get());
	}

	public void generateCompass(ItemModelGenerators itemModels, Item compass) {
		List<RangeSelectItemModel.Entry> overrides = itemModels.createCompassModels(compass);
		itemModels.itemModelOutput.accept(compass,
				ItemModelUtils.rangeSelect(new CursedCompassAngle(false), 32.0F, overrides)
		);
	}
}
