package alshain01.TradeShop;

import java.util.Date;

import org.bukkit.Material;

public class Trade {
	private Long id;
	private long createTime;
	private TradeItem[] items = new TradeItem[3];
	
	public class TradeItem {
		private Material item;
		private int quantity;
		
		public int getQuantity() {
			return quantity;
		}
		
		public Material getItem() {
			return item;
		}
	}
	
	public Trade(Material sellItem, Material buyItem, int sellQty, int buyQty) {
		id = null;
		
		items[0].item  = buyItem;
		items[1].item  = null;
		items[2].item = sellItem;
		
		items[0].quantity = buyQty;
		items[1].quantity = 0;
		items[2].quantity = sellQty;

		
		this.createTime = new Date().getTime();
	}
	
	public Trade(Material sellItem, Material buyItem1, Material buyItem2, int sellQty, int buyQty1, int buyQty2) {
		id = null;
		
		items[0].item  = buyItem1;
		items[1].item  = buyItem2;
		items[2].item = sellItem;
		
		items[0].quantity = buyQty1;
		items[1].quantity = buyQty2;
		items[2].quantity = sellQty;

		
		this.createTime = new Date().getTime();
	}
	
	public TradeItem getSellItem() {
		return items[2];
	}
	
	public TradeItem getBuyItem1() {
		return items[0];
	}
	
	public TradeItem getBuyItem2() {
		return items[1];
	}
	
	public long getCreationTime() {
		return createTime;
	}
	
	public long getID() {
		return id;
	}
}
