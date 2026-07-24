package first.fargo_soul.common.item.terraSoul.cosmicPower;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.api.SoulInfoHelper;
import first.fargo_soul.api.TargetHelper;
import first.fargo_soul.common.attachment.InvincibleData;
import first.fargo_soul.common.attachment.soulInfoData.soulInfo.StackSoulInfo;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.register.SoulInfoRegister;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class BlazeSoul extends SoulItem {

    public BlazeSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        SoulInfoHelper helper = SoulInfoHelper.get(living);
        helper.removeInfo(FargoSoul.rl("blaze_soul_armor"), SoulInfoRegister.STACK);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity ticker = slotContext.entity();
        Level level = ticker.level();
        boolean isClientSide = level.isClientSide();
        if (!isClientSide && ticker.tickCount % 100 == 0) {
            SoulInfoHelper helper = SoulInfoHelper.get(ticker);
            StackSoulInfo stackSoulInfo = helper.getInfo(FargoSoul.rl("blaze_soul_armor"), SoulInfoRegister.STACK);
            if (stackSoulInfo == null) {
                stackSoulInfo = new StackSoulInfo();
                helper.putInfo(FargoSoul.rl("blaze_soul_armor"), stackSoulInfo);
            }
            stackSoulInfo.setStack(Math.min(3, stackSoulInfo.getStack() + 1));
        }
    }

    @Override
    public void target(@NotNull LivingEntity target, @Nullable Entity attacker, @NotNull DamageContainer container, boolean isClient) {
        if (!isClient) {
            SoulInfoHelper helper = SoulInfoHelper.get(target);
            StackSoulInfo stackSoulInfo = helper.getInfo(FargoSoul.rl("blaze_soul_armor"), SoulInfoRegister.STACK);
            if (stackSoulInfo != null) {
                int infoStack = stackSoulInfo.getStack();
                container.setNewDamage(container.getNewDamage() * (1 - infoStack * 0.15f));
                AttributeInstance instance = target.getAttribute(Attributes.ATTACK_DAMAGE);
                if (instance != null) {
                    double damage = instance.getValue() * 4;
                    TargetHelper targetHelper = TargetHelper.get(target);
                    List<LivingEntity> targetList = targetHelper.getTargetList(3);
                    for (LivingEntity living : targetList) {
                        DamageSources damageSources = target.damageSources();
                        InvincibleData.attack(living)
                                .attacker(target.getUUID())
                                .damageSource(damageSources.inFire())
                                .damageAmount((float) damage)
                                .apply();
                    }
                }
            }
        }
    }
}