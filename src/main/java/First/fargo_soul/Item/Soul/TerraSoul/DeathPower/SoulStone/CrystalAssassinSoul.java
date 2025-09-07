package First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.KeyUtils;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CrystalAssassinSoul extends SoulItem {
    public static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public CrystalAssassinSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
    }


    public static void CrystalAssassinSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && event.getSource().getWeaponItem() != null) {
            if (event.getEntity() instanceof Monster monster && player.equals(monster.getTarget())) {
                long CrystalAssassinSoul = player.getPersistentData().getLong("CrystalAssassinSoul");
                long gameTime = player.level().getGameTime();
                if (CrystalAssassinSoul < gameTime) {
                    player.addEffect(new MobEffectInstance(EffectRegister.PreemptiveStrike, 200));
                    player.getPersistentData().putLong("CrystalAssassinSoul", gameTime + 400);
                }

            }
            if (player.getEffect(EffectRegister.PreemptiveStrike) != null) {
                player.removeEffect(EffectRegister.PreemptiveStrike);
            }
            if (event.getEntity() instanceof LivingEntity livingEntity && livingEntity.getAttribute(Attributes.ARMOR) instanceof AttributeInstance) {
                ResourceLocation resourceLocation = SoulsRegister.CrystalAssassinSoul.getId();
                AttributeUtils.addAttributeModifier(livingEntity, Attributes.ARMOR, resourceLocation, -10, AttributeModifier.Operation.ADD_VALUE);
                executorService.schedule(() -> AttributeUtils.removeAttributeModifier(livingEntity, Attributes.ARMOR, resourceLocation), 10, TimeUnit.SECONDS);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void CrystalAssassinSoulMovementInputHandler(MovementInputUpdateEvent event) {
        if (event.getEntity() instanceof LocalPlayer player && CurioUtils.isEquipped(player, SoulsRegister.CrystalAssassinSoul.get())) {
            long CrystalAssassinSoulMovement = player.getPersistentData().getLong("CrystalAssassinSoulMovement");
            long GameTime = player.level().getGameTime();
            if (CrystalAssassinSoulMovement < GameTime) {
                if (KeyUtils.isDoubleTappingForward(event.getInput())) {
                    player.getPersistentData().putLong("CrystalAssassinSoulMovement", GameTime + 20);
                    Vec3 viewVector = player.getLookAngle().normalize().scale(1.2);
                    player.addDeltaMovement(viewVector);
                }
            }
        }
    }






}
