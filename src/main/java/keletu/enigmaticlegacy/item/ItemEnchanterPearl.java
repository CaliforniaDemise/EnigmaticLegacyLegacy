package keletu.enigmaticlegacy.item;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemEnchanterPearl extends ItemBaseFireProof {

	public ItemEnchanterPearl() {
		super("enchanter_pearl", EnumRarity.EPIC);
		this.maxStackSize = 1;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add("");
		if (GuiScreen.isShiftKeyDown()) {
			list.add(I18n.format("tooltip.enigmaticlegacy.enchanterPearl1"));
			list.add(I18n.format("tooltip.enigmaticlegacy.enchanterPearl2"));
			list.add(I18n.format("tooltip.enigmaticlegacy.enchanterPearl3"));
			list.add("");
			list.add(I18n.format("tooltip.enigmaticlegacy.enchanterPearl4"));
			list.add(I18n.format("tooltip.enigmaticlegacy.enchanterPearl5"));
			list.add("");
			list.add(I18n.format("tooltip.enigmaticlegacy.enchanterPearl6"));
		} else {
			list.add(I18n.format("tooltip.enigmaticlegacy.holdShift"));
		}
	}

}