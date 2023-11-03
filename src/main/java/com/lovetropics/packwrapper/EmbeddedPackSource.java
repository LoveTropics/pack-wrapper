package com.lovetropics.packwrapper;

import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraftforge.forgespi.locating.IModFile;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class EmbeddedPackSource implements RepositorySource {
    private static final Logger LOGGER = LogUtils.getLogger();

    private final IModFile modFile;
    private final PackType type;

    public EmbeddedPackSource(final IModFile modFile, final PackType type) {
        this.modFile = modFile;
        this.type = type;
    }

    @Override
    public void loadPacks(final Consumer<Pack> consumer) {
        final Path packRoot = modFile.getFilePath().resolve(type == PackType.CLIENT_RESOURCES ? "resourcepacks" : "datapacks");
        try (final Stream<Path> stream = Files.list(packRoot)) {
            final List<Path> paths = stream.toList();
            for (final Path path : paths) {
                final String packId = path.getFileName().toString();
                final Component packName = Component.literal(packId);
                final Pack.ResourcesSupplier resourcesSupplier = id -> new PathPackResources(id, path, false);
                final Pack pack = Pack.readMetaAndCreate(packId, packName, false, resourcesSupplier, type, Pack.Position.TOP, PackSource.DEFAULT);
                if (pack != null) {
                    consumer.accept(pack);
                }
            }
        } catch (final IOException e) {
            LOGGER.error("Could not list embedded mod packs");
        }
    }
}
