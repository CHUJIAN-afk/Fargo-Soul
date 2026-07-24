package first.fargo_soul.api;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

/**
 * 属性修改器的链式构建器，用于简化 {@code LivingEntity} 的属性修饰操作。
 * <p>
 * 替代 {@link first.fargo_soul.utils.AttributeUtils} 的静态方法调用，提供更流畅的链式 API。
 * <p>
 *
 * <h3>基本用法</h3>
 * <pre>{@code
 * // 条件性添加/移除属性修饰器（最常见场景）
 * AttributeBuild.build(living)
 *     .attribute(Attributes.KNOCKBACK_RESISTANCE)
 *     .location(itemId)
 *     .amount(1.0)
 *     .base()                          // ADD_MULTIPLIED_BASE
 *     .condition(soulInfo.isEnabled())
 *     .apply();
 *
 * // 等价于 AttributeUtils 的写法：
 * AttributeUtils.condition(living, Attributes.KNOCKBACK_RESISTANCE,
 *     AttributeUtils.base(itemId, 1.0), soulInfo.isEnabled());
 * }</pre>
 *
 * <h3>操作模式快捷方法</h3>
 * <pre>{@code
 * .value(amount)   → ADD_VALUE           (直接加值，如 +10 护甲)
 * .base(amount)    → ADD_MULTIPLIED_BASE  (乘以基础值，如 +25% 移速)
 * .total(amount)   → ADD_MULTIPLIED_TOTAL (乘以最终值，如 +50% 总伤害)
 * }</pre>
 *
 * <h3>从 AttributeModifier 构建</h3>
 * <pre>{@code
 * AttributeBuild.build(living)
 *     .attribute(Attributes.MAX_HEALTH)
 *     .modifier(AttributeUtils.base(itemId, 0.5))
 *     .condition(enabled)
 *     .apply();
 * }</pre>
 *
 * <h3>无条件添加修饰器</h3>
 * <pre>{@code
 * AttributeBuild.build(living)
 *     .attribute(Attributes.ARMOR)
 *     .location(itemId)
 *     .value(15)
 *     .apply();   // condition 默认为 true，amount 非 0 时直接添加
 * }</pre>
 *
 * <h3>移除修饰器</h3>
 * <pre>{@code
 * AttributeBuild.build(living)
 *     .attribute(Attributes.ARMOR)
 *     .location(itemId)
 *     .remove();  // 直接移除，忽略 amount/operation/condition
 * }</pre>
 *
 * @see first.fargo_soul.utils.AttributeUtils
 * @see AttributeModifier
 * @see AttributeModifier.Operation
 */
public class AttributeBuild {

    private final LivingEntity living;
    private Holder<Attribute> attribute = null;
    private ResourceLocation location = null;
    private double amount = 0;
    private AttributeModifier.Operation operation = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
    private boolean condition = true;

    private AttributeBuild(LivingEntity living) {
        this.living = living;
    }

    /**
     * 创建一个针对指定实体的属性构建器。
     *
     * @param living 目标实体
     * @return 新的 AttributeBuild 实例
     */
    public static AttributeBuild build(LivingEntity living) {
        return new AttributeBuild(living);
    }

    // ==================== 链式设置方法 ====================

    /**
     * 设置目标属性。
     *
     * @param attribute 属性 Holder，如 {@code Attributes.KNOCKBACK_RESISTANCE}
     * @return this，用于链式调用
     */
    public AttributeBuild attribute(Holder<Attribute> attribute) {
        this.attribute = attribute;
        return this;
    }

    /**
     * 设置修饰器的资源定位符（唯一标识）。
     * <p>
     * 通常使用物品的注册 ID，确保每个修饰器有唯一 key。
     *
     * @param location 修饰器的 ResourceLocation 标识
     * @return this，用于链式调用
     */
    public AttributeBuild location(ResourceLocation location) {
        this.location = location;
        return this;
    }

    /**
     * 设置修饰器的数值。
     *
     * @param amount 修饰值（正数增加，负数减少）
     * @return this，用于链式调用
     */
    public AttributeBuild amount(double amount) {
        this.amount = amount;
        return this;
    }

    /**
     * 设置修饰器的操作模式。
     *
     * @param operation 操作模式
     * @return this，用于链式调用
     * @see AttributeModifier.Operation
     */
    public AttributeBuild operation(AttributeModifier.Operation operation) {
        this.operation = operation;
        return this;
    }

