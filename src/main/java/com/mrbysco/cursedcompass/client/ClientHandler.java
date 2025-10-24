package com.mrbysco.cursedcompass.client;

import com.mrbysco.cursedcompass.client.property.CursedCompassAngleProperty;
import com.mrbysco.cursedcompass.registry.CursedRegistry;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientHandler {
	public static void onClientSetup(final FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ItemProperties.register(CursedRegistry.CURSED_COMPASS.get(), ResourceLocation.withDefaultNamespace("angle"),
					new CursedCompassAngleProperty());
		});
	}
}
