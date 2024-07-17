package keletu.enigmaticlegacy;

import keletu.enigmaticlegacy.api.ExtendedBaubleType;
import keletu.enigmaticlegacy.api.IExtendedBauble;
import keletu.enigmaticlegacy.api.cap.ExtendedBaubleItem;
import keletu.enigmaticlegacy.api.cap.ExtendedBaublesCapabilities;
import keletu.enigmaticlegacy.api.cap.ExtendedBaublesContainer;
import keletu.enigmaticlegacy.api.cap.IExtendedBaublesItemHandler;
import keletu.enigmaticlegacy.entity.EntityItemIndestructible;
import keletu.enigmaticlegacy.entity.EntityItemSoulCrystal;
import keletu.enigmaticlegacy.entity.RenderEntitySoulCrystal;
import keletu.enigmaticlegacy.event.EventHandlerEntity;
import keletu.enigmaticlegacy.event.EventHandlerItem;
import keletu.enigmaticlegacy.item.*;
import keletu.enigmaticlegacy.item.etherium.*;
import keletu.enigmaticlegacy.key.EnderChestRingHandler;
import keletu.enigmaticlegacy.packet.*;
import keletu.enigmaticlegacy.proxy.CommonProxy;
import keletu.enigmaticlegacy.util.LoggerWrapper;
import keletu.enigmaticlegacy.util.LootHandler;
import keletu.enigmaticlegacy.util.ModCompat;
import keletu.enigmaticlegacy.util.compat.CompatTrinketEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
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
import net.minecraftforge.oredict.OreDictionary;

@Mod(
        modid = EnigmaticLegacy.MODID,
        name = EnigmaticLegacy.MOD_NAME,
        version = EnigmaticLegacy.VERSION,
        dependencies = "required-after:baubles;after:patchouli;after:grimoire"
)
public class EnigmaticLegacy {

    public static final String MODID = "enigmaticlegacy";
    public static final String MOD_NAME = "Enigmatic Legacy²";
    public static final String VERSION = "0.1.1";

    @SidedProxy(clientSide = "keletu.enigmaticlegacy.proxy.ClientProxy", serverSide = "keletu.enigmaticlegacy.proxy.CommonProxy")
    public static CommonProxy proxy;
    public static final LoggerWrapper logger = new LoggerWrapper("Enigmatic Legacy");
    public static ItemArmor.ArmorMaterial ARMOR_ETHERIUM = EnumHelper.addArmorMaterial("etherium", EnigmaticLegacy.MODID + ":etherium", 132, new int[] { 4, 7, 9, 4 }, 24, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 4F);
    public static Item.ToolMaterial ETHERIUM = EnumHelper.addToolMaterial("etherium", 5, 3000, 8.0F, 5.0F, 32);


    public static CreativeTabs tabEnigmaticLegacy = new CreativeTabs("tabEnigmaticLegacy") {
        @SideOnly(Side.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(theAcknowledgment);
        }
    };

    public static Item cursedRing = new ItemCursedRing();
    //public static Item cursedStone = new ItemCursedStone();
    public static ItemSoulCrystal soulCrystal = new ItemSoulCrystal();
    public static Item ironRing = new ItemIronRing();
    public static Item enderRing = new ItemEnderRing();
    public static Item gemRing = new ItemGemRing();
    public static Item magnetRing = new ItemMagnetRing();
    public static Item superMagnetRing = new ItemSuperMagnetRing();
    public static Item miningCharm = new ItemMiningCharm();
    public static Item monsterCharm = new ItemMonsterCharm();
    public static Item berserkEmblem = new ItemBerserkEmblem();
    public static Item megaSponge = new ItemMegasponge();
    public static Item enchanterPearl = new ItemEnchanterPearl();
    public static Item xpScroll = new ItemExperienceScroll();
    public static Item cursedScroll = new ItemCursedScroll();
    public static Item animalGuide = new ItemAnimalGuide();

    //Material
    public static Item earthHeart = new ItemEarthHeart();
    public static Item thiccScroll = new ItemBase("thicc_scroll", EnumRarity.COMMON);
    public static Item etheriumOre = new ItemBaseFireProof("etherium_ore", EnumRarity.RARE);
    public static Item etheriumIngot = new ItemBaseFireProof("etherium_ingot", EnumRarity.RARE);
    public static Item enderRod = new ItemBase("ender_rod", EnumRarity.EPIC);
    public static Item astralDust = new ItemBaseFireProof("astral_dust", EnumRarity.EPIC);
    public static Item evilEssence = new ItemBaseFireProof("evil_essence", EnumRarity.EPIC);
    public static Item ingotWitherite = new ItemIngotWitherite();
    public static Item twistedCore = new ItemBase("twisted_core", EnumRarity.EPIC).setMaxStackSize(1);