    /**
     * 设置操作模式为 {@link AttributeModifier.Operation#ADD_VALUE}（直接加值）。
     * <p>
     * 同时设置 amount。
     * <p>
     * 示例：{@code .value(15)} → 护甲 +15
     *
     * @param amount 修饰值
     * @return this，用于链式调用
     */
    public AttributeBuild value(double amount) {
        this.amount = amount;
        this.operation = AttributeModifier.Operation.ADD_VALUE;
        return this;
    }

    /**
     * 设置操作模式为 {@link AttributeModifier.Operation#ADD_MULTIPLIED_BASE}（乘以基础值）。
     * <p>
     * 同时设置 amount。
     * <p>
     * 示例：{@code .base(0.25)} → 基础值 × 1.25（即 +25%）
     *
     * @param amount 修饰值
     * @return this，用于链式调用
     */
    public AttributeBuild base(double amount) {
        this.amount = amount;
        this.operation = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
        return this;
    }

    /**
     * 设置操作模式为 {@link AttributeModifier.Operation#ADD_MULTIPLIED_TOTAL}（乘以最终值）。
     * <p>
     * 同时设置 amount。
     * <p>
     * 示例：{@code .total(0.5)} → 最终值 × 1.5（即 +50%）
     *
     * @param amount 修饰值
     * @return this，用于链式调用
     */
    public AttributeBuild total(double amount) {
        this.amount = amount;
        this.operation = AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL;
        return this;
    }

    /**
     * 从已有的 {@link AttributeModifier} 中提取 location、amount 和 operation。
     * <p>
     * 适合与 {@code AttributeUtils.value/base/total} 工厂方法配合使用：
     * <pre>{@code
     * .modifier(AttributeUtils.base(itemId, 0.5))
     * }</pre>
     *
     * @param modifier 属性修饰器
     * @return this，用于链式调用
     */
    public AttributeBuild modifier(AttributeModifier modifier) {
        this.location = modifier.id();
        this.amount = modifier.amount();
        this.operation = modifier.operation();
        return this;
    }

    /**
     * 设置条件：为 true 时添加/更新修饰器，为 false 时移除修饰器。
     * <p>
     * 默认为 true。
     *
     * @param condition 是否启用修饰器
     * @return this，用于链式调用
     */
    public AttributeBuild condition(boolean condition) {
        this.condition = condition;
        return this;
    }

    // ==================== 终止方法 ====================

    /**
     * 根据当前配置应用属性修饰器。
     * <p>
     * 行为逻辑：
     * <ul>
     *   <li>若 attribute 或 location 为 null → 忽略，不做任何操作</li>
     *   <li>若 condition 为 true 且 amount ≠ 0 → 添加或更新修饰器（仅在值/模式变化时更新）</li>
     *   <li>若 condition 为 false 或 amount == 0 → 移除已存在的修饰器</li>
     * </ul>
     * <p>
     * 此方法为幂等操作：相同参数重复调用不会产生多余的移除/添加。
     */
    public void apply() {
        if (attribute == null || location == null) {
            return;
        }
        if (condition && amount != 0) {
            if (living.getAttribute(attribute) instanceof AttributeInstance instance) {
                AttributeModifier old = instance.getModifier(location);
                if (old == null || old.amount() != amount || old.operation() != operation) {
                    if (old != null) {
                        instance.removeModifier(location);
                    }
                    instance.addPermanentModifier(new AttributeModifier(location, amount, operation));
                }
            }
        } else {
            if (living.getAttribute(attribute) instanceof AttributeInstance instance) {
                AttributeModifier old = instance.getModifier(location);
                if (old != null) {
                    instance.removeModifier(location);
                }
            }
        }
    }

    /**
     * 直接移除指定属性上的修饰器，忽略 amount、operation、condition。
     * <p>
     * 仅需 attribute 和 location 即可执行移除。
     *
     * @return this，用于链式调用（移除后可继续配置并 apply 其他修饰器）
     */
    public AttributeBuild remove() {
        if (attribute != null && location != null) {
            if (living.getAttribute(attribute) instanceof AttributeInstance instance) {
                AttributeModifier old = instance.getModifier(location);
                if (old != null) {
                    instance.removeModifier(location);
                }
            }
        }
        return this;
    }
}
