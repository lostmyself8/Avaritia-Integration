package committee.nova.mods.avaritia_integration.module.mekanism.common.config;

import mekanism.api.math.FloatingLong;
import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.value.CachedFloatingLongValue;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class UsageConfig extends BaseMekanismConfig {

    private final ForgeConfigSpec configSpec;

    public final CachedFloatingLongValue neutronCollector;
    public final CachedFloatingLongValue singularityCompressor;

    UsageConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Machine Energy Usage Config. This config is synced from server to client.").push("usage");

        neutronCollector = CachedFloatingLongValue.define(this, builder, "Energy per operation tick (Joules).", "neutronCollector",
                FloatingLong.createConst(50));
        singularityCompressor = CachedFloatingLongValue.define(this, builder, "Energy per operation tick (Joules).", "singularityCompressor",
                FloatingLong.createConst(100));

        builder.pop();
        configSpec = builder.build();
    }

    @Override
    public String getFileName() {
        return "machine-usage";
    }

    @Override
    public ForgeConfigSpec getConfigSpec() {
        return configSpec;
    }

    @Override
    public ModConfig.Type getConfigType() {
        return ModConfig.Type.SERVER;
    }

    @Override
    public boolean addToContainer() {
        return false;
    }
}
