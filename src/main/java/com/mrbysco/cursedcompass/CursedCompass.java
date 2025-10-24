package com.mrbysco.cursedcompass;

import com.mojang.logging.LogUtils;
import com.mrbysco.cursedcompass.client.ClientHandler;
import com.mrbysco.cursedcompass.registry.CursedRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(CursedCompass.MOD_ID)
public class CursedCompass {
	public static final String MOD_ID = "cursedcompass";
	public static final Logger LOGGER = LogUtils.getLogger();

	public static final TagKey<EntityType<?>> TARGETS = TagKey.create(Registries.ENTITY_TYPE, modLoc("targets"));

	public CursedCompass(IEventBus eventBus, Dist dist, ModContainer container) {
		CursedRegistry.GLM.register(eventBus);
		CursedRegistry.DATA_COMPONENT_TYPES.register(eventBus);
		CursedRegistry.ITEMS.register(eventBus);
		CursedRegistry.CREATIVE_MODE_TABS.register(eventBus);

		if (dist.isClient()) {
			eventBus.addListener(ClientHandler::onClientSetup);
		}
	}

	public static ResourceLocation modLoc(String name) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
	}

}
