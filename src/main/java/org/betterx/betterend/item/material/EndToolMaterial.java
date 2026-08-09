package org.betterx.betterend.item.material;

import org.betterx.betterend.registry.EndTags;
import org.betterx.wover.tag.api.TagManager;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;

public enum EndToolMaterial {
    THALLASIUM(ToolMaterial.IRON.incorrectBlocksForDrops(), 2, 320, 7.0F, 1.5F, 12, "ingots/thallasium",
            new SpearTuning(0.95F, 0.95F, 0.6F, 2.5F, 11.0F, 6.75F, 5.1F, 11.25F, 4.6F)),
    TERMINITE(ToolMaterial.DIAMOND.incorrectBlocksForDrops(), 3, 1230, 8.5F, 3.0F, 14, "ingots/terminite",
            new SpearTuning(1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F)),
    AETERNIUM(EndTags.INCORRECT_FOR_AETERNIUM_TOOL, 5, 2196, 10.0F, 4.5F, 18, "ingots/aeternium",
            new SpearTuning(1.15F, 1.2F, 0.4F, 2.5F, 9.0F, 5.5F, 5.1F, 8.75F, 4.6F));

    private final ToolMaterial toolMaterial;
    private final int level;
    public final TagKey<Block> incorrectBlocksForDrops;
    private final SpearTuning spearTuning;

    EndToolMaterial(
            TagKey<Block> incorrectBlocksForDrops,
            int level,
            int uses,
            float speed,
            float damage,
            int enchantibility,
            String repairTag,
            SpearTuning spearTuning
    ) {
        this.incorrectBlocksForDrops = incorrectBlocksForDrops;
        this.level = level;
        this.spearTuning = spearTuning;
        this.toolMaterial = new ToolMaterial(
                incorrectBlocksForDrops,
                uses,
                speed,
                damage,
                enchantibility,
                TagManager.ITEMS.makeCommonTag(repairTag)
        );
    }

    public int getLevel() {
        return level;
    }

    public ToolMaterial toolMaterial() {
        return toolMaterial;
    }

    public SpearTuning spearTuning() {
        return spearTuning;
    }

    public record SpearTuning(
            float attackDuration,
            float damageMultiplier,
            float delay,
            float dismountTime,
            float dismountThreshold,
            float knockbackTime,
            float knockbackThreshold,
            float damageTime,
            float damageThreshold
    ) {
    }
}
