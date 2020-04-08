package info.beastsoftware.beastcore.beastutils.utils;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ILocationUtil {

    public static List<Chunk> getChunksAroundLocation(Location location, int radius) {


        List<Chunk> chunks = new ArrayList<>();

        if (radius <= 0) {
            chunks.add(location.getChunk());
            return chunks;
        }


        Chunk locChunk = location.getChunk();
        chunks.add(locChunk);

        int finalX = radius + locChunk.getX();
        int finalZ = radius + locChunk.getZ();


        int finalXNegative;
        int finalZNegative;

        finalXNegative = locChunk.getX() - radius;
        finalZNegative = locChunk.getZ() - radius;

        //get chunks in a positive radius
        for (int x = locChunk.getX(); x <= finalX; x++) {
            for (int z = locChunk.getZ(); z <= finalZ; z++) {
                chunks.add(location.getWorld().getChunkAt(x, z));
            }
        }

        //get chunks in a negative radius
        for (int x = finalXNegative; x <= locChunk.getX(); x++) {
            for (int z = finalZNegative; z <= locChunk.getZ(); z++) {
                chunks.add(location.getWorld().getChunkAt(x, z));
            }
        }


        return chunks;
    }


    public static List<Block> getNearbyBlocks(Location location, int radius, Material searchedBlock) {
        List<Block> blocks = new ArrayList<>();
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    Block b = location.getWorld().getBlockAt(x, y, z);
                    if (b.getType().equals(searchedBlock))
                        blocks.add(b);
                }
            }
        }
        return blocks;
    }


    public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<>();
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    Block b = location.getWorld().getBlockAt(x, y, z);
                    blocks.add(b);
                }
            }
        }
        return blocks;
    }


    private static Location getGroundLocation(World world, int x, int z) {
        int y = world.getHighestBlockYAt(x, z);
        return new Location(world, x, y, z);
    }

    public static Location getRandomLocation(World world, int minX, int maxX, int minZ, int maxZ) {

        Random random = new Random();

        int x = random.nextInt((maxX - (-maxX)) + 1) + (-maxX);
        int z = random.nextInt((maxZ - (-maxZ)) + 1) + (-maxZ);

        while (x < minX && x > -minX) {
            x = random.nextInt((maxX - (-maxX)) + 1) + (-maxX);
        }

        while (z < minZ && z > -minZ) {
            z = random.nextInt((maxZ - (-maxZ)) + 1) + (-maxZ);
        }

        return getGroundLocation(world, x, z);
    }

    public static boolean isSameLocation(Location location1, Location location2) {
        int x1 = location1.getBlockX();
        int x2 = location2.getBlockX();
        if (x1 != x2) return false;
        int y1 = location1.getBlockY();
        int y2 = location2.getBlockY();
        if (y1 != y2) return false;
        int z1 = location1.getBlockZ();
        int z2 = location2.getBlockZ();
        if (z1 != z2) return false;
        return location1.getWorld().equals(location2.getWorld());
    }


    public static List<Chunk> getChunkSquare(int radius, Location initial) {


        List<Chunk> chunks = new ArrayList<>();

        Chunk initialChunk = initial.getChunk();

        if (radius == 1) {
            radius = 0;
        } else
            radius = radius / 3;

        int initialX = initialChunk.getX() - radius;
        int initialZ = initialChunk.getZ() - radius;

        int finalX = initialChunk.getX() + radius;
        int finalZ = initialChunk.getZ() + radius;


        for (int i = initialX; i <= finalX; i++) {
            for (int j = initialZ; j <= finalZ; j++)
                chunks.add(initial.getWorld().getChunkAt(i, j));
        }


        return chunks;
    }


    public static boolean isInChunkRadius(Chunk chunk, Chunk target, int radius) {

        //not in same world
        if (!chunk.getWorld().getName().equals(target.getWorld().getName())) {
            return false;
        }

        int x = chunk.getX();
        int z = chunk.getZ();

        int targetX = target.getX();
        int targetZ = target.getZ();


        if (targetX == x && targetZ == z) {
            return true;
        }


        int finalX = radius + x;
        int finalZ = radius + z;


        int finalXNegative = x - radius;
        int finalZNegative = z - radius;


        return targetX <= finalX && targetZ <= finalZ && targetX >= finalXNegative && targetZ >= finalZNegative;

    }


    public static int getHighestBlockInChunk(Chunk actualChunk) {

        int highest = 0;

        for(int x = 0; x < 16; x++){
            for (int z = 0; z < 16; z++){

                Block block = actualChunk.getBlock(x,0,z);
                Location location = block.getLocation();
                int highestInLocation = actualChunk.getWorld().getHighestBlockYAt(location);
                if(highestInLocation > highest){
                    highest = highestInLocation;
                }
            }
        }

        return highest;
    }
}
