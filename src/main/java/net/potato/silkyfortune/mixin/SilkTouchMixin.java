package net.potato.silkyfortune.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.SilkTouchEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SilkTouchEnchantment.class)
public class SilkTouchMixin {
	SilkTouchEnchantment that = (SilkTouchEnchantment)(Object)this;
	@Inject(at = @At("HEAD"), method = "canAccept", cancellable = true)
	private void canAccept(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(that != other);
	}
}
