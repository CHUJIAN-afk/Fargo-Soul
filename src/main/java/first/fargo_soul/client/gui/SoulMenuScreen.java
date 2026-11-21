package first.fargo_soul.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.network.SoulTogglePacket;
import first.fargo_soul.register.FargoSoulAttachmentRegister;
import first.fargo_soul.register.FargoSoulKeyRegister;
import first.lyra.common.dataComponent.LyraRarity;
import first.lyra.register.LyraDataComponentRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.GuiSpriteManager;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.metadata.gui.GuiSpriteScaling;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.util.*;

/** 魂石开关配置界面，采用原版风格材质、阴影树形连线与展开及点击动画。 */
public class SoulMenuScreen extends Screen {

    private final int rowHeight = 24;
    private final int indentStep = 14;
    private final int toggleWidth = 34;
    private final int branchWidth = 38;
    private final int rowButtonHeight = 18;
    private final int scrollTrackWidth = 12;

    private final ResourceLocation panelSprite = FargoSoul.rl("soul_menu/panel");
    private final ResourceLocation listBgSprite = FargoSoul.rl("soul_menu/list_bg");
    private final ResourceLocation slotSprite = FargoSoul.rl("soul_menu/slot");
    private final ResourceLocation slotDisabledSprite = FargoSoul.rl("soul_menu/slot_disabled");
    private final ResourceLocation rowBgSprite = FargoSoul.rl("soul_menu/row_bg");
    private final ResourceLocation rowHoverSprite = FargoSoul.rl("soul_menu/row_hover");
    private final ResourceLocation rowDisabledSprite = FargoSoul.rl("soul_menu/row_disabled");
    private final ResourceLocation buttonSprite = FargoSoul.rl("soul_menu/button");
    private final ResourceLocation buttonHoverSprite = FargoSoul.rl("soul_menu/button_hover");
    private final ResourceLocation buttonPressedSprite = FargoSoul.rl("soul_menu/button_pressed");
    private final ResourceLocation buttonDisabledSprite = FargoSoul.rl("soul_menu/button_disabled");
    private final ResourceLocation toggleOnSprite = FargoSoul.rl("soul_menu/toggle_on");
    private final ResourceLocation toggleOnHoverSprite = FargoSoul.rl("soul_menu/toggle_on_hover");
    private final ResourceLocation toggleOnPressedSprite = FargoSoul.rl("soul_menu/toggle_on_pressed");
    private final ResourceLocation toggleOffSprite = FargoSoul.rl("soul_menu/toggle_off");
    private final ResourceLocation toggleOffHoverSprite = FargoSoul.rl("soul_menu/toggle_off_hover");
    private final ResourceLocation toggleOffPressedSprite = FargoSoul.rl("soul_menu/toggle_off_pressed");
    private final ResourceLocation branchSprite = FargoSoul.rl("soul_menu/branch_btn");
    private final ResourceLocation branchHoverSprite = FargoSoul.rl("soul_menu/branch_btn_hover");
    private final ResourceLocation branchPressedSprite = FargoSoul.rl("soul_menu/branch_btn_pressed");
    private final ResourceLocation scrollTrackSprite = FargoSoul.rl("soul_menu/scroll_track");
    private final ResourceLocation scrollThumbSprite = FargoSoul.rl("soul_menu/scroll_thumb");
    private final ResourceLocation scrollThumbHoverSprite = FargoSoul.rl("soul_menu/scroll_thumb_hover");
    private final ResourceLocation scrollThumbPressedSprite = FargoSoul.rl("soul_menu/scroll_thumb_pressed");
    private final ResourceLocation arrowCollapsedSprite = FargoSoul.rl("soul_menu/arrow_collapsed");
    private final ResourceLocation arrowCollapsedHoverSprite = FargoSoul.rl("soul_menu/arrow_collapsed_hover");
    private final ResourceLocation arrowMidSprite = FargoSoul.rl("soul_menu/arrow_mid");
    private final ResourceLocation arrowMidHoverSprite = FargoSoul.rl("soul_menu/arrow_mid_hover");
    private final ResourceLocation arrowExpandedSprite = FargoSoul.rl("soul_menu/arrow_expanded");
    private final ResourceLocation arrowExpandedHoverSprite = FargoSoul.rl("soul_menu/arrow_expanded_hover");

    private final List<TreeNode> treeRoots = new ArrayList<>();
    private final List<LayoutRow> layoutRows = new ArrayList<>();
    private final Set<SoulItem> equippedSouls = new HashSet<>();
    private final Set<SoulItem> workingDisabled = new HashSet<>();
    private final Set<SoulItem> expanded = new HashSet<>();
    private final Map<SoulItem, Float> expandProgress = new HashMap<>();
    private final Map<String, Long> clickAnims = new HashMap<>();

    private int panelLeft;
    private int panelTop;
    private int panelWidth;
    private int panelHeight;
    private int listLeft;
    private int listTop;
    private int listRight;
    private int listBottom;
    private int scrollTrackLeft;
    private float totalContentHeight;
    private double targetScrollOffset;
    private double displayScrollOffset;
    private boolean draggingScrollbar;
    private double dragThumbGrabOffset;
    private PressedTarget pressedTarget;
    private long lastFrameMs;
    private boolean synced;

