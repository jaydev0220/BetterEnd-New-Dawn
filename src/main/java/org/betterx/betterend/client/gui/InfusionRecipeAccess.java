package org.betterx.betterend.client.gui;

import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.recipe.builders.InfusionRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import java.util.Collection;
import java.util.List;

@EventBusSubscriber(modid = BetterEnd.MOD_ID)
public final class InfusionRecipeAccess {
    private static volatile List<RecipeHolder<InfusionRecipe>> recipes = List.of();
    private InfusionRecipeAccess() {}

    @SubscribeEvent
    public static void syncRecipes(OnDatapackSyncEvent event) {
        event.sendRecipes(InfusionRecipe.TYPE);
    }

    @SubscribeEvent
    public static void receiveRecipes(RecipesReceivedEvent event) {
        recipes = List.copyOf(event.getRecipeMap().byType(InfusionRecipe.TYPE));
    }

    public static Collection<RecipeHolder<InfusionRecipe>> all(Level level) {
        return recipes;
    }
}