    //Armor
    public static Item etheriumHelm = new EtheriumArmor(EntityEquipmentSlot.HEAD, 1, "etherium_helm");
    public static Item etheriumChest = new EtheriumArmor(EntityEquipmentSlot.CHEST, 1, "etherium_chest");
    public static Item etheriumLegs = new EtheriumArmor(EntityEquipmentSlot.LEGS, 2, "etherium_legs");
    public static Item etheriumBoots = new EtheriumArmor(EntityEquipmentSlot.FEET, 1, "etherium_boots");

    //Tool
    public static Item etheriumSword = new ItemEtheriumSword();
    public static Item etheriumAxe = new ItemEtheriumAxe();
    public static Item etheriumPickaxe = new ItemEtheriumPick();
    public static Item etheriumSpade = new ItemEtheriumSpade();
    public static Item astralBreaker = new ItemAstralBreaker();
    public static Item theAcknowledgment = new ItemTheAcknowledgment();
    public static Item theTwist = new ItemTheTwist();
    public static Item infinimeal = new ItemInfinimeal();

    public static SimpleNetworkWrapper packetInstance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ELConfigs.onConfig(event);

        CapabilityManager.INSTANCE.register(IExtendedBaublesItemHandler.class,
                new ExtendedBaublesCapabilities.CapabilityBaubles<IExtendedBaublesItemHandler>(), ExtendedBaublesContainer.class);

        CapabilityManager.INSTANCE
                .register(IExtendedBauble.class, new ExtendedBaublesCapabilities.CapabilityItemBaubleStorage(), () -> new ExtendedBaubleItem(ExtendedBaubleType.SCROLL));


        packetInstance = NetworkRegistry.INSTANCE.newSimpleChannel("EnigmaticChannel");
        packetInstance.registerMessage(PacketRecallParticles.Handler.class, PacketRecallParticles.class, 0, Side.CLIENT);
        packetInstance.registerMessage(PacketEnderRingKey.Handler.class, PacketEnderRingKey.class, 1, Side.SERVER);
        packetInstance.registerMessage(PacketPortalParticles.Handler.class, PacketPortalParticles.class, 2, Side.CLIENT);
        packetInstance.registerMessage(PacketEnchantedWithPearl.Handler.class, PacketEnchantedWithPearl.class, 3, Side.SERVER);
        packetInstance.registerMessage(PacketOpenExtendedBaublesInventory.class, PacketOpenExtendedBaublesInventory.class, 4, Side.SERVER);
        packetInstance.registerMessage(PacketSyncExtended.Handler.class, PacketSyncExtended.class, 5, Side.CLIENT);

        MinecraftForge.EVENT_BUS.register(new EventHandlerEntity());
        MinecraftForge.EVENT_BUS.register(new EventHandlerItem());

        if (event.getSide().isClient())
            EnderChestRingHandler.registerKeybinds();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(MODID, proxy);

        EntityRegistry.registerModEntity(new ResourceLocation(MODID + ":" + "soul_crystal"), EntityItemSoulCrystal.class, "soul_crystal", 0, MODID, 80, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(MODID + ":" + "permanent_item"), EntityItemIndestructible.class, "permanent_item", 1, MODID, 80, 3, true);
        MinecraftForge.EVENT_BUS.register(new LootHandler());

