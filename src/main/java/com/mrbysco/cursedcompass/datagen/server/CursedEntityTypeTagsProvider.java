package com.mrbysco.cursedcompass.datagen.server;

import com.mrbysco.cursedcompass.CursedCompass;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class CursedEntityTypeTagsProvider extends EntityTypeTagsProvider {
	public CursedEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper) {
		super(output, provider, CursedCompass.MOD_ID, helper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(CursedCompass.TARGETS)
				.addTag(EntityTypeTags.ZOMBIES)
				.addTag(EntityTypeTags.SKELETONS)
				.addTag(EntityTypeTags.UNDEAD)
				.addTag(EntityTypeTags.RAIDERS)
				.addTag(EntityTypeTags.ILLAGER)
				.addTag(Tags.EntityTypes.BOSSES)
		;
	}
}
