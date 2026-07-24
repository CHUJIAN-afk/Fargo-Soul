package first.fargo_soul.common.attachment.soulInfoData.soulInfo;

import java.util.function.Supplier;


public record SoulInfoType<T extends AbstractSoulInfo>(Supplier<T> supplier) {
}