    public SoulMenuScreen(Player player) {
        super(Component.translatable("gui.fargo_soul.soul_menu.title"));
        SoulItemData data = player.getData(FargoSoulAttachmentRegister.SOUL_ITEM_DATA);
        workingDisabled.addAll(data.getDisabled());
        equippedSouls.addAll(data.getEquipped());

        Set<SoulItem> strictDescendants = new HashSet<>();
        for (SoulItem root : data.getRoots()) {
            collectDescendants(root, strictDescendants, new HashSet<>());
        }
        List<SoulItem> topRoots = new ArrayList<>();
        for (SoulItem root : data.getRoots()) {
            if (!strictDescendants.contains(root)) {
                topRoots.add(root);
            }
        }
        if (topRoots.isEmpty()) {
            topRoots.addAll(data.getRoots());
        }
        topRoots.sort(Comparator.comparingInt(BuiltInRegistries.ITEM::getId));
        for (SoulItem root : topRoots) {
            TreeNode node = buildNode(root, new HashSet<>());
            treeRoots.add(node);
            if (!node.children.isEmpty()) {
                expanded.add(root);
                expandProgress.put(root, 1f);
            }
        }
        lastFrameMs = Util.getMillis();
    }

    private static void collectDescendants(SoulItem parent, Set<SoulItem> result, Set<SoulItem> visited) {
        if (visited.add(parent)) {
            for (SoulItem child : parent.getSoulItemList()) {
                result.add(child);
                collectDescendants(child, result, visited);
            }
        }
    }

    private static TreeNode buildNode(SoulItem item, Set<SoulItem> ancestors) {
        List<TreeNode> children = new ArrayList<>();
        Set<SoulItem> descendants = new HashSet<>();
        if (ancestors.add(item)) {
            List<SoulItem> sortedChildren = new ArrayList<>(item.getSoulItemList());
            sortedChildren.sort(Comparator.comparingInt(BuiltInRegistries.ITEM::getId));
            for (SoulItem child : sortedChildren) {
                if (!ancestors.contains(child)) {
                    TreeNode childNode = buildNode(child, ancestors);
                    children.add(childNode);
                    descendants.add(child);
                    descendants.addAll(childNode.descendants);
                }
            }
            ancestors.remove(item);
        }
        return new TreeNode(item, item.getDefaultInstance(), children, descendants);
    }

    @Override
    protected void init() {
        panelWidth = Math.min(392, this.width - 28);
        panelHeight = Math.min(300, this.height - 28);
        panelLeft = (this.width - panelWidth) / 2;
        panelTop = (this.height - panelHeight) / 2;
        listLeft = panelLeft + 10;
        listTop = panelTop + 50;
        scrollTrackLeft = panelLeft + panelWidth - 10 - scrollTrackWidth;
        listRight = scrollTrackLeft - 3;
        listBottom = panelTop + panelHeight - 32;

        int barY = panelTop + 24;
        int gap = 4;
        int buttonWidth = (panelWidth - 20 - gap * 3) / 4;
        int startX = panelLeft + 10;

        SoulButton enableAll = addRenderableWidget(new SoulButton(startX, barY, buttonWidth, 20,
                Component.translatable("gui.fargo_soul.soul_menu.enable_all"),
                btn -> workingDisabled.removeAll(equippedSouls)));

        SoulButton disableAll = addRenderableWidget(new SoulButton(startX + (buttonWidth + gap), barY, buttonWidth, 20,
                Component.translatable("gui.fargo_soul.soul_menu.disable_all"),
                btn -> workingDisabled.addAll(equippedSouls)));

        SoulButton expandAll = addRenderableWidget(new SoulButton(startX + (buttonWidth + gap) * 2, barY, buttonWidth, 20,
                Component.translatable("gui.fargo_soul.soul_menu.expand_all"),
                btn -> {
                    for (TreeNode root : treeRoots) {
                        collectParents(root, expanded);
                    }
                }));

        SoulButton collapseAll = addRenderableWidget(new SoulButton(startX + (buttonWidth + gap) * 3, barY, buttonWidth, 20,
                Component.translatable("gui.fargo_soul.soul_menu.collapse_all"),
                btn -> expanded.clear()));

        boolean hasSouls = !treeRoots.isEmpty();
        enableAll.active = hasSouls;
        disableAll.active = hasSouls;
        expandAll.active = hasSouls;
        collapseAll.active = hasSouls;

        addRenderableWidget(new SoulButton(panelLeft + panelWidth - 74, panelTop + panelHeight - 26, 64, 20,
                Component.translatable("gui.fargo_soul.soul_menu.done"),
                btn -> onClose()));

        rebuildLayout();
        clampScroll(true);
    }

    private static void collectParents(TreeNode node, Set<SoulItem> target) {
        if (!node.children.isEmpty()) {
            target.add(node.item);
            for (TreeNode child : node.children) {
                collectParents(child, target);
            }
        }
    }

    /** 推进展开与滚动插值，并重建当前帧所有可见节点的布局与裁剪范围。 */
    private void updateAnimationsAndLayout(long now) {
        float dt = Math.clamp((now - lastFrameMs) / 1000f, 0f, 0.05f);
        lastFrameMs = now;

        for (TreeNode root : treeRoots) {
            stepExpandAnim(root, dt);
        }
        rebuildLayout();
        clampScroll(false);
        if (draggingScrollbar) {
            displayScrollOffset = targetScrollOffset;
        } else {
            double diff = targetScrollOffset - displayScrollOffset;
            if (Math.abs(diff) < 0.25) {
                displayScrollOffset = targetScrollOffset;
            } else {
                displayScrollOffset += diff * Math.min(1.0, dt * 18.0);
            }
        }
        rebuildLayout();
    }

