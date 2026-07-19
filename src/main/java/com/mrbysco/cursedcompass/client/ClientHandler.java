package com.mrbysco.cursedcompass.client;

import com.mrbysco.cursedcompass.CursedCompass;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent;

@EventBusSubscriber(Dist.CLIENT)
public class ClientHandler {

	@SubscribeEvent
	public static void registerRangeSelectProperties(final RegisterRangeSelectItemModelPropertyEvent event) {
		event.register(CursedCompass.modLoc("compass_angle"), CursedCompassAngle.MAP_CODEC);
	}
}
