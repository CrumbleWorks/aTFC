package org.crumbleworks.forge.aTFC.content.items;

import org.crumbleworks.forge.aTFC.content.gamelogic.drying.bricks.DryableBrick;

import net.minecraft.util.ResourceLocation;

import net.minecraft.item.Item.Properties;
import org.crumbleworks.forge.aTFC.content.items.Bulky.Bulk;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class CurableBrickItem extends aTFCBaseItem
        implements DryableBrick {

    private final float hoursToDry;
    private final ResourceLocation uncuredBrickModel;
    private final ResourceLocation curedBrickModel;
    private final float dryingThreshold;
    private final float renderYOffset;

    public CurableBrickItem(Bulk bulk, int weight, Properties properties,
            float hoursToDry, ResourceLocation uncuredBrickModel,
            ResourceLocation curedBrickModel, float dryingThreshold,
            float renderYOffset) {
        super(bulk, weight, properties);
        this.hoursToDry = hoursToDry;
        this.uncuredBrickModel = uncuredBrickModel;
        this.curedBrickModel = curedBrickModel;
        this.dryingThreshold = dryingThreshold;
        this.renderYOffset = renderYOffset;
    }

    @Override
    public float hoursUntilFullyDried() {
        return hoursToDry;
    }

    public ResourceLocation uncuredBrickModel() {
        return uncuredBrickModel;
    }

    public ResourceLocation curedBrickModel() {
        return curedBrickModel;
    }

    @Override
    public float dryingThreshold() {
        return dryingThreshold;
    }

    @Override
    public float renderYOffset() {
        return renderYOffset;
    }
}
