package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import first.lyra.register.LyraAttributeRegister;
import first.lyra.register.SimpleMobEffectBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class FargoSoulMobEffectRegister {

    public static final DeferredRegister<MobEffect> Register = DeferredRegister.create(Registries.MOB_EFFECT, FargoSoul.MODID);

    public static final Holder<MobEffect> DeathMark =
            Register.register("death_mark", location -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0xB51F1F)
                    .addAttributeModifier(Attributes.MAX_HEALTH, location, -0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .addAttributeModifier(Attributes.ARMOR, location, -0.4f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, location, 0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .build()
            );

    /** 水晶碎甲：降低目标 20 点护甲 */
    public static final Holder<MobEffect> CrystalArmorBreak =
            Register.register("crystal_armor_break", location -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0x7FE0FF)
                    .addAttributeModifier(Attributes.ARMOR, location, -20.0f, AttributeModifier.Operation.ADD_VALUE)
                    .build()
            );

    /** 先发至人：下次攻击造成的伤害提升 60% */
    public static final Holder<MobEffect> PreemptiveStrike =
            Register.register("preemptive_strike", location -> new SimpleMobEffectBuilder(MobEffectCategory.BENEFICIAL, 0xFFD700)
                    .build()
            );

    /** 暗影焰：周期性造成魔法伤害 */
    public static final Holder<MobEffect> ShadowFire =
            Register.register("shadow_fire", location -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0x4B0082)
                    .addAttributeModifier(LyraAttributeRegister.HealthRegen, location, amplifier -> -(amplifier + 1) * 1.5, AttributeModifier.Operation.ADD_VALUE)
                    .build()
            );

    /** 暗影之赐：最大生命 +40%，移速 +15%，攻击 +40% */
    public static final Holder<MobEffect> ShadowGift =
            Register.register("shadow_gift", location -> new SimpleMobEffectBuilder(MobEffectCategory.BENEFICIAL, 0x4B0082)
                    .addAttributeModifier(Attributes.MAX_HEALTH, location, 0.4f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, location, 0.15f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE, location, 0.4f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .build()
            );

    /** 铅中毒：每秒受到毒伤，并传染给周围目标 */
    public static final Holder<MobEffect> LeadPoisoning =
            Register.register("lead_poisoning", location -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0x5A5A5A)
                    .addAttributeModifier(LyraAttributeRegister.HealthRegen, location, amplifier -> -(amplifier + 1) * 1.0, AttributeModifier.Operation.ADD_VALUE)
                    .shouldApplyEffectTickThisTick((duration, amplifier) -> true)
                    .applyEffectTick((entity, amplifier) -> {
                        MobEffectInstance effect = entity.getEffect(FargoSoulMobEffectRegister.LeadPoisoning);
                        if (effect != null && effect.getDuration() > 20 && !entity.level().isClientSide()) {
                            List<LivingEntity> list = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(4), living -> living != entity && !living.hasEffect(effect.getEffect()));
                            for (LivingEntity living : list) {
                                living.addEffect(new MobEffectInstance(effect.getEffect(), effect.getDuration() / 2, amplifier));
                            }
                        }
                    })
                    .build()
            );

    /** 惊人一刻：攻击力 +150% */
    public static final Holder<MobEffect> AmazingMoment =
            Register.register("amazing_moment", location -> new SimpleMobEffectBuilder(MobEffectCategory.BENEFICIAL, 0xFFD700)
                    .build()
            );

    /** 泰拉共鸣：释放雷电球概率提升，雷电球伤害提升 */
    public static final Holder<MobEffect> TerraResonance =
            Register.register("terra_resonance", location -> new SimpleMobEffectBuilder(MobEffectCategory.BENEFICIAL, 0x38ffec)
                    .build()
            );

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }

}
