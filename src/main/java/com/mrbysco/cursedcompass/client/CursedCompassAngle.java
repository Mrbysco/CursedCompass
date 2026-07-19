package com.mrbysco.cursedcompass.client;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class CursedCompassAngle implements RangeSelectItemModelProperty {
	public static final MapCodec<CursedCompassAngle> MAP_CODEC = CursedCompassAngleState.MAP_CODEC.xmap(CursedCompassAngle::new,
			angle -> angle.state);
	private final CursedCompassAngleState state;

	public CursedCompassAngle(boolean wobble) {
		this(new CursedCompassAngleState(wobble));
	}

	private CursedCompassAngle(CursedCompassAngleState state) {
		this.state = state;
	}

	public float get(ItemStack itemStack, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
		return this.state.get(itemStack, level, owner, seed);
	}

	@Override
	public MapCodec<? extends RangeSelectItemModelProperty> type() {
		return MAP_CODEC;
	}
}
