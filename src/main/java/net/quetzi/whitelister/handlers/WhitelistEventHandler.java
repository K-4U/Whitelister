package net.quetzi.whitelister.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.quetzi.whitelister.Whitelister;

/**
 * Created by Quetzi on 24/09/14.
 */
public class WhitelistEventHandler {

    @SubscribeEvent
    public void PlayerLoggedInHandler(PlayerEvent.PlayerLoggedInEvent event) {

        if(!Whitelister.isEnabled) return;
        if (MinecraftServer.getServer().getConfigurationManager().func_152596_g(event.player.getGameProfile())) {
            Whitelister.log.info("Allowing exempt " + event.player.getGameProfile().getName());
            return;
        }

        if (!Whitelister.whitelist.contains(event.player.getGameProfile().getName().toLowerCase())) {
            Whitelister.log.info(event.player.getGameProfile().getName() + " not on whitelist.");
            Whitelister.log.info("Blocking " + event.player.getGameProfile().getName());
            ((EntityPlayerMP) event.player).playerNetServerHandler.kickPlayerFromServer(Whitelister.kickMessage);
        } else {
            Whitelister.log.info("Allowing " + event.player.getGameProfile().getName());
        }
    }
}
