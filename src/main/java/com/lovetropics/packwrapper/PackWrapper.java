package com.lovetropics.packwrapper;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforgespi.locating.IModFile;

@Mod("packwrapper")
public class PackWrapper {
    public PackWrapper(final IEventBus modEventBus) {
        final IModFile modFile = ModLoadingContext.get().getActiveContainer().getModInfo().getOwningFile().getFile();

        modEventBus.addListener((AddPackFindersEvent event) -> event.addRepositorySource(new EmbeddedPackSource(modFile, event.getPackType())));
    }
}
