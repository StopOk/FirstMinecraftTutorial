package net.potsok.tutorialmod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties STRAWBERRY = new FoodProperties.Builder().nutrition(2).fast().alwaysEat()
            .saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600), 1).build();
    public static final FoodProperties APPLE_PIE = new FoodProperties.Builder().nutrition(5).alwaysEat()
            .saturationMod(0.6f).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 4000), 1).build();
}
