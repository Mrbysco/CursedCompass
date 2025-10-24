package com.mrbysco.cursedcompass.client.property;

import com.mrbysco.cursedcompass.registry.CursedRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class CursedCompassAngleProperty implements ItemPropertyFunction {
	private double rotation;
	private double rota;
	private long lastUpdateTick;

	public float call(ItemStack stack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingBaseIn, int seed) {
		if (livingBaseIn == null && !stack.isFramed()) {
			return 0.0F;
		} else {
			boolean livingExists = livingBaseIn != null;
			Entity entity = livingExists ? livingBaseIn : stack.getFrame();
			if (clientLevel == null && entity.level() instanceof ClientLevel) {
				clientLevel = (ClientLevel) entity.level();
			}

			double d0;
			BlockPos targetPos = getTargetPos(stack);
			if (targetPos != null) {
				double d1 = livingExists ? (double) entity.getYRot() : this.getFrameRotation((ItemFrame) entity);
				d1 = Mth.positiveModulo(d1 / 360.0D, 1.0D);
				double d2 = this.getSpawnToAngle(entity, targetPos) / (double) ((float) Math.PI * 2F);
				d0 = 0.5D - (d1 - 0.25D - d2);
			} else {
				// TODO: Disable the wobble if no target?
				d0 = Math.random();
			}

			if (livingExists) {
				d0 = this.wobble(clientLevel, d0);
			}

			return Mth.positiveModulo((float) d0, 1.0F);
		}
	}


	private double wobble(ClientLevel clientLevel, double p_185093_2_) {
		if (clientLevel.getGameTime() != this.lastUpdateTick) {
			this.lastUpdateTick = clientLevel.getGameTime();
			double d0 = p_185093_2_ - this.rotation;
			d0 = Mth.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
			this.rota += d0 * 0.1D;
			this.rota *= 0.8D;
			this.rotation = Mth.positiveModulo(this.rotation + this.rota, 1.0D);
		}

		return this.rotation;
	}


	private double getFrameRotation(ItemFrame itemFrame) {
		Direction direction = itemFrame.getDirection();
		int i = direction.getAxis().isVertical() ? 90 * direction.getAxisDirection().getStep() : 0;
		return Mth.wrapDegrees(180 + direction.get2DDataValue() * 90 + itemFrame.getRotation() * 45 + i);
	}


	private double getSpawnToAngle(Entity entityIn, @NotNull BlockPos pos) {
		return Math.atan2((double) pos.getZ() - entityIn.getZ(), (double) pos.getX() - entityIn.getX());
	}

	@Nullable
	public BlockPos getTargetPos(ItemStack stack) {
		if (stack.has(CursedRegistry.TARGET_POS)) {
			return stack.get(CursedRegistry.TARGET_POS);
		}
		return null;
	}
}
