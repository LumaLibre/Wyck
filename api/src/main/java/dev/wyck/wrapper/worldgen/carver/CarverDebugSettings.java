package dev.wyck.wrapper.worldgen.carver;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.util.BukkitBootstrapUtil;
import dev.wyck.wrapper.internal.Wrapper;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("2.3.0")
public interface CarverDebugSettings extends Wrapper {

    @ApiStatus.Internal
    ConstructWireProvider<CarverDebugSettings> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.carver.CarverDebugSettingsImpl");

    CarverDebugSettings DEFAULT = builder().build();

    @AsOf("2.3.0")
    boolean debugMode();

    @AsOf("2.3.0")
    BlockData airState();

    @AsOf("2.3.0")
    BlockData waterState();

    @AsOf("2.3.0")
    BlockData lavaState();

    @AsOf("2.3.0")
    BlockData barrierState();

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static CarverDebugSettings of(boolean debugMode, BlockData airState, BlockData waterState, BlockData lavaState, BlockData barrierState) {
        return WIRE.construct(debugMode, airState, waterState, lavaState, barrierState);
    }

    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    @AsOf("3.0.0")
    final class Builder {
        private boolean debugMode = false;
        private BlockData airState = BukkitBootstrapUtil.util().createBlockData(Material.ACACIA_BUTTON);
        private BlockData waterState = BukkitBootstrapUtil.util().createBlockData(Material.CANDLE);
        private BlockData lavaState = BukkitBootstrapUtil.util().createBlockData(Material.ORANGE_STAINED_GLASS);
        private BlockData barrierState = BukkitBootstrapUtil.util().createBlockData(Material.GLASS);

        public Builder() {}

        public Builder(CarverDebugSettings settings) {
            this.debugMode = settings.debugMode();
            this.airState = settings.airState();
            this.waterState = settings.waterState();
            this.lavaState = settings.lavaState();
            this.barrierState = settings.barrierState();
        }

        @AsOf("3.0.0")
        public Builder debugMode(boolean debugMode) {
            this.debugMode = debugMode;
            return this;
        }

        @AsOf("3.0.0")
        public Builder airState(BlockData airState) {
            this.airState = airState;
            return this;
        }

        @AsOf("3.0.0")
        public Builder waterState(BlockData waterState) {
            this.waterState = waterState;
            return this;
        }

        @AsOf("3.0.0")
        public Builder lavaState(BlockData lavaState) {
            this.lavaState = lavaState;
            return this;
        }

        @AsOf("3.0.0")
        public Builder barrierState(BlockData barrierState) {
            this.barrierState = barrierState;
            return this;
        }

        // Friendly builder methods

        @AsOf("3.0.0")
        public Builder debugMode() {
            this.debugMode = true;
            return this;
        }

        @AsOf("3.0.0")
        public Builder airState(Material airState) {
            this.airState = BukkitBootstrapUtil.util().createBlockData(airState);
            return this;
        }

        @AsOf("3.0.0")
        public Builder waterState(Material waterState) {
            this.waterState = BukkitBootstrapUtil.util().createBlockData(waterState);
            return this;
        }

        @AsOf("3.0.0")
        public Builder lavaState(Material lavaState) {
            this.lavaState = BukkitBootstrapUtil.util().createBlockData(lavaState);
            return this;
        }

        @AsOf("3.0.0")
        public Builder barrierState(Material barrierState) {
            this.barrierState = BukkitBootstrapUtil.util().createBlockData(barrierState);
            return this;
        }

        @AsOf("3.0.0")
        public CarverDebugSettings build() {
            return of(debugMode, airState, waterState, lavaState, barrierState);
        }
    }
}
