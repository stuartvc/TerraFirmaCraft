/*
 * Licensed under the EUPL, Version 1.2.
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 */

package net.dries007.tfc.compat.jei.category;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.dries007.tfc.common.recipes.SimpleItemRecipe;
import org.jetbrains.annotations.Nullable;

public abstract class SimpleItemRecipeCategory<T extends SimpleItemRecipe> extends BaseRecipeCategory<T>
{
    public SimpleItemRecipeCategory(RecipeType<T> type, IGuiHelper helper, ItemStack icon)
    {
        super(type, helper, helper.createBlankDrawable(98, 26), icon);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses)
    {
        IRecipeSlotBuilder inputSlot = builder.addSlot(RecipeIngredientRole.INPUT, 6, 5);
        IRecipeSlotBuilder toolSlot = null;
        if (getToolTag() != null)
        {
            toolSlot = builder.addSlot(RecipeIngredientRole.CATALYST, 26, 5);
            toolSlot.setSlotName("tool");
        }
        IRecipeSlotBuilder outputSlot = builder.addSlot(RecipeIngredientRole.OUTPUT, 76, 5);

        inputSlot.addIngredients(recipe.getIngredient());
        if (toolSlot != null)
        {
            toolSlot.addIngredients(Ingredient.of(getToolTag()));
        }
        outputSlot.addItemStack(recipe.getResultItem());
    }

    @Override
    public void draw(T recipe, IRecipeSlotsView recipeSlots, PoseStack stack, double mouseX, double mouseY)
    {
        slot.draw(stack, 5, 4);
        if (recipeSlots.findSlotByName("tool").isPresent())
        {
            slot.draw(stack, 25, 4);
        }
        slot.draw(stack, 75, 4);
        arrow.draw(stack, 48, 5);
        arrowAnimated.draw(stack, 48, 5);
    }

    @Nullable
    protected abstract TagKey<Item> getToolTag();
}