package keletu.enigmaticlegacy.effect;

import keletu.enigmaticlegacy.EnigmaticLegacy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GrowingHungerEffect extends Potion {

    public GrowingHungerEffect() {
        super(false, 0xBD1BE5);
        this.setPotionName("potion.growing_hunger");
        this.setRegistryName(new ResourceLocation(EnigmaticLegacy.MODID, "growing_hunger"));
        this.registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "c281d54f-3277-4e4c-899e-c27f4f697b24", 0.1, 2);
        this.setIconIndex(4, 1);
    }

    @Override
    public void performEffect(EntityLivingBase living, int amplifier) {
        if (living instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) living;
            player.addExhaustion((float) (0.5 * (1 + amplifier)) / 4);
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex() {
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("enigmaticlegacy", "textures/misc/potions.png"));
        return super.getStatusIconIndex();
    }
}