    private void stepExpandAnim(TreeNode node, float dt) {
        if (!node.children.isEmpty()) {
            float current = expandProgress.getOrDefault(node.item, 0f);
            float target = expanded.contains(node.item) ? 1f : 0f;
            float speed = 6.5f * dt;
            if (current < target) {
                current = Math.min(target, current + speed);
            } else if (current > target) {
                current = Math.max(target, current - speed);
            }
            expandProgress.put(node.item, current);
            for (TreeNode child : node.children) {
                stepExpandAnim(child, dt);
            }
        }
    }

    private float measureSubtreeHeight(TreeNode node) {
        float height = rowHeight;
        if (!node.children.isEmpty()) {
            float p = expandProgress.getOrDefault(node.item, 0f);
            if (p > 0.001f) {
                float childrenHeight = 0f;
                for (TreeNode child : node.children) {
                    childrenHeight += measureSubtreeHeight(child);
                }
                height += childrenHeight * smoothstep(p);
            }
        }
        return height;
    }

    private void rebuildLayout() {
        layoutRows.clear();
        int innerTop = listTop + 2;
        int innerBottom = listBottom - 2;
        float cursor = innerTop - (float) displayScrollOffset;
        float total = 0f;
        for (int i = 0; i < treeRoots.size(); i++) {
            TreeNode root = treeRoots.get(i);
            float h = appendLayout(root, 0, cursor, innerTop, innerBottom, i == treeRoots.size() - 1, new boolean[0]);
            cursor += h;
            total += h;
        }
        totalContentHeight = total;
    }

    private float appendLayout(TreeNode node, int depth, float y, int clipTop, int clipBottom, boolean lastChild, boolean[] ancestorHasNext) {
        layoutRows.add(new LayoutRow(node, depth, y, clipTop, clipBottom, lastChild, ancestorHasNext));
        float totalHeight = rowHeight;
        if (!node.children.isEmpty()) {
            float p = expandProgress.getOrDefault(node.item, 0f);
            if (p > 0.001f) {
                float ease = smoothstep(p);
                float childrenFullHeight = 0f;
                for (TreeNode child : node.children) {
                    childrenFullHeight += measureSubtreeHeight(child);
                }
                float revealedHeight = childrenFullHeight * ease;
                int childClipTop = Math.max(clipTop, Math.round(y + rowHeight));
                int childClipBottom = Math.min(clipBottom, Math.round(y + rowHeight + revealedHeight));
                float slideUp = (1f - ease) * Math.min(rowHeight, childrenFullHeight) * 0.35f;
                float childY = y + rowHeight - slideUp;
                boolean[] nextAncestors = Arrays.copyOf(ancestorHasNext, depth + 1);
                nextAncestors[depth] = !lastChild;
                for (int i = 0; i < node.children.size(); i++) {
                    childY += appendLayout(node.children.get(i), depth + 1, childY, childClipTop, childClipBottom, i == node.children.size() - 1, nextAncestors);
                }
                totalHeight += revealedHeight;
            }
        }
        return totalHeight;
    }

    private static float smoothstep(float t) {
        return t * t * (3f - 2f * t);
    }

    private double getMaxScroll() {
        return Math.max(0, totalContentHeight - (listBottom - listTop - 4));
    }

    private void clampScroll(boolean immediate) {
        double maxScroll = getMaxScroll();
        targetScrollOffset = Mth.clamp(targetScrollOffset, 0, maxScroll);
        if (immediate) {
            displayScrollOffset = targetScrollOffset;
        } else {
            displayScrollOffset = Mth.clamp(displayScrollOffset, 0, maxScroll);
        }
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        blitTiledBackground(guiGraphics, panelSprite, panelLeft, panelTop, panelWidth, panelHeight);
        blitTiledBackground(guiGraphics, listBgSprite, listLeft, listTop, listRight - listLeft, listBottom - listTop);
        guiGraphics.blitSprite(scrollTrackSprite, scrollTrackLeft, listTop, scrollTrackWidth, listBottom - listTop);
    }

