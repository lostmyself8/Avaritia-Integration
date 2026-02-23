package committee.nova.mods.avaritia_integration.module.create.registry;

import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.create.compat.cca.liquid_burning.LiquidBurningRecipeSerializer;
import committee.nova.mods.avaritia_integration.module.create.content.mixer.ExtremeMixingRecipe;
import committee.nova.mods.avaritia_integration.module.create.content.recipe.ExtremeBasinRecipe;
import committee.nova.mods.avaritia_integration.module.create.content.recipe.ExtremeProcessingRecipeBuilder;
import committee.nova.mods.avaritia_integration.module.create.content.recipe.ExtremeProcessingRecipeSerializer;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public enum CreateIntegrationRecipeTypes implements IRecipeTypeInfo {
    EXTREME_BASIN(ExtremeBasinRecipe::new),
    EXTREME_MIXING(ExtremeMixingRecipe::new),
    LIQUID_BURNING(LiquidBurningRecipeSerializer::new);

    private final ResourceLocation id;
    private final RegistryObject<RecipeSerializer<?>> serializerObject;
    @Nullable
    private final RegistryObject<RecipeType<?>> typeObject;
    private final Supplier<RecipeType<?>> type;

    CreateIntegrationRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier, Supplier<RecipeType<?>> typeSupplier, boolean registerType) {
        String name = Lang.asId(name());
        id = AvaritiaIntegration.rl(name);
        serializerObject = Registers.SERIALIZER_REGISTER.register(name, serializerSupplier);
        if (registerType) {
            typeObject = Registers.TYPE_REGISTER.register(name, typeSupplier);
            type = typeObject;
        } else {
            typeObject = null;
            type = typeSupplier;
        }
    }

    CreateIntegrationRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier) {
        String name = Lang.asId(name());
        id = AvaritiaIntegration.rl(name);
        serializerObject = Registers.SERIALIZER_REGISTER.register(name, serializerSupplier);
        typeObject = Registers.TYPE_REGISTER.register(name, () -> RecipeType.simple(id));
        type = typeObject;
    }

    CreateIntegrationRecipeTypes(ExtremeProcessingRecipeBuilder.ProcessingRecipeFactory<?> processingFactory) {
        this(() -> new ExtremeProcessingRecipeSerializer<>(processingFactory));
    }

    public static void register(IEventBus modEventBus) {
        Registers.SERIALIZER_REGISTER.register(modEventBus);
        Registers.TYPE_REGISTER.register(modEventBus);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeSerializer<?>> T getSerializer() {
        return (T) serializerObject.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeType<?>> T getType() {
        return (T) type.get();
    }

    public <C extends Container, T extends Recipe<C>> Optional<T> find(C inv, Level world) {
        return world.getRecipeManager()
                .getRecipeFor(getType(), inv, world);
    }

    private static class Registers {
        private static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, AvaritiaIntegration.MOD_ID);
        private static final DeferredRegister<RecipeType<?>> TYPE_REGISTER = DeferredRegister.create(Registries.RECIPE_TYPE, AvaritiaIntegration.MOD_ID);
    }
}
