package com.lovetropics.packwrapper;

import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.locating.IModFile;

@Mod("packwrapper")
public class PackWrapper {
    public PackWrapper() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> "1", (s, v) -> true));

        final IModFile modFile = ModLoadingContext.get().getActiveContainer().getModInfo().getOwningFile().getFile();

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener((AddPackFindersEvent event) -> event.addRepositorySource(new EmbeddedPackSource(modFile, event.getPackType())));
    }
}