    /** 合批提交九宫格底板的平铺纹理。 */
    private void blitTiledBackground(GuiGraphics guiGraphics, ResourceLocation id, int x, int y, int width, int height) {
        GuiSpriteManager sprites = minecraft.getGuiSprites();
        TextureAtlasSprite sprite = sprites.getSprite(id);
        if (width > 0 && height > 0 && sprites.getSpriteScaling(sprite) instanceof GuiSpriteScaling.NineSlice(int width1, int height1, GuiSpriteScaling.NineSlice.Border border) && border.left() == border.right()) {
            int left = Math.min(border.left(), width / 2);
            int top = Math.min(border.top(), height / 2);
            int bottom = Math.min(border.bottom(), height / 2);
            int middleRight = width - left;
            int middleBottom = height - bottom;
            int tileWidth = width1 - left * 2;
            int tileHeight = height1 - top - bottom;

            Matrix4f pose = guiGraphics.pose().last().pose();
            BufferBuilder buffer = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            for (int dx = 0; dx < width; ) {
                int u = 0;
                int quadWidth = left;
                if (dx >= middleRight) {
                    u = width1 - left;
                    quadWidth = left;
                } else if (dx >= left) {
                    u = left;
                    quadWidth = Math.min(tileWidth, middleRight - dx);
                }
                float u0 = sprite.getU((float) u / width1);
                float u1 = sprite.getU((float) (u + quadWidth) / width1);
                for (int dy = 0; dy < height; ) {
                    int v = 0;
                    int quadHeight = top;
                    if (dy >= middleBottom) {
                        v = height1 - bottom;
                        quadHeight = bottom;
                    } else if (dy >= top) {
                        v = top;
                        quadHeight = Math.min(tileHeight, middleBottom - dy);
                    }
                    float v0 = sprite.getV((float) v / height1);
                    float v1 = sprite.getV((float) (v + quadHeight) / height1);
                    buffer.addVertex(pose, x + dx, y + dy, 0).setUv(u0, v0);
                    buffer.addVertex(pose, x + dx, y + dy + quadHeight, 0).setUv(u0, v1);
                    buffer.addVertex(pose, x + dx + quadWidth, y + dy + quadHeight, 0).setUv(u1, v1);
                    buffer.addVertex(pose, x + dx + quadWidth, y + dy, 0).setUv(u1, v0);
                    dy += quadHeight;
                }
                dx += quadWidth;
            }
            RenderSystem.setShaderTexture(0, sprite.atlasLocation());
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            BufferUploader.drawWithShader(buffer.buildOrThrow());
        } else {
            guiGraphics.blitSprite(id, x, y, width, height);
        }
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        long now = Util.getMillis();
        updateAnimationsAndLayout(now);
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.drawString(font, title, panelLeft + 11, panelTop + 9, 0xFF404040, false);
        int activeTotal = 0;
        for (SoulItem soulItem : equippedSouls) {
            if (!workingDisabled.contains(soulItem)) {
                activeTotal++;
            }
        }
        Component summary = Component.translatable("gui.fargo_soul.soul_menu.summary", activeTotal, equippedSouls.size());
        int summaryColor = activeTotal == equippedSouls.size() && !equippedSouls.isEmpty() ? 0xFF286B34 : 0xFF404040;
        guiGraphics.drawString(font, summary, panelLeft + panelWidth - 11 - font.width(summary), panelTop + 9, summaryColor, false);

        Component hint = Component.translatable("gui.fargo_soul.soul_menu.hint");
        int maxHintWidth = panelWidth - 94;
        String hintText = font.width(hint) > maxHintWidth ? font.plainSubstrByWidth(hint.getString(), maxHintWidth - 6) + "..." : hint.getString();
        guiGraphics.drawString(font, hintText, panelLeft + 11, panelTop + panelHeight - 20, 0xFF555555, false);

        int innerLeft = listLeft + 2;
        int innerRight = listRight - 2;
        int innerTop = listTop + 2;
        int innerBottom = listBottom - 2;
        int toggleX = innerRight - toggleWidth - 3;
        int branchX = toggleX - branchWidth - 3;

        SoulItem hoveredSoul = null;
        boolean hoveredToggle = false;

        if (layoutRows.isEmpty()) {
            Component empty = Component.translatable("gui.fargo_soul.soul_menu.empty");
            int textX = listLeft + (listRight - listLeft - font.width(empty)) / 2;
            int textY = listTop + (listBottom - listTop - font.lineHeight) / 2;
            guiGraphics.drawString(font, empty, textX, textY, 0xFFE0E0E0, true);
        } else {
            boolean inList = mouseX >= innerLeft && mouseX < innerRight && mouseY >= innerTop && mouseY < innerBottom;
            for (LayoutRow row : layoutRows) {
                int rowTop = Math.round(row.topY);
                int rowBottom = rowTop + rowHeight;
                if (row.clipBottom <= row.clipTop || rowBottom <= row.clipTop || rowTop >= row.clipBottom) {
                    continue;
                }
                guiGraphics.enableScissor(innerLeft, row.clipTop, innerRight, row.clipBottom);

                TreeNode node = row.node;
                boolean hasChildren = !node.children.isEmpty();
                boolean enabled = !workingDisabled.contains(node.item);
                boolean rowHovered = inList && mouseY >= Math.max(rowTop, row.clipTop) && mouseY < Math.min(rowBottom, row.clipBottom);

                int contentX = innerLeft + 5 + row.depth * indentStep;
                int plateLeft = contentX - 2;
                int plateRight = innerRight - 1;
                int btnTop = rowTop + (rowHeight - rowButtonHeight) / 2;
                int btnBottom = btnTop + rowButtonHeight;

                boolean toggleHovered = rowHovered && mouseX >= toggleX && mouseX < toggleX + toggleWidth && mouseY >= btnTop && mouseY < btnBottom;
                boolean branchHovered = hasChildren && rowHovered && mouseX >= branchX && mouseX < branchX + branchWidth && mouseY >= btnTop && mouseY < btnBottom;
                boolean bodyHovered = rowHovered && mouseX >= plateLeft && mouseX < (hasChildren ? branchX - 2 : toggleX - 2);

                drawShadowedTreeLines(guiGraphics, row, innerLeft, rowTop, rowBottom, contentX, enabled);

                ResourceLocation rowSprite = bodyHovered ? rowHoverSprite : (enabled ? rowBgSprite : rowDisabledSprite);
                blitTiledBackground(guiGraphics, rowSprite, plateLeft, rowTop + 1, plateRight - plateLeft, rowHeight - 2);

                int expandFlash = getClickFlashAlpha("row:" + BuiltInRegistries.ITEM.getKey(node.item), now);
                if (expandFlash > 0) {
                    guiGraphics.fill(plateLeft + 1, rowTop + 2, plateRight - 1, rowBottom - 2, (expandFlash << 24) | 0xFFFFFF);
                }

                if (hasChildren && expandProgress.getOrDefault(node.item, 0f) > 0.01f) {
                    int parentStemX = innerLeft + 9 + row.depth * indentStep;
                    guiGraphics.fill(parentStemX + 1, rowTop + 16, parentStemX + 2, rowBottom, 0xAA1E1E1E);
                    guiGraphics.fill(parentStemX, rowTop + 16, parentStemX + 1, rowBottom, 0xFFE6EAF2);
                }

                int iconSlotX = contentX;
                if (hasChildren) {
                    float progress = expandProgress.getOrDefault(node.item, 0f);
                    ResourceLocation arrowSprite;
                    if (progress < 0.25f) {
                        arrowSprite = bodyHovered ? arrowCollapsedHoverSprite : arrowCollapsedSprite;
                    } else if (progress > 0.75f) {
                        arrowSprite = bodyHovered ? arrowExpandedHoverSprite : arrowExpandedSprite;
                    } else {
                        arrowSprite = bodyHovered ? arrowMidHoverSprite : arrowMidSprite;
                    }
                    int arrowOffsetY = pressedTarget != null && pressedTarget.matches(node.item, PressPart.ROW_EXPAND) && bodyHovered ? 1 : 0;
                    guiGraphics.blitSprite(arrowSprite, contentX, rowTop + 7 + arrowOffsetY, 9, 9);
                    iconSlotX = contentX + 11;
                }

                guiGraphics.blitSprite(enabled ? slotSprite : slotDisabledSprite, iconSlotX, rowTop + 3, 18, 18);
                guiGraphics.renderItem(node.stack, iconSlotX + 1, rowTop + 4);
                if (!enabled) {
                    guiGraphics.fill(iconSlotX + 1, rowTop + 4, iconSlotX + 17, rowTop + 20, 0x661C1C1C);
                }

                int buttonsLeft = hasChildren ? branchX - 4 : toggleX - 4;
                String badge = "";
                int badgeWidth = 0;
                int badgeColor = 0xFFB0B0B0;
                if (hasChildren) {
                    int activeChildren = 0;
                    for (SoulItem descendant : node.descendants) {
                        if (!workingDisabled.contains(descendant)) {
                            activeChildren++;
                        }
                    }
                    badge = " (" + activeChildren + "/" + node.descendants.size() + ")";
                    badgeWidth = font.width(badge);
                    if (activeChildren == node.descendants.size()) {
                        badgeColor = 0xFF7BFF8A;
                    } else if (activeChildren > 0) {
                        badgeColor = 0xFFFFDF73;
                    }
                }

                int nameX = iconSlotX + 21;
                int maxNameWidth = Math.max(16, buttonsLeft - nameX - badgeWidth);
                String rawName = node.stack.getHoverName().getString();
                String drawName = font.width(rawName) > maxNameWidth ? font.plainSubstrByWidth(rawName, Math.max(0, maxNameWidth - 6)) + "..." : rawName;
                int nameColor = 0xFFA6A6A6;
                if (enabled) {
                    LyraRarity rarity = node.stack.get(LyraDataComponentRegister.RARITY);
                    nameColor = rarity != null ? (0xFF000000 | rarity.color()) : 0xFFFFFFFF;
                }
                guiGraphics.drawString(font, drawName, nameX, rowTop + 8, nameColor, true);
                if (hasChildren) {
                    guiGraphics.drawString(font, badge, nameX + font.width(drawName), rowTop + 8, badgeColor, true);
                }

                if (hasChildren) {
                    String branchKey = "branch:" + BuiltInRegistries.ITEM.getKey(node.item);
                    boolean branchPressed = (pressedTarget != null && pressedTarget.matches(node.item, PressPart.BRANCH) && branchHovered)
                            || isClickBounceActive(branchKey, now);
                    ResourceLocation bSprite = branchPressed ? branchPressedSprite : (branchHovered ? branchHoverSprite : branchSprite);
                    guiGraphics.blitSprite(bSprite, branchX, btnTop, branchWidth, rowButtonHeight);
                    int bFlash = getClickFlashAlpha(branchKey, now);
                    if (bFlash > 0) {
                        guiGraphics.fill(branchX + 1, btnTop + 1, branchX + branchWidth - 1, btnBottom - 1, (bFlash << 24) | 0xFFFFFF);
                    }
                    Component branchText = Component.translatable(isBranchAllEnabled(node) ? "gui.fargo_soul.soul_menu.branch_off" : "gui.fargo_soul.soul_menu.branch_on");
                    int bTextY = btnTop + 5 + (branchPressed ? 1 : 0);
                    guiGraphics.drawString(font, branchText, branchX + (branchWidth - font.width(branchText)) / 2, bTextY, branchHovered ? 0xFFFFFFA0 : 0xFFFFFFFF, true);
                }

                String toggleKey = "toggle:" + BuiltInRegistries.ITEM.getKey(node.item);
                boolean togglePressed = (pressedTarget != null && pressedTarget.matches(node.item, PressPart.TOGGLE) && toggleHovered)
                        || isClickBounceActive(toggleKey, now);
                ResourceLocation tSprite;
                if (enabled) {
                    tSprite = togglePressed ? toggleOnPressedSprite : (toggleHovered ? toggleOnHoverSprite : toggleOnSprite);
                } else {
                    tSprite = togglePressed ? toggleOffPressedSprite : (toggleHovered ? toggleOffHoverSprite : toggleOffSprite);
                }
                guiGraphics.blitSprite(tSprite, toggleX, btnTop, toggleWidth, rowButtonHeight);
                int tFlash = getClickFlashAlpha(toggleKey, now);
                if (tFlash > 0) {
                    guiGraphics.fill(toggleX + 1, btnTop + 1, toggleX + toggleWidth - 1, btnBottom - 1, (tFlash << 24) | 0xFFFFFF);
                }
                Component toggleText = Component.translatable(enabled ? "gui.fargo_soul.soul_menu.on" : "gui.fargo_soul.soul_menu.off");
                int tTextY = btnTop + 5 + (togglePressed ? 1 : 0);
                guiGraphics.drawString(font, toggleText, toggleX + (toggleWidth - font.width(toggleText)) / 2, tTextY, toggleHovered ? 0xFFFFFFA0 : 0xFFFFFFFF, true);

                if (toggleHovered) {
                    hoveredSoul = node.item;
                    hoveredToggle = true;
                } else if (bodyHovered) {
                    hoveredSoul = node.item;
                }

                guiGraphics.disableScissor();
            }
        }

        renderScrollbar(guiGraphics, mouseX, mouseY);

        if (hoveredSoul != null) {
            List<Component> tooltip = new ArrayList<>();
            ItemStack stack = hoveredSoul.getDefaultInstance();
            LyraRarity rarity = stack.get(LyraDataComponentRegister.RARITY);
            MutableComponent titleLine = stack.getHoverName().copy();
            if (rarity != null) {
                titleLine = titleLine.withColor(rarity.color());
            }
            tooltip.add(titleLine);
            boolean enabled = !workingDisabled.contains(hoveredSoul);
            tooltip.add(Component.translatable(enabled ? "gui.fargo_soul.soul_menu.enabled" : "gui.fargo_soul.soul_menu.disabled")
                    .withStyle(enabled ? ChatFormatting.GREEN : ChatFormatting.RED));
            if (hoveredToggle) {
                tooltip.add(Component.translatable("gui.fargo_soul.soul_menu.solo_hint").withStyle(ChatFormatting.YELLOW));
            } else {
                ResourceLocation location = BuiltInRegistries.ITEM.getKey(hoveredSoul);
                String key = "item." + location.getNamespace() + "." + location.getPath() + ".lyra_tooltip.";
                boolean hasDesc = false;
                for (int i = 1; I18n.exists(key + i); i++) {
                    hasDesc = true;
                    MutableComponent line = Component.translatable(key + i);
                    if (rarity != null) {
                        line = line.withColor(rarity.color());
                    }
                    tooltip.add(line);
                }
                if (!hasDesc) {
                    for (int i = -1; I18n.exists(key + i); i--) {
                        tooltip.add(Component.translatable(key + i).withStyle(ChatFormatting.GRAY));
                    }
                }
            }
            guiGraphics.renderComponentTooltip(font, tooltip, mouseX, mouseY);
        }
    }

