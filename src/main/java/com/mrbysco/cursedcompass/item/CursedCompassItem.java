package com.mrbysco.cursedcompass.item;

import com.mrbysco.cursedcompass.CursedCompass;
import com.mrbysco.cursedcompass.registry.CursedRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CursedCompassItem extends Item {
	public CursedCompassItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return false;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
		if (level instanceof ServerLevel serverLevel) {
			if (!stack.has(CursedRegistry.TARGET_ID)) {
				// Generate random target ID
				Iterable<Entity> entityIterable = serverLevel.getEntities().getAll();
				List<Entity> entityList = new ArrayList<>();
				entityIterable.forEach(entityList::add);
				if (entityList.isEmpty()) {
					return;
				}
				entityList = new ArrayList<>(entityList.stream()
						.filter(foundEntity ->
								foundEntity instanceof LivingEntity &&
										foundEntity.getType().is(CursedCompass.TARGETS) &&
										foundEntity.distanceTo(entity) < 100.0F
						).toList());
				if (!entityList.isEmpty()) {
					Collections.shuffle(entityList);
					Entity targetEntity = entityList.getFirst();
					stack.set(CursedRegistry.TARGET_ID, targetEntity.getUUID());
					stack.set(CursedRegistry.TARGET_POS, targetEntity.blockPosition());
				}
			} else {
				UUID targetID = stack.get(CursedRegistry.TARGET_ID);
				assert targetID != null;
				Entity targetEntity = serverLevel.getEntity(targetID);
				if (targetEntity == null || !targetEntity.isAlive()) {
					// Clear target if entity not found
					stack.remove(CursedRegistry.TARGET_ID);
					stack.remove(CursedRegistry.TARGET_POS);
				} else {
					if (level.dimension().equals(targetEntity.level().dimension())) {
						// Update target position
						stack.set(CursedRegistry.TARGET_POS, targetEntity.blockPosition());
					} else {
						// Clear target if dimension does not match
						stack.remove(CursedRegistry.TARGET_ID);
						stack.remove(CursedRegistry.TARGET_POS);
					}
				}
			}
		}
	}
}
