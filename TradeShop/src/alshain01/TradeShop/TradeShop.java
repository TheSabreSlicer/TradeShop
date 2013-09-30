package alshain01.TradeShop;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import alshain01.Flags.Flags;


public class TradeShop extends JavaPlugin {
	public static TradeShop instance;
	protected CustomYML shopData = new CustomYML(this, "data.yml");
	protected ConcurrentHashMap<Player, Long> createMode = new ConcurrentHashMap<Player, Long>();
	protected boolean flags = false;
	
	@Override
	public void onEnable() {
		instance = this;
		flags = Bukkit.getServer().getPluginManager().isPluginEnabled("Flags");
		if(flags) {
			String plugin = this.getName();
			Flags.instance.getRegistrar().register("TSAllowCreate", 
					"Allows the creation of a " + plugin + " shop.", false, plugin,
					"\\{Player\\} You are not allowed to create a " + plugin + "in \\{Owner\\}''s \\{AreaType\\}.",
					"\\{Player\\} You are not allowed to create a " + plugin + "in \\{World\\}");
/*			Flags.instance.getRegistrar().register("TSAllowTrade", 
	                "Allows players to use a " + plugin + "shop.", true, plugin,
	                "\\{Player\\} You are not allowed to use a " + plugin + "in \\{Owner\\}''s \\{AreaType\\}.",
					"\\{Player\\} You are not allowed to use a " + plugin + "in \\{World\\}");*/
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
		if(!(sender instanceof Player)) { return false; } //TODO: Send the console a message
		
		if(cmd.getName().equalsIgnoreCase("simpleshop")) {
			if(args.length > 0 && args[0] == "create") {
				// TODO: Send the player a message
				createMode.put(((Player)sender), new Date().getTime());
				return true;
			}
		}
		return false;
	}
}