        GameRegistry.addSmelting(etheriumOre, new ItemStack(etheriumIngot, 1), 8.0f);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if(ModCompat.COMPAT_TRINKETS)
            MinecraftForge.EVENT_BUS.register(new CompatTrinketEvent());
    }

    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {

        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().register(theAcknowledgment);
            event.getRegistry().register(ironRing);
            event.getRegistry().register(gemRing);
            event.getRegistry().register(enderRing);
            event.getRegistry().register(magnetRing);
            event.getRegistry().register(superMagnetRing);
            event.getRegistry().register(cursedRing);
            //event.getRegistry().register(cursedStone);
            event.getRegistry().register(soulCrystal);
            event.getRegistry().register(miningCharm);
            event.getRegistry().register(monsterCharm);
            event.getRegistry().register(berserkEmblem);
            event.getRegistry().register(megaSponge);
            event.getRegistry().register(earthHeart);
            event.getRegistry().register(infinimeal);
            event.getRegistry().register(twistedCore);
            event.getRegistry().register(theTwist);
            event.getRegistry().register(evilEssence);
            event.getRegistry().register(ingotWitherite);
            event.getRegistry().register(enchanterPearl);
            event.getRegistry().register(etheriumOre);
            event.getRegistry().register(etheriumIngot);
            event.getRegistry().register(enderRod);
            event.getRegistry().register(astralDust);
            event.getRegistry().register(thiccScroll);
            event.getRegistry().register(xpScroll);
            event.getRegistry().register(cursedScroll);
            event.getRegistry().register(animalGuide);
            event.getRegistry().register(etheriumHelm);
            event.getRegistry().register(etheriumChest);
            event.getRegistry().register(etheriumLegs);
            event.getRegistry().register(etheriumBoots);
            event.getRegistry().register(etheriumSword);
            event.getRegistry().register(etheriumAxe);
            event.getRegistry().register(etheriumPickaxe);
            event.getRegistry().register(etheriumSpade);
            event.getRegistry().register(astralBreaker);
        }

        @SubscribeEvent
        public static void OreRegister(RegistryEvent.Register<Enchantment> event)
        {
            OreDictionary.registerOre("coreEarth", new ItemStack(earthHeart, 1, 0));
            OreDictionary.registerOre("coreEarth", new ItemStack(earthHeart, 1, 1));
            OreDictionary.registerOre("ingotNetherite", new ItemStack(ingotWitherite, 1, 0));
        }

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void modelRegistryEvent(ModelRegistryEvent event) {
            ModelLoader.setCustomModelResourceLocation(cursedRing, 0, new ModelResourceLocation(cursedRing.getRegistryName(), "inventory"));
            //ModelLoader.setCustomModelResourceLocation(cursedStone, 0, new ModelResourceLocation(cursedStone.getRegistryName(), "inventory"));
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
            ModelLoader.setCustomModelResourceLocation(earthHeart, 1, new ModelResourceLocation(earthHeart.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(etheriumOre, 0, new ModelResourceLocation(etheriumOre.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(etheriumIngot, 0, new ModelResourceLocation(etheriumIngot.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(enderRod, 0, new ModelResourceLocation(enderRod.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(astralDust, 0, new ModelResourceLocation(astralDust.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(etheriumHelm, 0, new ModelResourceLocation(etheriumHelm.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(etheriumChest, 0, new ModelResourceLocation(etheriumChest.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(etheriumLegs, 0, new ModelResourceLocation(etheriumLegs.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(etheriumBoots, 0, new ModelResourceLocation(etheriumBoots.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(etheriumSword, 0, new ModelResourceLocation(etheriumSword.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(etheriumAxe, 0, new ModelResourceLocation(etheriumAxe.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(etheriumPickaxe, 0, new ModelResourceLocation(etheriumPickaxe.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(etheriumSpade, 0, new ModelResourceLocation(etheriumSpade.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(astralBreaker, 0, new ModelResourceLocation(astralBreaker.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(megaSponge, 0, new ModelResourceLocation(megaSponge.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(twistedCore, 0, new ModelResourceLocation(twistedCore.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(theAcknowledgment, 0, new ModelResourceLocation(theAcknowledgment.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(theTwist, 0, new ModelResourceLocation(theTwist.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(evilEssence, 0, new ModelResourceLocation(evilEssence.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(ingotWitherite, 0, new ModelResourceLocation(ingotWitherite.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(enchanterPearl, 0, new ModelResourceLocation(enchanterPearl.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(infinimeal, 0, new ModelResourceLocation(infinimeal.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(xpScroll, 0, new ModelResourceLocation(xpScroll.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(cursedScroll, 0, new ModelResourceLocation(cursedScroll.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(animalGuide, 0, new ModelResourceLocation(animalGuide.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(thiccScroll, 0, new ModelResourceLocation(thiccScroll.getRegistryName(), "inventory"));

            RenderingRegistry.registerEntityRenderingHandler(EntityItemSoulCrystal.class, manager -> new RenderEntitySoulCrystal(manager, Minecraft.getMinecraft().getRenderItem()));
        }
    }

}