    /** 绘制带阴影的树形连接线，先画阴影层再画主线层避免交叉遮挡。 */
    private void drawShadowedTreeLines(GuiGraphics guiGraphics, LayoutRow row, int innerLeft, int rowTop, int rowBottom, int contentX, boolean enabled) {
        int shadowColor = 0xAA1E1E1E;
        int lineColor = enabled ? 0xFFE6EAF2 : 0xFFA2A6AE;
        int centerY = rowTop + rowHeight / 2;

        for (int d = 1; d < row.depth; d++) {
            if (row.ancestorHasNext[d]) {
                int lineX = innerLeft + 9 + (d - 1) * indentStep;
                guiGraphics.fill(lineX + 1, rowTop, lineX + 2, rowBottom, shadowColor);
            }
        }
        if (row.depth > 0) {
            int lineX = innerLeft + 9 + (row.depth - 1) * indentStep;
            int branchEnd = contentX - 2;
            guiGraphics.fill(lineX + 1, rowTop, lineX + 2, row.lastChild ? centerY + 2 : rowBottom, shadowColor);
            guiGraphics.fill(lineX + 1, centerY + 1, branchEnd + 1, centerY + 2, shadowColor);
        }

        for (int d = 1; d < row.depth; d++) {
            if (row.ancestorHasNext[d]) {
                int lineX = innerLeft + 9 + (d - 1) * indentStep;
                guiGraphics.fill(lineX, rowTop, lineX + 1, rowBottom, 0xFFD4D8E0);
            }
        }
        if (row.depth > 0) {
            int lineX = innerLeft + 9 + (row.depth - 1) * indentStep;
            int branchEnd = contentX - 2;
            guiGraphics.fill(lineX, rowTop, lineX + 1, row.lastChild ? centerY + 1 : rowBottom, lineColor);
            guiGraphics.fill(lineX, centerY, branchEnd, centerY + 1, lineColor);
            if (enabled) {
                guiGraphics.fill(lineX, centerY, lineX + 1, centerY + 1, 0xFFFFFFFF);
            }
        }
    }

