package info.beastsoftware.beastcore.manager;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.utils.ILocationUtil;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class ChunkBusterTask {


    private final Queue<Chunk> toExplode;
    private final int layersPerTick;
    private int nextLayer = 0;
    private int highestBlock;
    private Chunk actualChunk;
    private final List<String> ignoredBlocks;
    private final BeastPlayer player;
    private final String cooldownMessage;
    private int countDown;
    private final int countDownTime;
    private final String chunksExploded;


    public ChunkBusterTask(List<Chunk> toExplode, int layersPerTick, List<String> ignoredBlocks, BeastPlayer player, String cooldownMessage, int countDown, boolean withCountDown, String chunksExploded) {
        this.toExplode = new LinkedBlockingDeque<>(toExplode);
        this.layersPerTick = layersPerTick;
        this.ignoredBlocks = ignoredBlocks;
        this.player = player;
        this.cooldownMessage = cooldownMessage;
        this.countDown = countDown;
        this.countDownTime = countDown;
        this.chunksExploded = chunksExploded;

        //countdown
        if (withCountDown) {
            this.runCountDown();
        }

        //explode directly
        else {
            this.explodeNextChunk();
        }
    }


    private void runCountDown() {

        if (countDown < 0) {
            this.explodeNextChunk();
            return;
        }


        this.sendCooldownMessage();


        countDown--;
        Bukkit.getScheduler().runTaskLater(BeastCore.getInstance(), this::runCountDown, 20L);
    }


    public void sendCooldownMessage() {
        String msg = StrUtils.replacePlaceholder(cooldownMessage, "{seconds}", String.valueOf(countDown));
        player.sms(msg);
    }


    public boolean isIgnoredBlock(Block block) {
        return ignoredBlocks.contains(block.getType().toString()) || block.getType().equals(Material.BEDROCK);
    }


    public void explodeNextChunk() {
        this.actualChunk = this.toExplode.poll();

        //no more chunks to explode
        if (Objects.isNull(this.actualChunk)) {
            this.sendExploded();
            return;
        }

        //reset parameters and explode next chunk
        this.nextLayer = 0;
        this.highestBlock = ILocationUtil.getHighestBlockInChunk(this.actualChunk);
        explodeLayer();
    }

    private void sendExploded() {
        player.sms(chunksExploded);
    }


    private void explodeLayer() {

        for (int layer = 0; layer < layersPerTick; layer++) {

            //layer is last, explode next chunk
            if (nextLayer > highestBlock) {
                explodeNextChunk();
                return;
            }

            //explode this layer
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {

                    Block block = actualChunk.getBlock(x, nextLayer, z);
                    if (!isIgnoredBlock(block)) {
                        block.setType(Material.AIR);
                    }
                }
            }

            this.nextLayer += 1;
        }

        Bukkit.getScheduler().runTaskLater(BeastCore.getInstance(), this::explodeLayer, 2L);
    }

}
