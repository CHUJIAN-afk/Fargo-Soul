package First.fargo_soul;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Entity.AbstractArrow.AbstractArrowRegister;
import First.fargo_soul.Entity.AbstractArrow.NeedleProjectile.NeedleRenderer;
import First.fargo_soul.Entity.Projectile.IceSpike.IceSpikeRenderer;
import First.fargo_soul.Entity.Projectile.ProjectileRegister;
import First.fargo_soul.Item.ProjectileItem.ProjectileItems;
import First.fargo_soul.Item.Soul.SoulRenderer;
import First.fargo_soul.Item.Soul.Souls;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import static First.fargo_soul.Item.Soul.Souls.SoulItems;


@Mod(Fargo_soul.MODID)
public class Fargo_soul {
    public static final String MODID = "fargo_soul";

    public Fargo_soul(final IEventBus eventBus) {
        SoulItems.register(eventBus);
        Souls.CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(eventBus);
        EffectRegister.EFFECTS.register(eventBus);
        ProjectileRegister.SoulProjectile.register(eventBus);
        ProjectileItems.Projectiles.register(eventBus);
        AbstractArrowRegister.SoulAbstractArrow.register(eventBus);
        eventBus.addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        //全局魂石渲染
        SoulItems.getRegistry().get().forEach(soul -> CuriosRendererRegistry.register(soul, SoulRenderer::new));
        //渲染器
        EntityRenderers.register(ProjectileRegister.IceSpike.get(), IceSpikeRenderer::new);
        EntityRenderers.register(AbstractArrowRegister.Needle.get(), NeedleRenderer::new);
    }
}
