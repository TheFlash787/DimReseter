package DimReseter;

import java.io.File;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.storage.WorldProperties;

import com.flowpowered.math.vector.Vector3i;

public class Reset {

	DimReseter plugin;

	public Reset(DimReseter plugin) {

		this.plugin = plugin;
	}

	public void spawnPlatform(String dimname) {
		World world = Sponge.getServer().getWorld(dimname).get();
		WorldProperties prop = world.getWorldStorage().getWorldProperties();
		int x = prop.getSpawnPosition().getX();
		int y = prop.getSpawnPosition().getY();
		int z = prop.getSpawnPosition().getZ();

		
		
				//chunkLoader.getMainChunk().add(x, y, z);
		
		Vector3i[] plat = new Vector3i[9];
		for (int i = 0; i <= 9; i++) {

			plat[0] = new Vector3i(x, y - 2, z);
			plat[1] = new Vector3i(x, y - 2, z + 1);
			plat[7] = new Vector3i(x, y - 2, z - 1);
			plat[3] = new Vector3i(x - 1, y - 2, z + 1);
			plat[4] = new Vector3i(x - 1, y - 2, z);
			plat[6] = new Vector3i(x - 1, y - 2, z - 1);
			plat[5] = new Vector3i(x + 1, y - 2, z);
			plat[2] = new Vector3i(x + 1, y - 2, z + 1);
			plat[8] = new Vector3i(x + 1, y - 2, z - 1);
			
			
		}
		
		for (Vector3i v : plat) {
			
			
			world.getLocation(v).setBlockType(BlockTypes.BEDROCK,
					Cause.source(Sponge.getPluginManager().fromInstance(plugin).get()).build());
			
			
		}

	}
	

	public void deleteRegions(String dim) {
		String path2 = Sponge.getGame().getGameDirectory()+ File.separator + "world" + File.separator + dim + File.separator + "region";
		File location = new File(path2);

		
		if (location.isDirectory()) {
			String[] entries = location.list();
			for (String s : entries) {
				File currentFile = new File(location.getPath(), s);

				currentFile.delete();

			}

		}
	}

}
