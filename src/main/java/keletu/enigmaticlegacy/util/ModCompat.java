package keletu.enigmaticlegacy.util;

import net.minecraftforge.fml.common.Loader;

public class ModCompat {
    public static final boolean COMPAT_TRINKETS = Loader.isModLoaded("xat");
    public static final boolean COMPAT_TOMBMANYGRAVES = Loader.isModLoaded("tombmanygraves2api");
}
