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

    /** 流血：恢复的生命值减少 50% */
    public static final Holder<MobEffect> Bleeding =
            Register.register("bleeding", location -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0xC22A2A)
                    .build()
            );

    /** 血如泉涌：恢复的生命值减少 70% */
    public static final Holder<MobEffect> Hemorrhage =
            Register.register("hemorrhage", location -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0xD22A2A)
                    .build()
            );

    /** 涂油：受到的火焰伤害提升 200% */
    public static final Holder<MobEffect> Oiled =
            Register.register("oil", location -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0x6B4A2B)
                    .build()
            );

    /** 寒冷：移动速度减少 30% */
    public static final Holder<MobEffect> Chill =
            Register.register("chilly", location -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0x9BC9FF)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, location, -0.3f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .build()
            );

    /** 星之光辉：暴击伤害提升 40% */
    public static final Holder<MobEffect> Starlight =
            Register.register("starlight", location -> new SimpleMobEffectBuilder(MobEffectCategory.BENEFICIAL, 0xFFE87A)
                    .build()
            );

    /** 盔甲破损：护甲值减少 40% */
    public static final Holder<MobEffect> ArmorBreak =
            Register.register("armor_break", location -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0x6E6E6E)
                    .addAttributeModifier(Attributes.ARMOR, location, -0.4f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .build()
            );

    /** 灵雾迷障：移动速度减少 20%，护甲值减少 10% */
    public static final Holder<MobEffect> TekeMist =
            Register.register("teke_mist", location -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0x9FA8DA)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, location, -0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .addAttributeModifier(Attributes.ARMOR, location, -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                    .build()
            );

    /** 山铜中毒：每秒受到毒伤 */
    public static final Holder<MobEffect> OrichalcumPoisoning =
            Register.register("orichalcum_poisoning", location -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0x9FE2BF)
                    .addAttributeModifier(LyraAttributeRegister.HealthRegen, location, amplifier -> -(amplifier + 1) * 1.0, AttributeModifier.Operation.ADD_VALUE)
                    .build()
            );

    /** 迈达斯：金魔石标记，被击杀时掉落金锭 */
    public static final Holder<MobEffect> Midas =
            Register.register("midas", location -> new SimpleMobEffectBuilder(MobEffectCategory.HARMFUL, 0xFFD700)
                    .build()
            );

    /** 仙馔密酒：增益标记 */
    public static final Holder<MobEffect> Ambrosia =
            Register.register("ambrosia", location -> new SimpleMobEffectBuilder(MobEffectCategory.BENEFICIAL, 0xFFD75F)
                    .build()
            );

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }

}
