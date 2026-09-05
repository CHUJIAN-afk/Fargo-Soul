package first.fargo_soul.dadageneeator.provider;

import first.lyra.dataGenerator.provider.LyraLanguageProvider;
import net.minecraft.data.PackOutput;

public class FargoSoulLangProvider extends LyraLanguageProvider {

    public FargoSoulLangProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void init() {
        // ===================== 配置 =====================
        add("fargo_soul.configuration.soul_chance", "Chance for other creatures to carry souls", "其他生物携带魂石的概率");
        add("fargo_soul.configuration.soul_drop_chance", "Chance for soul-carrying creatures to drop souls", "携带魂石的生物掉落魂石的概率");
        add("fargo_soul.configuration.soul_hp_increase_per_soul", "Base HP increase per soul for monsters", "每个魂石提升怪物的基础血量");
        add("fargo_soul.configuration.soul_hp_multiplier_per_soul", "HP multiplier per soul for monsters", "每个魂石提升怪物的血量系数");
        add("fargo_soul.configuration.soul_size_multiplier_per_soul", "Size multiplier per soul for monsters", "每个魂石提升怪物的体型系数");
        add("fargo_soul.configuration.allow_creatures_that_spawn_through_unnatural_paths_to_carry_souls", "Allow non-naturally spawned creatures to carry souls", "是否允许非自然途径生成的生物携带魂石");
        add("fargo_soul.configuration.allow_creatures_that_spawn_through_unnatural_paths_to_drop_souls", "Allow non-naturally spawned creatures to drop souls", "是否允许非自然途径生成的生物掉落魂石");
        add("fargo_soul.configuration.allow_friendly_mob_soul", "Allow friendly mobs to generate souls", "是否允许友善生物生成魂石");
        add("fargo_soul.configuration.allow_hostile_mob_soul", "Allow hostile mobs to generate souls", "是否允许敌人生成魂石");
        add("fargo_soul.configuration.cosmic_crucible_size", "Cosmic Crucible item variety capacity", "宇宙坩埚的物品种类数量");
        add("fargo_soul.configuration.cosmic_crucible_item_rendering", "Enable item rendering in Cosmic Crucible", "是否开启宇宙坩埚容器物品渲染");
        add("fargo_soul.configuration.cosmic_crucible_black_hole_event_horizon_rendering", "Enable Cosmic Crucible black hole event horizon rendering", "是否开启宇宙坩埚黑洞事件视界渲染");
        add("fargo_soul.configuration.embed_a_child_soul_in_the_item_tooltip", "Embed child souls in item tooltips", "是否在物品提示中嵌入子魂石");
        add("fargo_soul.configuration.creature_soul_rendering", "Render souls carried by creatures", "是否渲染生物携带的魂石");
        add("fargo_soul.configuration.item_render_scaling", "Enable item render scaling", "是否开启物品渲染缩放");
        add("fargo_soul.configuration.show_soul_tooltip", "Show soul information", "是否显示魂石信息");
        add("fargo_soul.configuration.show_soul_tooltip_scale", "Soul info scale", "魂石信息缩放比例");
        add("fargo_soul.configuration.show_soul_tooltip_x_offset", "Soul info X offset", "魂石信息X轴偏移");
        add("fargo_soul.configuration.show_soul_tooltip_y_offset", "Soul info Y offset", "魂石信息Y轴偏移");
        add("fargo_soul.configuration.information_interval", "Soul info display interval", "魂石信息显示间隔");
        add("fargo_soul.configuration.soul_info_max_render_time", "Soul info max render time", "魂石信息最大显示时间");
        add("fargo_soul.configuration.resident_show_soul_information", "Always show soul information", "是否常驻显示魂石信息");
        add("fargo_soul.configuration.black_soul_list", "Creature soul blacklist", "生物魂石黑名单");
        // ===================== 模组基础 =====================
        add("itemGroup.fargo_soul", "Fargo Soul", "Fargo 魂石");
        add("curios.identifier.soul", "Soul", "魂");
        add("fargo_soul.key.tooltip", "Hold [", "按住 [ %s ] 可查看概要");
        add("tooltip.item.soul_item", "Obtain the effects of all lower-tier souls", "获得所有下位魔石的效果");
        // ===================== 药水效果 =====================
        add("effect.fargo_soul.lead_poisoning", "Lead Poisoning", "铅中毒");
        add("effect.fargo_soul.oil", "Oiled", "浸油");
        add("effect.fargo_soul.orichalcum_poisoning", "Orichalcum Poisoning", "山铜中毒");
        add("effect.fargo_soul.shadow_fire", "Shadowflame", "暗影焰");
        add("effect.fargo_soul.midas", "Midas", "迈达斯");
        add("effect.fargo_soul.death_mark", "Death Mark", "死亡标记");
        add("effect.fargo_soul.crystal_armor_break", "Crystal Armor Break", "水晶碎甲");
        add("effect.fargo_soul.preemptive_strike", "Preemptive Strike", "先发至人");
        add("effect.fargo_soul.shadow_gift", "Shadow Gift", "暗影之赐");
        // ===================== 方块 =====================
        add("tooltip.fargo_soul.cosmic_crucible", "Used for fusion. Contains massive storage (default 64 slots, stack limit 2,147,483,647)\nInsert items via the top; right-click empty-handed to take last item\nRetains contents when broken", "可用于融合，内含的空间可储存巨量物品(默认64格，单格堆叠上限2,147,483,647)\n通过投入或交互顶部放入物品，空手右键拿出最近放入的物品\n破坏后保留内容物");
        // ===================== 界面 =====================
        add("fargo_soul.screen.is_empty", "You are not carrying any souls", "你没有携带任何魂石");
        // ===================== 进度 =====================
        add("fargo_soul.advancement.get", "Obtained", "获得");
        // ===================== JEI =====================
        add("jei.fargo_soul.category.soul", "Cosmic Convergence", "宇宙聚合");
    }

    private void add(String key, String enDesc, String zhDesc) {
        if (locale.equals("en_us")) {
            add(key, enDesc);
        } else if (locale.equals("zh_cn")) {
            add(key, zhDesc);
        }
    }

}
