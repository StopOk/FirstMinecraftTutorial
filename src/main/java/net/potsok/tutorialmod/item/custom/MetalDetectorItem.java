package net.potsok.tutorialmod.item.custom;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.potsok.tutorialmod.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MetalDetectorItem extends Item {
    public MetalDetectorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(!pContext.getLevel().isClientSide()) {
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            boolean foundBlock = false;

            player.sendSystemMessage(Component.literal("Scanning!"));

            for (int z = positionClicked.getZ() - 2; z <= positionClicked.getZ() + 2; z++ ) {
                BlockPos posNorth = positionClicked.north(-positionClicked.getZ() + z);
                for (int x = posNorth.getX() - 2; x <= posNorth.getX() + 2; x++) {
                    BlockPos posWest = posNorth.west(-posNorth.getX() + x);
                    for (int i = 0; i <= posWest.getY() + 64; i++) {
                        BlockState state = pContext.getLevel().getBlockState(posWest.below(i));

                        if (isValuableBlock(state)) {
                            outputValuableCoordinates(posWest.below(i), player, state.getBlock());
                            foundBlock = true;

                            break;
                        }
                    }
                }
            }

            if(!foundBlock) {
                player.sendSystemMessage(Component.literal("No Valuables Found!"));
            }

        }

        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),
                player -> player.broadcastBreakEvent(player.getUsedItemHand()));

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.tutorialmod.metal_detector.tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    private void outputValuableCoordinates(BlockPos blockPos, Player player, Block block) {
        player.sendSystemMessage(Component.literal( "Found " + I18n.get(block.getDescriptionId()) + " at (" +
                blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + ")"));
    }

    private boolean isValuableBlock(BlockState state) {
        return state.is(ModTags.Blocks.METAL_DETECTOR_VALUABLES);
    }
}
