package dev.wyck.worldgen.structure.types;

import com.google.common.base.Preconditions;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.structure.StructureSettings;
import dev.wyck.worldgen.structure.StructureType;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.structures.BuriedTreasureStructure;
import net.minecraft.world.level.levelgen.structure.structures.DesertPyramidStructure;
import net.minecraft.world.level.levelgen.structure.structures.EndCityStructure;
import net.minecraft.world.level.levelgen.structure.structures.IglooStructure;
import net.minecraft.world.level.levelgen.structure.structures.JungleTempleStructure;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressStructure;
import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentStructure;
import net.minecraft.world.level.levelgen.structure.structures.StrongholdStructure;
import net.minecraft.world.level.levelgen.structure.structures.SwampHutStructure;
import net.minecraft.world.level.levelgen.structure.structures.WoodlandMansionStructure;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@NullMarked
@ApiStatus.Internal
public record SimpleStructureImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override StructureType type,
    @Override StructureSettings settings
) implements SimpleStructure {

    private static final Map<StructureType, Function<Structure.StructureSettings, Structure>> CONSTRUCTORS = Map.of(
        StructureType.BURIED_TREASURE, BuriedTreasureStructure::new,
        StructureType.DESERT_PYRAMID, DesertPyramidStructure::new,
        StructureType.END_CITY, EndCityStructure::new,
        StructureType.FORTRESS, NetherFortressStructure::new,
        StructureType.IGLOO, IglooStructure::new,
        StructureType.JUNGLE_TEMPLE, JungleTempleStructure::new,
        StructureType.OCEAN_MONUMENT, OceanMonumentStructure::new,
        StructureType.STRONGHOLD, StrongholdStructure::new,
        StructureType.SWAMP_HUT, SwampHutStructure::new,
        StructureType.WOODLAND_MANSION, WoodlandMansionStructure::new
    );

    @Override
    public Object toMinecraft() {
        Function<Structure.StructureSettings, Structure> constructor = CONSTRUCTORS.get(this.type);
        Preconditions.checkNotNull(constructor, this.type + " has a configuration");
        return Holder.direct(constructor.apply(this.settings.asHandle()));
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow();
    }

    @Override
    public SimpleStructure register() {
        StructureRegistration.register(this.resourceKey, this);
        return this;
    }
}
