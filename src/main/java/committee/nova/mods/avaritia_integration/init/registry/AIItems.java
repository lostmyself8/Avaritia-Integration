package committee.nova.mods.avaritia_integration.init.registry;

import committee.nova.mods.avaritia.api.common.item.BaseItem;
import committee.nova.mods.avaritia.init.registry.ModRarities;
import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.gregtech.registry.AIMaterials;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class AIItems {

    public static final DeferredRegister<Item> REGISTRY =
            DeferredRegister.create(ForgeRegistries.ITEMS, AvaritiaIntegration.MOD_ID);

    public static final List<Supplier<? extends Item>> ITEMS = new LinkedList<>();

    // ===== blaze_cube =====
    public static Supplier<Item> BLAZE_CUBE_DENSE_PLATE;
    public static Supplier<Item> BLAZE_CUBE_DUST;
    public static Supplier<Item> BLAZE_CUBE_GEAR;
    public static Supplier<Item> BLAZE_CUBE_NUGGET;
    public static Supplier<Item> BLAZE_CUBE_PLATE;
    public static Supplier<Item> BLAZE_CUBE_ROD;
    public static Supplier<Item> BLAZE_CUBE_WIRE;
    public static Supplier<Item> MOLTEN_BLAZE_BUCKET;

    // ===== crystal_matrix =====

    public static Supplier<Item> CRYSTAL_MATRIX_DENSE_PLATE;
    public static Supplier<Item> CRYSTAL_MATRIX_DUST;
    public static Supplier<Item> CRYSTAL_MATRIX_GEAR;
    public static Supplier<Item> CRYSTAL_MATRIX_NUGGET;
    public static Supplier<Item> CRYSTAL_MATRIX_PLATE;
    public static Supplier<Item> CRYSTAL_MATRIX_ROD;
    public static Supplier<Item> CRYSTAL_MATRIX_WIRE;
    public static Supplier<Item> MOLTEN_CRYSTAL_MATRIX_BUCKET;

    // ===== infinity =====
    public static Supplier<Item> INFINITY_DENSE_PLATE;
    public static Supplier<Item> INFINITY_DUST;
    public static Supplier<Item> INFINITY_GEAR;
    public static Supplier<Item> INFINITY_PLATE;
    public static Supplier<Item> INFINITY_ROD;
    public static Supplier<Item> INFINITY_WIRE;

    // ===== neutron =====
    public static Supplier<Item> NEUTRON_DENSE_PLATE;
    public static Supplier<Item> NEUTRON_DUST;
    public static Supplier<Item> NEUTRON_PLATE;
    public static Supplier<Item> NEUTRON_ROD;
    public static Supplier<Item> NEUTRON_WIRE;
    public static Supplier<Item> MOLTEN_NEUTRON_BUCKET;
    public static Supplier<Item> MOLTEN_STAR_BUCKET;

    // ===== init =====
    public static void init(IEventBus bus) {
        REGISTRY.register(bus);

        MOLTEN_BLAZE_BUCKET = register("molten_blaze_bucket",
                () -> new BucketItem(AIFluids.source_molten_blaze,
                        new Item.Properties().stacksTo(1).rarity(ModRarities.UNCOMMON)));

        MOLTEN_CRYSTAL_MATRIX_BUCKET = register("molten_crystal_matrix_bucket",
                () -> new BucketItem(AIFluids.source_molten_crystal_matrix,
                        new Item.Properties().stacksTo(1).rarity(ModRarities.RARE)));

        MOLTEN_NEUTRON_BUCKET = register("molten_neutron_bucket",
                () -> new BucketItem(AIFluids.source_molten_neutron,
                        new Item.Properties().stacksTo(1).rarity(ModRarities.RARE)));

        MOLTEN_STAR_BUCKET = register("molten_star_bucket",
                () -> new BucketItem(AIFluids.source_molten_star,
                        new Item.Properties().stacksTo(1).rarity(ModRarities.RARE)));

        if(ModList.get().isLoaded("gtceu")){
            AIMaterials.replaceItem();
            return;
        }

        // blaze_cube
        BLAZE_CUBE_DENSE_PLATE = register("blaze_cube_dense_plate", () -> new BaseItem(p -> p.rarity(ModRarities.UNCOMMON)));
        BLAZE_CUBE_DUST = register("blaze_cube_dust", () -> new BaseItem(p -> p.rarity(ModRarities.UNCOMMON)));
        BLAZE_CUBE_GEAR = register("blaze_cube_gear", () -> new BaseItem(p -> p.rarity(ModRarities.UNCOMMON)));
        BLAZE_CUBE_NUGGET = register("blaze_cube_nugget", () -> new BaseItem(p -> p.rarity(ModRarities.UNCOMMON)));
        BLAZE_CUBE_PLATE = register("blaze_cube_plate", () -> new BaseItem(p -> p.rarity(ModRarities.UNCOMMON)));
        BLAZE_CUBE_ROD = register("blaze_cube_rod", () -> new BaseItem(p -> p.rarity(ModRarities.UNCOMMON)));
        BLAZE_CUBE_WIRE = register("blaze_cube_wire", () -> new BaseItem(p -> p.rarity(ModRarities.UNCOMMON)));


        // crystal_matrix
        CRYSTAL_MATRIX_DENSE_PLATE = register("crystal_matrix_dense_plate", () -> new BaseItem(p -> p.rarity(ModRarities.RARE)));
        CRYSTAL_MATRIX_DUST = register("crystal_matrix_dust", () -> new BaseItem(p -> p.rarity(ModRarities.RARE)));
        CRYSTAL_MATRIX_GEAR = register("crystal_matrix_gear", () -> new BaseItem(p -> p.rarity(ModRarities.RARE)));
        CRYSTAL_MATRIX_NUGGET = register("crystal_matrix_nugget", () -> new BaseItem(p -> p.rarity(ModRarities.RARE)));
        CRYSTAL_MATRIX_PLATE = register("crystal_matrix_plate", () -> new BaseItem(p -> p.rarity(ModRarities.RARE)));
        CRYSTAL_MATRIX_ROD = register("crystal_matrix_rod", () -> new BaseItem(p -> p.rarity(ModRarities.RARE)));
        CRYSTAL_MATRIX_WIRE = register("crystal_matrix_wire", () -> new BaseItem(p -> p.rarity(ModRarities.RARE)));


        // infinity
        INFINITY_DENSE_PLATE = register("infinity_dense_plate", () -> new BaseItem(p -> p.rarity(ModRarities.EPIC)));
        INFINITY_DUST = register("infinity_dust", () -> new BaseItem(p -> p.rarity(ModRarities.EPIC)));
        INFINITY_GEAR = register("infinity_gear", () -> new BaseItem(p -> p.rarity(ModRarities.EPIC)));
        INFINITY_PLATE = register("infinity_plate", () -> new BaseItem(p -> p.rarity(ModRarities.EPIC)));
        INFINITY_ROD = register("infinity_rod", () -> new BaseItem(p -> p.rarity(ModRarities.EPIC)));
        INFINITY_WIRE = register("infinity_wire", () -> new BaseItem(p -> p.rarity(ModRarities.EPIC)));

        // neutron
        NEUTRON_DENSE_PLATE = register("neutron_dense_plate", () -> new BaseItem(p -> p.rarity(ModRarities.RARE)));
        NEUTRON_DUST = register("neutron_dust", () -> new BaseItem(p -> p.rarity(ModRarities.RARE)));
        NEUTRON_PLATE = register("neutron_plate", () -> new BaseItem(p -> p.rarity(ModRarities.RARE)));
        NEUTRON_ROD = register("neutron_rod", () -> new BaseItem(p -> p.rarity(ModRarities.RARE)));
        NEUTRON_WIRE = register("neutron_wire", () -> new BaseItem(p -> p.rarity(ModRarities.RARE)));

    }

    // ===== 注册工具 =====
    public static <T extends Item> RegistryObject<T> register(String id, Supplier<T> obj) {
        RegistryObject<T> r = REGISTRY.register(id, obj);
        ITEMS.add(r);
        return r;
    }
}