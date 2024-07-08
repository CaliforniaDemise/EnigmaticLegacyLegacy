package keletu.enigmaticlegacy;

import keletu.enigmaticlegacy.entity.EntityItemIndestructible;
import keletu.enigmaticlegacy.entity.RenderEntityItemIndestructible;
import keletu.enigmaticlegacy.item.*;
import keletu.enigmaticlegacy.item.etherium.EtheriumArmor;
import keletu.enigmaticlegacy.key.EnderChestRingHandler;
import keletu.enigmaticlegacy.packet.PacketEnderRingKey;
import keletu.enigmaticlegacy.packet.PacketRecallParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(
        modid = EnigmaticLegacy.MODID,
        name = EnigmaticLegacy.MOD_NAME,
        version = EnigmaticLegacy.VERSION,
        dependencies = "required-after:baubles"
)
public class EnigmaticLegacy {

    public static final String MODID = "enigmaticlegacy";
    public static final String MOD_NAME = "Enigmatic Legacy²";
    public static final String VERSION = "0.0.1";

    public static ItemArmor.ArmorMaterial ARMOR_ETHERIUM = EnumHelper.addArmorMaterial("etherium", EnigmaticLegacy.MODID + ":etherium", 132, new int[] { 4, 7, 9, 4 }, 24, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 4F);
    public static Item cursedRing = new ItemCursedRing();
    public static ItemSoulCrystal soulCrystal = new ItemSoulCrystal();
    public static Item ironRing = new ItemIronRing();
    public static Item enderRing = new ItemEnderRing();
    public static Item gemRing = new ItemGemRing();
    public static Item magnetRing = new ItemMagnetRing();
    public static Item superMagnetRing = new ItemSuperMagnetRing();
    public static Item miningCharm = new ItemMiningCharm();
    public static Item monsterCharm = new ItemMonsterCharm();
    public static Item berserkEmblem = new ItemBerserkEmblem();

    //Material
    public static Item earthHeart = new ItemEarthHeart();
    public static Item etheriumOre = new ItemBase("etherium_ore", EnumRarity.RARE);
    public static Item etheriumIngot = new ItemBase("etherium_ingot", EnumRarity.RARE);
    public static Item enderRod = new ItemBase("ender_rod", EnumRarity.EPIC);
    public static Item astralDust = new ItemBase("astral_dust", EnumRarity.EPIC);
    //Armor
    public static Item etheriumHelm = new EtheriumArmor(EntityEquipmentSlot.HEAD, 1, "etherium_helm");
    public static Item etheriumChest = new EtheriumArmor(EntityEquipmentSlot.CHEST, 1, "etherium_chest");
    public static Item etheriumLegs = new EtheriumArmor(EntityEquipmentSlot.LEGS, 2, "etherium_legs");
    public static Item etheriumBoots = new EtheriumArmor(EntityEquipmentSlot.FEET, 1, "etherium_boots");
    public static SimpleNetworkWrapper packetInstance;
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ELConfigs.onConfig(event);

        packetInstance = NetworkRegistry.INSTANCE.newSimpleChannel("EnigmaticChannel");
        packetInstance.registerMessage(PacketRecallParticles.Handler.class, PacketRecallParticles.class, 0, Side.CLIENT);
        packetInstance.registerMessage(PacketEnderRingKey.Handler.class, PacketEnderRingKey.class, 1, Side.SERVER);

        if (event.getSide().isClient())
            EnderChestRingHandler.registerKeybinds();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        EntityRegistry.registerModEntity(new ResourceLocation(MODID + ":" + "permanent_item"), EntityItemIndestructible.class, "permanent_item", 0, MODID, 80, 3, true);
        GameRegistry.addSmelting(etheriumOre, new ItemStack(etheriumIngot, 1), 0.5f);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {

        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().register(cursedRing);
            event.getRegistry().register(soulCrystal);
            event.getRegistry().register(ironRing);
            event.getRegistry().register(gemRing);
            event.getRegistry().register(enderRing);
            event.getRegistry().register(magnetRing);
            event.getRegistry().register(superMagnetRing);
            event.getRegistry().register(miningCharm);
            event.getRegistry().register(monsterCharm);
            event.getRegistry().register(berserkEmblem);
            event.getRegistry().register(earthHeart);
            event.getRegistry().register(etheriumOre);
            event.getRegistry().register(etheriumIngot);
            event.getRegistry().register(enderRod);
            event.getRegistry().register(astralDust);
            event.getRegistry().register(etheriumHelm);
            event.getRegistry().register(etheriumChest);
            event.getRegistry().register(etheriumLegs);
            event.getRegistry().register(etheriumBoots);
        }

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void modelRegistryEvent(ModelRegistryEvent event) {
            ModelLoader.setCustomModelResourceLocation(cursedRing, 0, new ModelResourceLocation(cursedRing.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(soulCrystal, 0, new ModelResourceLocation(soulCrystal.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(ironRing, 0, new ModelResourceLocation(ironRing.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(gemRing, 0, new ModelResourceLocation(gemRing.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(enderRing, 0, new ModelResourceLocation(enderRing.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(magnetRing, 0, new ModelResourceLocation(magnetRing.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(superMagnetRing, 0, new ModelResourceLocation(superMagnetRing.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(miningCharm, 0, new ModelResourceLocation(miningCharm.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(monsterCharm, 0, new ModelResourceLocation(monsterCharm.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(berserkEmblem, 0, new ModelResourceLocation(berserkEmblem.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(earthHeart, 0, new ModelResourceLocation(earthHeart.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(etheriumOre, 0, new ModelResourceLocation(etheriumOre.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(etheriumIngot, 0, new ModelResourceLocation(etheriumIngot.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(enderRod, 0, new ModelResourceLocation(enderRod.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(astralDust, 0, new ModelResourceLocation(astralDust.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(etheriumHelm, 0, new ModelResourceLocation(etheriumHelm.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(etheriumChest, 0, new ModelResourceLocation(etheriumChest.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(etheriumLegs, 0, new ModelResourceLocation(etheriumLegs.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(etheriumBoots, 0, new ModelResourceLocation(etheriumBoots.getRegistryName(), "inventory"));

            RenderingRegistry.registerEntityRenderingHandler(EntityItemIndestructible.class, manager -> new RenderEntityItemIndestructible(manager, Minecraft.getMinecraft().getRenderItem()));
        }
    }

}
