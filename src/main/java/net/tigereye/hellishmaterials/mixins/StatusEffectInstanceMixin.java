package net.tigereye.hellishmaterials.mixins;

import net.tigereye.hellishmaterials.registration.HMStatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.CompoundTag;
import net.tigereye.hellishmaterials.mechanics.BatetDeferment;
import net.tigereye.hellishmaterials.mob_effect.BloodDebtInstance;

@Mixin(StatusEffectInstance.class)
public class StatusEffectInstanceMixin implements BloodDebtInstance{
    private float bloodDebt = 0;

    @Inject(
        at = @At("HEAD"),
        method = "fromTag(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/entity/effect/StatusEffectInstance;",
        cancellable = true)
    private static void RedirectToBloodDebtFromTagMixin(CompoundTag tag, CallbackInfoReturnable<StatusEffectInstance> info){
        if(StatusEffect.byRawId(tag.getByte("Id")) == HMStatusEffects.HM_BLOODDEBT){
            info.setReturnValue(bloodDebtFromTag(tag));
        }
    }

    @ModifyVariable(
        at = @At("HEAD"),
        method = "toTag")
    public CompoundTag BloodMagicToTagMixin(CompoundTag tag) {
        if(((StatusEffectInstance)(Object)this).getEffectType() == HMStatusEffects.HM_BLOODDEBT){
            tag.putFloat("HM_BloodDebt", bloodDebt);
        }
        return tag;
    }

    @Override
    public float addDebt(float amount){
        bloodDebt += amount;
        return bloodDebt;
    }

    @Override
    public float removeDebt(float amount){
        if(bloodDebt < amount){
            bloodDebt = 0;
        }
        else{
            bloodDebt -= amount;
        }
        return bloodDebt;
    }

    @Override
    public float drawRepayment(){
        float payment;
        if(bloodDebt <= BatetDeferment.MINIMUM_REPAYMENT){
            payment = bloodDebt;
            bloodDebt = 0;
        }
        else{
            payment = bloodDebt * BatetDeferment.REPAYMENT_RATE;
            if(payment < BatetDeferment.MINIMUM_REPAYMENT){
                payment = BatetDeferment.MINIMUM_REPAYMENT;
            }
            bloodDebt -= payment;
        }
        return payment;
    }

    @Override
    public float getDebt(){
        return bloodDebt;
    }

    @Override
    public void setDebt(float debt) {
        this.bloodDebt = debt;
    }

    private static StatusEffectInstance bloodDebtFromTag(CompoundTag tag) {
        float debt = 0;
        if (tag.contains("HM_BloodDebt")) {
           debt = tag.getFloat("HM_BloodDebt");
        }  
        return HMStatusEffects.newBloodDebtStatusEffectInstance(debt);
    }

}
