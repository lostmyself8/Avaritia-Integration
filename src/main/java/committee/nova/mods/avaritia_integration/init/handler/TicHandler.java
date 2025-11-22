package committee.nova.mods.avaritia_integration.init.handler;

import committee.nova.mods.avaritia.api.util.PlayerUtils;
import committee.nova.mods.avaritia.init.handler.AbilityHandler;
import committee.nova.mods.avaritia.init.registry.ModBlocks;
import committee.nova.mods.avaritia.init.registry.ModDamageTypes;
import committee.nova.mods.avaritia_integration.module.tconstruct.AvaritiaModifierIds;
import committee.nova.mods.avaritia_integration.module.tconstruct.TConstructModule;
import committee.nova.mods.avaritia_integration.module.tconstruct.registry.TicIntegrationModifiers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static committee.nova.mods.avaritia.init.handler.AbilityHandler.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TicHandler {
    private static final UUID ATTRIBUTE_BONUS = UUID.fromString("2509DE5E-6CE8-4530-640E-514C1F170001");
    public static final Set<String> entitiesWithHelmets = new HashSet<>();
    public static final Set<String> entitiesWithBoots = new HashSet<>();
    public static final Map<String, FlightInfo> entitiesWithFlight = new ConcurrentHashMap<>();
    private static boolean isLoaded(){
       return ModList.get().isLoaded(TConstructModule.MOD_ID);
    }
    private static boolean isInfinity(ItemStack item){
        return isLoaded() && item.is(TinkerTags.Items.HARVEST_PRIMARY) && (getModifierLevel(item ,AvaritiaModifierIds.RuleOver) > 0 || getModifierLevel(item ,TicIntegrationModifiers.Crystalshine.getId()) > 0);
    }
    @SubscribeEvent
    public static void onPlayerMine(PlayerInteractEvent.LeftClickBlock event) {
        var item = event.getItemStack();
        var level = event.getLevel();
        var pos = event.getPos();
        var state = level.getBlockState(pos);
        var player = event.getEntity();
        var face = event.getFace();
        if (face == null || level.isClientSide || item.isEmpty() || player.isCreative()) {
            return;
        }
        if (isInfinity(item)) {
            if (state.is(Blocks.BEDROCK)) {
                level.setBlock(pos, ModBlocks.fake_bedrock.get().defaultBlockState(), 2);
            } else if (state.is(Blocks.END_PORTAL_FRAME)) {
                // 保留原方块状态（包括是否有末影之眼）
                BlockState fakeEndPortalFrameState = ModBlocks.fake_end_portal_frame.get().defaultBlockState()
                        .setValue(EndPortalFrameBlock.FACING, state.getValue(EndPortalFrameBlock.FACING))
                        .setValue(EndPortalFrameBlock.HAS_EYE, state.getValue(EndPortalFrameBlock.HAS_EYE));
                level.setBlock(pos, fakeEndPortalFrameState, 2);
            } else if (state.is(Blocks.END_PORTAL)) {
                // 保留末地传送门状态
                level.setBlock(pos, ModBlocks.fake_end_portal.get().defaultBlockState(), 2);
            }
        }
    }
    @SubscribeEvent
    public static void updateAbilities(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.side.isServer() && isLoaded()) {
            var player = event.player;
            String key = player.getGameProfile().getName() + ":" + player.level().isClientSide;

            int x = getEternityLevel(player);

            nightVision(player, key, x>1);
            fly(player, key, x>2);
            speedUp(player, key, x>4);
        }
    }
    private static int getEternityLevel(LivingEntity living){
        int x = 0;
        for (int i=98;i<104;i++){
            if (getModifierLevel(living.getSlot(i).get(), AvaritiaModifierIds.Eternity)>0) {
                x += 1;
            }
        }
        return x;
    }
    private static int getModifierLevel(ItemStack item, ModifierId id){
        return item.getItem() instanceof IModifiable ? ToolStack.from(item).getModifierLevel(id) : 0;
    }
    private static void nightVision(Player player, String key, boolean canNightVision) {
        if (canNightVision) {
            if (entitiesWithHelmets.contains(key)) {
                player.setAirSupply(300);
                player.getFoodData().setFoodLevel(20);
                player.getFoodData().setSaturation(20f);
                if (!player.hasEffect(MobEffects.NIGHT_VISION) || player.getEffect(MobEffects.NIGHT_VISION).getDuration()<280) {
                    player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0, false, false));
                }
            } else {
                entitiesWithHelmets.add(key);
            }
        } else {
            entitiesWithHelmets.remove(key);
        }
    }
    private static void fly(Player player, String key, boolean canFly) {
        boolean isFlyingGameMode = !PlayerUtils.isPlayingMode(player);
        AbilityHandler.FlightInfo flightInfo = entitiesWithFlight.computeIfAbsent(key, uuid -> new AbilityHandler.FlightInfo());
        if (isFlyingGameMode || canFly) {
            if (!flightInfo.hadFlightItem) {
                if (!player.getAbilities().mayfly) {
                    updateClientServerFlight(player, true);
                }
                flightInfo.hadFlightItem = true;
            } else if (flightInfo.wasFlyingGameMode && !isFlyingGameMode) {
                updateClientServerFlight(player, true, flightInfo.wasFlying);
            } else if (flightInfo.wasFlyingAllowed && !player.getAbilities().mayfly) {
                updateClientServerFlight(player, true, flightInfo.wasFlying);
            }
            flightInfo.wasFlyingGameMode = isFlyingGameMode;
        } else {
            if (flightInfo.hadFlightItem) {
                if (player.getAbilities().mayfly) {
                    updateClientServerFlight(player, false);
                }
                flightInfo.hadFlightItem = false;
            }
            flightInfo.wasFlyingGameMode = false;
        }
        flightInfo.wasFlying = player.getAbilities().flying;
        flightInfo.wasFlyingAllowed = player.getAbilities().mayfly;
    }
    private static void speedUp(Player player, String key, boolean canSpeedUp) {
        AttributeInstance attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (canSpeedUp) {
            if (entitiesWithBoots.contains(key)) {
                if (attribute != null && attribute.getModifier(ATTRIBUTE_BONUS) == null) {
                    attribute.addTransientModifier(new AttributeModifier(ATTRIBUTE_BONUS, "avaritia_integration.speedup",0.03,
                            AttributeModifier.Operation.ADDITION));
                }
                player.setMaxUpStep(1.0625F);//Step 17 pixels, Allows for stepping directly from a path to the top of a block next to the path.
            } else {
                entitiesWithBoots.add(key);
            }
        } else {
            if (attribute != null) {
                attribute.removeModifier(ATTRIBUTE_BONUS);
            }
            entitiesWithBoots.remove(key);
        }
    }
    @SubscribeEvent
    public static void onPlayerDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
        stripAbilities(event.getEntity());
        reapplyFly(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        stripAbilities(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        stripAbilities(event.getEntity());
        clearFly(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        stripAbilities(event.getEntity());
    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player entity) {
            stripAbilities(entity);
        }
    }

    private static void stripAbilities(Player player) {
        String key = player.getGameProfile().getName() + ":" + player.level().isClientSide;
        entitiesWithHelmets.remove(key);
        entitiesWithFlight.remove(key);
        entitiesWithBoots.remove(key);
    }

    private static void clearFly(Player player) {
        entitiesWithFlight.remove(player.getGameProfile().getName() + ":" + player.level().isClientSide);
    }

    private static void reapplyFly(Player player) {
        //For when the dimension changes/we need to reapply the flight info values to the client
        FlightInfo flightInfo = entitiesWithFlight.get(player.getGameProfile().getName() + ":" + player.level().isClientSide);
        if (flightInfo != null) {
            if (flightInfo.wasFlyingAllowed || flightInfo.wasFlying) {
                updateClientServerFlight(player, flightInfo.wasFlyingAllowed, flightInfo.wasFlying);
            }
        }
    }
    private static void updateClientServerFlight(Player player, boolean allowFlying) {
        updateClientServerFlight(player, allowFlying, allowFlying && player.getAbilities().flying);
    }

    private static void updateClientServerFlight(Player player, boolean allowFlying, boolean isFlying) {
        player.getAbilities().mayfly = allowFlying;
        player.getAbilities().flying = isFlying;
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.onUpdateAbilities();
        }
    }
    private static boolean isEternity(LivingEntity entity){
        return isLoaded() && getEternityLevel(entity)>3;
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onGetHurt(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        Entity attacker = source.getEntity();
        Entity entity = event.getEntity();
        if (entity instanceof Player player && isEternity(player)) {
            if (!source.is(ModDamageTypes.INFINITY)) {
                event.setCanceled(true);
            } else{
                event.setAmount(0);
                entity.level().explode(attacker, entity.getBlockX(), entity.getBlockY(), entity.getBlockZ(), 25.0F, Level.ExplosionInteraction.MOB);
            }
        }
    }

    //取消对无尽套的伤害
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onAttacked(LivingAttackEvent event) {
        if (isEternity(event.getEntity()) && !event.getSource().is(ModDamageTypes.INFINITY)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (isEternity(player) && !event.getSource().is(ModDamageTypes.INFINITY)) {
                event.setAmount(0.0F);
                player.hurtTime = 0;
                player.deathTime = 0;
            }
        }
    }
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (event.isRecentlyHit() && event.getEntity() instanceof AbstractSkeleton && event.getSource().getEntity() instanceof Player player) {
            ModifierId id = TicIntegrationModifiers.BlazeCrown.getId();
            if (getModifierLevel(player.getMainHandItem(), id) > 0) {
                addDrop(event, new ItemStack(Items.WITHER_SKELETON_SKULL, 1));
            }
        }
    }
    private static void addDrop(LivingDropsEvent event, ItemStack drop) {
        ItemEntity entity = new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), drop);
        entity.setDefaultPickUpDelay();
        event.getDrops().add(entity);
    }
}
