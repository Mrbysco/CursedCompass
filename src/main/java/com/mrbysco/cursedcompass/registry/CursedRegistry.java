package com.mrbysco.cursedcompass.registry;

import com.mojang.serialization.MapCodec;
import com.mrbysco.cursedcompass.CursedCompass;
import com.mrbysco.cursedcompass.item.CursedCompassItem;
import com.mrbysco.cursedcompass.loot.CursedLootModifier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.UUID;
import java.util.function.Supplier;

public class CursedRegistry {
	public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, CursedCompass.MOD_ID);
	public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, CursedCompass.MOD_ID);
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CursedCompass.MOD_ID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CursedCompass.MOD_ID);

	public static final Supplier<DataComponentType<UUID>> TARGET_ID = DATA_COMPONENT_TYPES.registerComponentType("target_id", builder ->
			builder
					.persistent(UUIDUtil.STRING_CODEC)
					.networkSynchronized(UUIDUtil.STREAM_CODEC)
	);

	public static final Supplier<DataComponentType<BlockPos>> TARGET_POS = DATA_COMPONENT_TYPES.registerComponentType("target_pos", builder ->
			builder.persistent(BlockPos.CODEC)
					.networkSynchronized(BlockPos.STREAM_CODEC)
	);

	public static final DeferredItem<CursedCompassItem> CURSED_COMPASS = ITEMS.registerItem("cursed_compass", (properties) ->
			new CursedCompassItem(properties.stacksTo(1)));

	public static final Supplier<CreativeModeTab> COMPASS_TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
			.icon(() -> CursedRegistry.CURSED_COMPASS.get().getDefaultInstance())
			.title(Component.translatable("itemGroup.cursedcompass"))
			.displayItems((parameters, output) -> {
				output.accept(CursedRegistry.CURSED_COMPASS.get());
			}).build());

	public static final Supplier<MapCodec<? extends IGlobalLootModifier>> CURSED_LOOT = GLM.register("cursed_loot", CursedLootModifier.CODEC);
}
