package First.fargo_soul;

import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.DataComponent.DataComponentsRegister;
import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Entity.EntityRegister;
import First.fargo_soul.Item.CreativeModeTabRegister;
import First.fargo_soul.Item.ModItemRegister;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


@Mod(Fargo_soul.MODID)
public class Fargo_soul {

    public static final String MODID = "fargo_soul";
    public static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    public static final Logger logger = LoggerFactory.getLogger(MODID);

    public Fargo_soul(IEventBus eventBus) {
        ModItemRegister.register(eventBus);//物品注册
        CreativeModeTabRegister.register(eventBus);//创造模式物品栏
        EntityRegister.register(eventBus);//实体注册
        EffectRegister.register(eventBus);//药水效果注册
        AttributeRegister.register(eventBus);//属性注册
        AttachmentRegister.register(eventBus);//数据组件注册
        DataComponentsRegister.register(eventBus);//物品组件注册
    }

}
