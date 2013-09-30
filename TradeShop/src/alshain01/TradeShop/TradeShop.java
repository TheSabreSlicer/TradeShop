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
	protected CustomYML messageReader = new CustomYML(this, "message.yml");
	
	//TODO: Create an async task to remove players from the list after a timeout.
	protected ConcurrentHashMap<Player, Long> createQueue = new ConcurrentHashMap<Player, Long>();
	protected ConcurrentHashMap<Player, Trade> modifyQueue = new ConcurrentHashMap<Player, Trade>();
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
		final String addHelp = "/tradeshop add <material> <quantity> <material> <quantity>";
		final String deleteHelp = "/tradeshop delete <material> <material>";
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(Message.NoConsoleError.get());
			return true;
		}
		
		if(args.length < 1) { return false; }
		
		if(cmd.getName().equalsIgnoreCase("tradeshop")) {
			if(args[0].equalsIgnoreCase("create")) {
				
				// Add the creation to the queue
				sender.sendMessage(Message.CreateMode.get());
				createQueue.put(((Player)sender), new Date().getTime());
				return true;
				
			} else if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("delete")) {
				
				// Check the argument formatting
				if(args.length != 3 || args.length != 5) {
					if(args[0].equalsIgnoreCase("add")) {
						sender.sendMessage(addHelp);
					} else {
						sender.sendMessage(deleteHelp);
					}
					return true;
				}
				
				// Parse the arguments
				Material tradeItem = Material.getMaterial(args[1]);
				Material tradeForItem = null;
				int tradeQty = 0;
				int tradeForQty = 0;
				
				// Find the second material and quantities
				if (args.length == 5) {
					tradeForItem = Material.getMaterial(args[3]);

					try {
						tradeQty = Integer.valueOf(args[2]);
						tradeForQty = Integer.valueOf(args[4]);
					} catch (NumberFormatException ex) {
						sender.sendMessage(addHelp);
						return true;
					}
				} else {
					tradeForItem = Material.getMaterial(args[2]);
				}
				
				// Check that valid materials were provided.
				if(tradeItem == null || tradeForItem == null) {
					String item;
					if (tradeItem == null) { item = args[1]; }
					else {
						if(args[0].equalsIgnoreCase("add")) {
							item = args[3];
						} else {
							item = args[2];
						}
					}

					sender.sendMessage(Message.InvalidMaterialError.get()
							.replaceAll("\\{Material\\}", item));
					return true;
				}
				
				// Add the modification to the queue.
				sender.sendMessage(Message.ModifyMode.get());
				modifyQueue.put((Player)sender, new Trade(tradeItem, tradeForItem, tradeQty, tradeForQty));
				return true;
			}
		}
		return false;
	}
}
