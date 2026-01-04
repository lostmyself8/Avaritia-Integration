package committee.nova.mods.avaritia_integration.module.mekanism.api.recipes;

import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@NothingNullByDefault
public abstract class MekCompressorRecipe extends ItemStackToItemStackRecipe {

    private final int inputCount;
    private final int timeRequire;

    /**
     * @param id Recipe name.
     */
    protected MekCompressorRecipe(ResourceLocation id, ItemStackIngredient input, ItemStack output, int inputCount, int timeRequire) {
        super(id, input, output);
        this.inputCount = inputCount;
        this.timeRequire = timeRequire;
    }

    public int getInputCount() {
        return inputCount;
    }

    public int getTimeRequire() {
        return timeRequire;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        super.write(buffer);
        buffer.writeVarInt(inputCount);
        buffer.writeVarInt(timeRequire);
    }
}