    private void renderScrollbar(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        double maxScroll = getMaxScroll();
        int thumbX = scrollTrackLeft + 1;
        int thumbW = scrollTrackWidth - 2;
        int trackTop = listTop + 2;
        int trackHeight = listBottom - listTop - 4;
        if (maxScroll > 0) {
            int thumbHeight = getScrollThumbHeight(trackHeight);
            int thumbTop = getScrollThumbTop(trackTop, trackHeight, thumbHeight, maxScroll);
            boolean thumbHovered = mouseX >= thumbX && mouseX < thumbX + thumbW && mouseY >= thumbTop && mouseY < thumbTop + thumbHeight;
            ResourceLocation thumbSprite = draggingScrollbar ? scrollThumbPressedSprite : (thumbHovered ? scrollThumbHoverSprite : scrollThumbSprite);
            guiGraphics.blitSprite(thumbSprite, thumbX, thumbTop, thumbW, thumbHeight);
            int gripY = thumbTop + thumbHeight / 2 - 3;
            for (int i = 0; i < 3; i++) {
                int y = gripY + i * 3;
                guiGraphics.fill(thumbX + 2, y, thumbX + thumbW - 2, y + 1, draggingScrollbar ? 0xFF555555 : 0xFF707070);
                guiGraphics.fill(thumbX + 2, y + 1, thumbX + thumbW - 2, y + 2, 0xFFEAEAEA);
            }
        } else {
            guiGraphics.blitSprite(buttonDisabledSprite, thumbX, trackTop, thumbW, trackHeight);
        }
    }

