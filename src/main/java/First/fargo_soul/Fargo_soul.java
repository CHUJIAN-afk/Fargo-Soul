package First.fargo_soul;

import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.Compact.Create.CreateCompact;
import First.fargo_soul.Compact.Create.CreateSoulsRegister;
import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Entity.EntityRegister;
import First.fargo_soul.Item.CreativeModeTabRegister;
import First.fargo_soul.Item.ModItemRegister;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;


@Mod(Fargo_soul.MODID)
public class Fargo_soul {
    public static final String MODID = "fargo_soul";

    public Fargo_soul(final IEventBus eventBus) {
        ModItemRegister.register(eventBus);//物品注册
        CreativeModeTabRegister.register(eventBus);//创造模式物品栏
        EntityRegister.register(eventBus);//实体注册
        EffectRegister.register(eventBus);//药水效果注册
        AttributeRegister.register(eventBus);//属性注册
        ModCompact(eventBus);//模组联动
    }

    private static void ModCompact(IEventBus eventBus) {
        //机械动力联动
        if (CreateCompact.isLoadCreate()) {
            CreateSoulsRegister.register(eventBus);
        }
    }


}
