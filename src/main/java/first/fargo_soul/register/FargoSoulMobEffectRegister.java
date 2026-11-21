package first.fargo_soul.register;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import first.fargo_soul.FargoSoul;
import first.lyra.register.LyraAttributeRegister;
import first.lyra.register.SimpleMobEffectBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.List;

public class FargoSoulMobEffectRegister {

    private static final Registrate REGISTER = FargoSoul.REGISTRATE;

    public static final RegistryEntry<MobEffect, MobEffect> DeathMark =
            REGISTER.simple("death_mark", Registries.MOB_EFFECT, () -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0xB51F1F)
                    .addAttributeModifier(Attributes.MAX_HEALTH, FargoSoul.rl("death_mark"), -0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .addAttributeModifier(Attributes.ARMOR, FargoSoul.rl("death_mark"), -0.4f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, FargoSoul.rl("death_mark"), 0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .build()
            );

    /** 水晶碎甲：降低目标 20 点护甲 */
    public static final RegistryEntry<MobEffect, MobEffect> CrystalArmorBreak =
            REGISTER.simple("crystal_armor_break", Registries.MOB_EFFECT, () -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0x7FE0FF)
                    .addAttributeModifier(Attributes.ARMOR, FargoSoul.rl("crystal_armor_break"), -20.0f, AttributeModifier.Operation.ADD_VALUE)
                    .build()
            );

    /** 先发至人：下次攻击造成的伤害提升 60% */
    public static final RegistryEntry<MobEffect, MobEffect> PreemptiveStrike =
            REGISTER.simple("preemptive_strike", Registries.MOB_EFFECT, () -> new SimpleMobEffectBuilder(MobEffectCategory.BENEFICIAL, 0xFFD700)
                    .build()
            );

    /** 暗影焰：周期性造成魔法伤害 */
    public static final RegistryEntry<MobEffect, MobEffect> ShadowFire =
            REGISTER.simple("shadow_fire", Registries.MOB_EFFECT, () -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0x4B0082)
                    .addAttributeModifier(LyraAttributeRegister.HealthRegen, FargoSoul.rl("shadow_fire"), amplifier -> -(amplifier + 1) * 1.5, AttributeModifier.Operation.ADD_VALUE)
                    .build()
            );

    /** 暗影之赐：最大生命 +40%，移速 +15%，攻击 +40% */
    public static final RegistryEntry<MobEffect, MobEffect> ShadowGift =
            REGISTER.simple("shadow_gift", Registries.MOB_EFFECT, () -> new SimpleMobEffectBuilder(MobEffectCategory.BENEFICIAL, 0x4B0082)
                    .addAttributeModifier(Attributes.MAX_HEALTH, FargoSoul.rl("shadow_gift"), 0.4f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, FargoSoul.rl("shadow_gift"), 0.15f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE, FargoSoul.rl("shadow_gift"), 0.4f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .build()
            );

    /** 铅中毒：每秒受到毒伤，并传染给周围目标 */
    public static final RegistryEntry<MobEffect, MobEffect> LeadPoisoning =
            REGISTER.simple("lead_poisoning", Registries.MOB_EFFECT, () -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0x5A5A5A)
                    .addAttributeModifier(LyraAttributeRegister.HealthRegen, FargoSoul.rl("lead_poisoning"), amplifier -> -(amplifier + 1) * 1.0, AttributeModifier.Operation.ADD_VALUE)
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
    public static final RegistryEntry<MobEffect, MobEffect> AmazingMoment =
            REGISTER.simple("amazing_moment", Registries.MOB_EFFECT, () -> new SimpleMobEffectBuilder(MobEffectCategory.BENEFICIAL, 0xFFD700)
                    .build()
            );

    /** 泰拉共鸣：释放雷电球概率提升，雷电球伤害提升 */
    public static final RegistryEntry<MobEffect, MobEffect> TerraResonance =
            REGISTER.simple("terra_resonance", Registries.MOB_EFFECT, () -> new SimpleMobEffectBuilder(MobEffectCategory.BENEFICIAL, 0x38ffec)
                    .build()
            );

    /** 流血：恢复的生命值减少 50% */
    public static final RegistryEntry<MobEffect, MobEffect> Bleeding =
            REGISTER.simple("bleeding", Registries.MOB_EFFECT, () -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0xC22A2A)
                    .build()
            );

    /** 血如泉涌：恢复的生命值减少 70% */
    public static final RegistryEntry<MobEffect, MobEffect> Hemorrhage =
            REGISTER.simple("hemorrhage", Registries.MOB_EFFECT, () -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0xD22A2A)
                    .build()
            );

    /** 涂油：受到的火焰伤害提升 200% */
    public static final RegistryEntry<MobEffect, MobEffect> Oiled =
            REGISTER.simple("oil", Registries.MOB_EFFECT, () -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0x6B4A2B)
                    .build()
            );

    /** 寒冷：移动速度减少 30% */
    public static final RegistryEntry<MobEffect, MobEffect> Chill =
            REGISTER.simple("chilly", Registries.MOB_EFFECT, () -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0x9BC9FF)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, FargoSoul.rl("chilly"), -0.3f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .build()
            );

    /** 星之光辉：暴击伤害提升 40% */
    public static final RegistryEntry<MobEffect, MobEffect> Starlight =
            REGISTER.simple("starlight", Registries.MOB_EFFECT, () -> new SimpleMobEffectBuilder(MobEffectCategory.BENEFICIAL, 0xFFE87A)
                    .build()
            );

    /** 盔甲破损：护甲值减少 40% */
    public static final RegistryEntry<MobEffect, MobEffect> ArmorBreak =
            REGISTER.simple("armor_break", Registries.MOB_EFFECT, () -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0x6E6E6E)
                    .addAttributeModifier(Attributes.ARMOR, FargoSoul.rl("armor_break"), -0.4f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .build()
            );

    /** 灵雾迷障：移动速度减少 20%，护甲值减少 10% */
    public static final RegistryEntry<MobEffect, MobEffect> TekeMist =
            REGISTER.simple("teke_mist", Registries.MOB_EFFECT, () -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0x9FA8DA)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, FargoSoul.rl("teke_mist"), -0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .addAttributeModifier(Attributes.ARMOR, FargoSoul.rl("teke_mist"), -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .build()
            );

    /** 山铜中毒：每秒受到毒伤 */
    public static final RegistryEntry<MobEffect, MobEffect> OrichalcumPoisoning =
            REGISTER.simple("orichalcum_poisoning", Registries.MOB_EFFECT, () -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0x9FE2BF)
                    .addAttributeModifier(LyraAttributeRegister.HealthRegen, FargoSoul.rl("orichalcum_poisoning"), amplifier -> -(amplifier + 1) * 1.0, AttributeModifier.Operation.ADD_VALUE)
                    .build()
            );

    /** 迈达斯：金魔石标记，被击杀时掉落金锭 */
    public static final RegistryEntry<MobEffect, MobEffect> Midas =
            REGISTER.simple("midas", Registries.MOB_EFFECT, () -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0xFFD700)
                    .build()
            );

    /** 仙馔密酒：增益标记 */
    public static final RegistryEntry<MobEffect, MobEffect> Ambrosia =
            REGISTER.simple("ambrosia", Registries.MOB_EFFECT, () -> new SimpleMobEffectBuilder(MobEffectCategory.BENEFICIAL, 0xFFD75F)
                    .build()
            );

    public static void init() {
    }

}
