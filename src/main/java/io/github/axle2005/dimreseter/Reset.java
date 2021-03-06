package io.github.axle2005.dimreseter;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.storage.WorldProperties;

import com.flowpowered.math.vector.Vector3i;

import me.ryanhamshire.griefprevention.api.claim.Claim;
import me.ryanhamshire.griefprevention.api.claim.ClaimManager;

public class Reset {

	DimReseter plugin;
	Boolean gpPresent;
	Map<UUID, Claim> claimData = new ConcurrentHashMap<>();
	
	public Reset(DimReseter plugin) {

		this.plugin = plugin;
	}
	
	public void spawnPlatform(String dimname) {
		if(Sponge.getServer().getWorld(dimname).isPresent())
		{
			World world = Sponge.getServer().getWorld(dimname).get();
			WorldProperties prop = world.getWorldStorage().getWorldProperties();
			int x = prop.getSpawnPosition().getX();
			int y = prop.getSpawnPosition().getY();
			int z = prop.getSpawnPosition().getZ();

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


	}

	public void deleteRegions(String dim) {
		String path = Sponge.getGame().getGameDirectory() + File.separator + "world" + File.separator + dim;
		String path2 = path + File.separator + "data";
				
		File location2 = new File(path2);
		if (location2.isDirectory()) {
			String[] entries = location2.list();
			for (String s : entries) {
				File currentFile = new File(location2.getPath(), s);
				if (currentFile.getName().equals("villages_end.dat")
						|| currentFile.getName().equals("villages_nether.dat")
						|| currentFile.getName().equals("EndCity.dat")) {
					currentFile.delete();
				}

			}

		}
		String path3 = path + File.separator + "region";
		resetUtil(path3);

	}
	public void clearClaims(String dim, Boolean gpPresent)
	{
		if(gpPresent)
		{
			
			Optional<World> world = Sponge.getServer().getWorld(dim);
			if(world.isPresent())
			{
				World w = world.get();
				ClaimManager cm = plugin.getGPApi().getClaimManager(w);	
				List<Claim> claims = cm.getWorldClaims();
				for(Claim c : claims)
				{
					
					if(c.isBasicClaim())
					{
						
						claimData.put(c.getUniqueId(), c);
					}
					
				}
				for(Claim c : claimData.values()){
					cm.deleteClaim(c, plugin.getCause());
					
					
				}
			}
			plugin.getLogger().info("Deleted Claims in Dim: "+dim);
		}
	}

	private void resetUtil(String path) {
		File location = new File(path);

		if (location.isDirectory()) {
			String[] entries = location.list();
			for (String s : entries) {
				File currentFile = new File(location.getPath(), s);

				currentFile.delete();

			}

		}
	}

}