package alshain01.TradeShop;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import alshain01.Flags.Flags;


public class TradeShop extends JavaPlugin {
	public static TradeShop instance;
	protected CustomYML shopData = new CustomYML(this, "data.yml");
	
	//TODO: Create an async task to remove players from the list after a timeout.
	protected ConcurrentHashMap<Player, Long> createMode = new ConcurrentHashMap<Player, Long>();
	protected ConcurrentHashMap<Player, Trade> modifyMode = new ConcurrentHashMap<Player, Trade>();
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
			Flags.instance.getRegistrar().register("TSAllowTrade", 
	                "Allows players to use a " + plugin + "shop.", true, plugin,
	                "\\{Player\\} You are not allowed to use a " + plugin + "in \\{Owner\\}''s \\{AreaType\\}.",
					"\\{Player\\} You are not allowed to use a " + plugin + "in \\{World\\}");
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
		if(!(sender instanceof Player)) { return false; } //TODO: Send the console a message
		if(args.length < 1) { return false; } //TODO: Send the player a nice message explaining their error
		
		if(cmd.getName().equalsIgnoreCase("tradeshop")) {
			if(args[0].equalsIgnoreCase("create")) {
				// TODO: Send the player a message to let them know they are in create mode
				createMode.put(((Player)sender), new Date().getTime());
				return true;
			} else if (args[0].equalsIgnoreCase("add")) {
				//TODO: Send the player a nice message explaining their error
				if(args.length != 5) { return false; }
				Material tradeItem = Material.getMaterial(args[1]);
				Material tradeForItem = Material.getMaterial(args[3]);
				
				if(tradeItem == null || tradeForItem == null) {
					//TODO: Send the player a message explaining their error
					return true;
				}
				
				int tradeQty = 0;
				int tradeForQty = 0;
				try {
					tradeQty = Integer.valueOf(args[2]);
					tradeForQty = Integer.valueOf(args[4]);
				} catch (NumberFormatException ex) {
					//TODO: Send the player a message explaining their error
					return true;
				}
				
				modifyMode.put((Player)sender, new Trade(tradeItem, tradeForItem, tradeQty, tradeForQty));
				return true;
			} else if (args[0].equalsIgnoreCase("delete")) {
				//TODO: Send the player a nice message explaining their error
				if(args.length != 3) { return false; }
				
				//TODO: everything here
			}
		}
		return false;
	}
	
}
