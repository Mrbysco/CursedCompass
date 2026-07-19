package com.mrbysco.cursedcompass.client;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.cursedcompass.registry.CursedRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.NeedleDirectionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class CursedCompassAngleState extends NeedleDirectionHelper {
	public static final MapCodec<CursedCompassAngleState> MAP_CODEC = RecordCodecBuilder.mapCodec(
			wobble -> wobble.group(
							Codec.BOOL.optionalFieldOf("wobble", Boolean.FALSE).forGetter(CursedCompassAngleState::wobble)
					)
					.apply(wobble, CursedCompassAngleState::new)
	);
	private final NeedleDirectionHelper.Wobbler wobbler = this.newWobbler(0.8F);
	private final NeedleDirectionHelper.Wobbler noTargetWobbler = this.newWobbler(0.8F);
	private final RandomSource random = RandomSource.create();

	public CursedCompassAngleState(boolean wobble) {
		super(wobble);
	}

	@Override
	protected float calculate(ItemStack stack, ClientLevel clientLevel, int seed, ItemOwner itemOwner) {
		GlobalPos globalpos = getTargetPos(stack);
		long i = clientLevel.getGameTime();
		boolean valid = isValidCompassTargetPos(itemOwner, globalpos);
		return !valid
				? this.getRandomlySpinningRotation(seed, i)
				: this.getRotationTowardsCompassTarget(itemOwner, i, globalpos.pos());
	}

	private float getRandomlySpinningRotation(int seed, long gameTime) {
		if (this.noTargetWobbler.shouldUpdate(gameTime)) {
			this.noTargetWobbler.update(gameTime, this.random.nextFloat());
		}

		float targetRotation = this.noTargetWobbler.rotation() + (float) hash(seed) / (float) Integer.MAX_VALUE;
		return Mth.positiveModulo(targetRotation, 1.0F);
	}

	private float getRotationTowardsCompassTarget(ItemOwner owner, long gameTime, BlockPos compassTargetPos) {
		float angleToTarget = (float) getAngleFromEntityToPos(owner, compassTargetPos);
		float ownerYRotation = getWrappedVisualRotationY(owner);
		LivingEntity livingEntity = owner.asLivingEntity();
		float targetRotation;
		if (livingEntity instanceof Player player) {
			if (player.isLocalPlayer() && player.level().tickRateManager().runsNormally()) {
				if (this.wobbler.shouldUpdate(gameTime)) {
					this.wobbler.update(gameTime, 0.5F - (ownerYRotation - 0.25F));
				}

				targetRotation = angleToTarget + this.wobbler.rotation();
				return Mth.positiveModulo(targetRotation, 1.0F);
			}
		}

		targetRotation = 0.5F - (ownerYRotation - 0.25F - angleToTarget);
		return Mth.positiveModulo(targetRotation, 1.0F);
	}

	private static boolean isValidCompassTargetPos(ItemOwner owner, @Nullable GlobalPos positionToPointTo) {
		return positionToPointTo != null &&
				positionToPointTo.dimension() == owner.level().dimension() &&
				!(positionToPointTo.pos().distToCenterSqr(owner.position()) < (double) 1.0E-5F);
	}

	@Nullable
	public GlobalPos getTargetPos(ItemStack stack) {
		if (stack.has(CursedRegistry.TARGET_POS)) {
			return stack.get(CursedRegistry.TARGET_POS);
		}
		return null;
	}

	private static double getAngleFromEntityToPos(ItemOwner owner, BlockPos position) {
		Vec3 target = Vec3.atCenterOf(position);
		Vec3 ownerPosition = owner.position();
		return Math.atan2(target.z() - ownerPosition.z(), target.x() - ownerPosition.x()) / (double) ((float) Math.PI * 2F);
	}

	private static float getWrappedVisualRotationY(ItemOwner owner) {
		return Mth.positiveModulo(owner.getVisualRotationYInDegrees() / 360.0F, 1.0F);
	}

	private static int hash(int input) {
		return input * 1327217883;
	}
}