    private int getScrollThumbHeight(int trackHeight) {
        float viewHeight = listBottom - listTop - 4;
        return Math.max(22, Math.min(trackHeight, (int) (viewHeight * trackHeight / Math.max(1f, totalContentHeight))));
    }

    private int getScrollThumbTop(int trackTop, int trackHeight, int thumbHeight, double maxScroll) {
        return trackTop + (int) ((displayScrollOffset / maxScroll) * (trackHeight - thumbHeight));
    }

    private int getClickFlashAlpha(String key, long now) {
        Long start = clickAnims.get(key);
        if (start != null) {
            long elapsed = now - start;
            if (elapsed < 160L) {
                float ratio = 1f - elapsed / 160f;
                return (int) (ratio * ratio * 115f);
            }
            clickAnims.remove(key);
        }
        return 0;
    }

    private boolean isClickBounceActive(String key, long now) {
        Long start = clickAnims.get(key);
        return start != null && now - start < 55L;
    }

    private boolean isBranchAllEnabled(TreeNode node) {
        if (workingDisabled.contains(node.item)) {
            return false;
        }
        for (SoulItem descendant : node.descendants) {
            if (workingDisabled.contains(descendant)) {
                return false;
            }
        }
        return true;
    }

    private PressedTarget hitTestList(double mouseX, double mouseY) {
        int innerLeft = listLeft + 2;
        int innerRight = listRight - 2;
        int innerTop = listTop + 2;
        int innerBottom = listBottom - 2;
        if (mouseX >= innerLeft && mouseX < innerRight && mouseY >= innerTop && mouseY < innerBottom) {
            int toggleX = innerRight - toggleWidth - 3;
            int branchX = toggleX - branchWidth - 3;
            for (LayoutRow row : layoutRows) {
                int rowTop = Math.round(row.topY);
                int rowBottom = rowTop + rowHeight;
                if (row.clipBottom <= row.clipTop || mouseY < Math.max(rowTop, row.clipTop) || mouseY >= Math.min(rowBottom, row.clipBottom)) {
                    continue;
                }
                int contentX = innerLeft + 5 + row.depth * indentStep;
                int plateLeft = contentX - 2;
                int btnTop = rowTop + (rowHeight - rowButtonHeight) / 2;
                int btnBottom = btnTop + rowButtonHeight;
                boolean hasChildren = !row.node.children.isEmpty();

                if (mouseX >= toggleX && mouseX < toggleX + toggleWidth && mouseY >= btnTop && mouseY < btnBottom) {
                    return new PressedTarget(row.node, PressPart.TOGGLE);
                }
                if (hasChildren && mouseX >= branchX && mouseX < branchX + branchWidth && mouseY >= btnTop && mouseY < btnBottom) {
                    return new PressedTarget(row.node, PressPart.BRANCH);
                }
                if (hasChildren && mouseX >= plateLeft && mouseX < branchX - 2) {
                    return new PressedTarget(row.node, PressPart.ROW_EXPAND);
                }
                return null;
            }
        }
        return null;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }
        if (button == 0) {
            double maxScroll = getMaxScroll();
            if (maxScroll > 0 && mouseX >= scrollTrackLeft && mouseX < scrollTrackLeft + scrollTrackWidth && mouseY >= listTop && mouseY < listBottom) {
                int trackTop = listTop + 2;
                int trackHeight = listBottom - listTop - 4;
                int thumbHeight = getScrollThumbHeight(trackHeight);
                int thumbTop = getScrollThumbTop(trackTop, trackHeight, thumbHeight, maxScroll);
                draggingScrollbar = true;
                if (mouseY >= thumbTop && mouseY < thumbTop + thumbHeight) {
                    dragThumbGrabOffset = mouseY - thumbTop;
                } else {
                    dragThumbGrabOffset = thumbHeight / 2.0;
                    updateScrollFromMouse(mouseY);
                }
                return true;
            }
            PressedTarget hit = hitTestList(mouseX, mouseY);
            if (hit != null) {
                pressedTarget = hit;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (draggingScrollbar && button == 0) {
            updateScrollFromMouse(mouseY);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            draggingScrollbar = false;
            if (pressedTarget != null) {
                PressedTarget released = hitTestList(mouseX, mouseY);
                PressedTarget started = pressedTarget;
                pressedTarget = null;
                if (released != null && released.matches(started.node.item, started.part)) {
                    TreeNode node = started.node;
                    long now = Util.getMillis();
                    if (started.part == PressPart.TOGGLE) {
                        if (Screen.hasShiftDown()) {
                            workingDisabled.addAll(equippedSouls);
                            workingDisabled.remove(node.item);
                        } else if (!workingDisabled.remove(node.item)) {
                            workingDisabled.add(node.item);
                        }
                        clickAnims.put("toggle:" + BuiltInRegistries.ITEM.getKey(node.item), now);
                    } else if (started.part == PressPart.BRANCH) {
                        if (isBranchAllEnabled(node)) {
                            workingDisabled.add(node.item);
                            workingDisabled.addAll(node.descendants);
                        } else {
                            workingDisabled.remove(node.item);
                            workingDisabled.removeAll(node.descendants);
                        }
                        clickAnims.put("branch:" + BuiltInRegistries.ITEM.getKey(node.item), now);
                    } else if (started.part == PressPart.ROW_EXPAND) {
                        if (!expanded.remove(node.item)) {
                            expanded.add(node.item);
                        }
                        clickAnims.put("row:" + BuiltInRegistries.ITEM.getKey(node.item), now);
                    }
                    playClickSound();
                    return true;
                }
            }
        }
        boolean handled = super.mouseReleased(mouseX, mouseY, button);
        if (button == 0) {
            for (var child : children()) {
                if (child instanceof SoulButton soulButton) {
                    soulButton.holding = false;
                }
            }
        }
        return handled;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (mouseX >= listLeft && mouseX < scrollTrackLeft + scrollTrackWidth && mouseY >= listTop && mouseY < listBottom) {
            targetScrollOffset -= scrollY * rowHeight * 1.5;
            clampScroll(false);
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (FargoSoulKeyRegister.SoulMenuKey.matches(keyCode, scanCode)) {
            onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void removed() {
        if (!synced && minecraft != null && minecraft.player != null && minecraft.getConnection() != null) {
            synced = true;
            minecraft.player.getData(FargoSoulAttachmentRegister.SOUL_ITEM_DATA).applyDisabled(minecraft.player, workingDisabled);
            List<ResourceLocation> ids = new ArrayList<>();
            for (SoulItem soulItem : workingDisabled) {
                ids.add(BuiltInRegistries.ITEM.getKey(soulItem));
            }
            PacketDistributor.sendToServer(new SoulTogglePacket(ids));
        }
        super.removed();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void updateScrollFromMouse(double mouseY) {
        double maxScroll = getMaxScroll();
        if (maxScroll > 0) {
            int trackTop = listTop + 2;
            int trackHeight = listBottom - listTop - 4;
            int thumbHeight = getScrollThumbHeight(trackHeight);
            int movable = Math.max(1, trackHeight - thumbHeight);
            double ratio = Mth.clamp((mouseY - trackTop - dragThumbGrabOffset) / movable, 0, 1);
            targetScrollOffset = ratio * maxScroll;
            displayScrollOffset = targetScrollOffset;
        }
    }

    private void playClickSound() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    /** 带按压与点击反馈动画的原版风格按钮。 */
    private final class SoulButton extends Button {

        private boolean holding;
        private long lastClickMs = -1000L;

        private SoulButton(int x, int y, int width, int height, Component message, OnPress onPress) {
            super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (active && visible && button == 0 && clicked(mouseX, mouseY)) {
                holding = true;
                return true;
            }
            return false;
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            if (holding && button == 0) {
                holding = false;
                if (active && visible && clicked(mouseX, mouseY)) {
                    lastClickMs = Util.getMillis();
                    playDownSound(Minecraft.getInstance().getSoundManager());
                    onPress();
                    return true;
                }
            }
            return false;
        }

        @Override
        protected void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            long elapsed = Util.getMillis() - lastClickMs;
            boolean pressed = active && ((holding && isHovered) || (elapsed >= 0 && elapsed < 55L));
            ResourceLocation sprite;
            if (!active) {
                sprite = buttonDisabledSprite;
            } else if (pressed) {
                sprite = buttonPressedSprite;
            } else if (isHoveredOrFocused()) {
                sprite = buttonHoverSprite;
            } else {
                sprite = buttonSprite;
            }
            guiGraphics.blitSprite(sprite, getX(), getY(), getWidth(), getHeight());
            if (active && elapsed >= 0 && elapsed < 160L) {
                float ratio = 1f - elapsed / 160f;
                int alpha = (int) (ratio * ratio * 105f);
                guiGraphics.fill(getX() + 1, getY() + 1, getX() + getWidth() - 1, getY() + getHeight() - 1, (alpha << 24) | 0xFFFFFF);
            }
            int textColor = !active ? 0xFFA0A0A0 : (isHoveredOrFocused() ? 0xFFFFFFA0 : 0xFFFFFFFF);
            int textY = getY() + (getHeight() - 8) / 2 + (pressed ? 1 : 0);
            guiGraphics.drawCenteredString(font, getMessage(), getX() + getWidth() / 2, textY, textColor);
        }
    }

    private enum PressPart {
        TOGGLE,
        BRANCH,
        ROW_EXPAND
    }

    private record PressedTarget(TreeNode node, PressPart part) {
        private boolean matches(SoulItem item, PressPart targetPart) {
            return node.item == item && part == targetPart;
        }
    }

    private record TreeNode(SoulItem item, ItemStack stack, List<TreeNode> children, Set<SoulItem> descendants) {
    }

    private record LayoutRow(TreeNode node, int depth, float topY, int clipTop, int clipBottom, boolean lastChild, boolean[] ancestorHasNext) {
    }
}
