package org.crumbleworks.forge.aTFC.worldgen;

import java.util.OptionalLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.IBiomeMagnifier;


//FIXME create two impls, one for serverside and one for clientside, so we don't have import issues when hooking them up
/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class atfcDimenstionType extends DimensionType {

    private static final Logger LOGGER = LoggerFactory.getLogger(atfcDimenstionType.class);

    private final IWorld world;
    
    protected atfcDimenstionType(OptionalLong fixedTime, boolean hasSkyLight,
            boolean hasCeiling, boolean ultrawarm, boolean natural,
            double coordinateScale, boolean hasDragonFight,
            boolean piglinSafe, boolean bedWorks, boolean respawnAnchorWorks,
            boolean hasRaids, int logicalHeight, IBiomeMagnifier magnifier,
            ResourceLocation infiniburn, ResourceLocation effects,
            float ambientLight, IWorld world) {
        super(fixedTime, hasSkyLight, hasCeiling, ultrawarm, natural, coordinateScale,
                hasDragonFight, piglinSafe, bedWorks, respawnAnchorWorks, hasRaids,
                logicalHeight, magnifier, infiniburn, effects, ambientLight);
        this.world = world;
    }
    
    @Override
    public float getCelestrialAngleByTime(long dayTime) {
        //TODO make sure dayTime goes up to 1 year of ticks
        // -> add some sort of buffer or similar to worlddata?
        // -> modify time command to take day-of-year too
        
        //NEW MATH
        //Tagesrechnung @365 Tage (172 = 21. Jul = 1.0f; 355 = 21.Dez = -1.0f) [offset von 11 Tagen kann in mathe ignoriert werden weil arbiträr]
        int daysInYear = 365; //input von aussen!
        int day = 1; //input von aussen! Minecraftday / 365
        double solsticeFactor = -Math.cos(day * (Math.PI / (daysInYear / 2)));
        
        //Latitude -40k southpole bis 40k northpole
        int southtonorth = 80000;
        int latitude = 1; //player coordinate input!
        double polarFactor = -Math.cos((latitude * (Math.PI / southtonorth)) + (Math.PI/2));
        
        
        
        ///////////////////////////////////////////////
        //OLD MATH
        double d0 = MathHelper.frac((double)this.fixedTime.orElse(dayTime) / 24000.0D - 0.25D);
        double d1 = 0.5D - Math.cos(d0 * Math.PI) / 2.0D;
        float angle = (float)(d0 * 2.0D + d1) / 3.0F;
        
        ///////////////////////////////////////////////
        //fancy java math...
        
        final int distTo90deg = 40000;
        
        double lonDeg = 0, latDeg = 0;
        int xPos = 0, zPos = 0;
        
        if(world instanceof ClientWorld) {
            PlayerEntity player = Minecraft.getInstance().player;
            BlockPos playerPos = player.getPosition();
            
            xPos = playerPos.getX();
            zPos = playerPos.getZ();
            
            lonDeg = Math.sin((double)((double)xPos/(double)(2*distTo90deg))*Math.PI)*(double)90;
            latDeg = Math.sin((double)((double)zPos/(double)(2*distTo90deg))*Math.PI)*(double)90;
        }
        
        int dayOfTheYear = (int)Math.floor(dayTime / 24000D) % 365;
        int hourOfTheDay = (int)Math.floor((dayTime % 24000) / 1000);
        
        return angle;
    }
}
