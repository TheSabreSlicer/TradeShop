package alshain01.TradeShop;

import java.util.Date;

import org.bukkit.Material;

public class Trade {
	private long createTime;
	private Material tradeItem;
	private Material tradeForItem;
	private int tradeQty;
	private int tradeForQty;
	
	public Trade(Material tradeItem, Material tradeForItem, int tradeQty, int tradeForQty) {
		this.tradeItem = tradeItem;
		this.tradeForItem = tradeForItem;
		this.tradeQty = tradeQty;
		this.tradeForQty = tradeForQty;
		this.createTime = new Date().getTime();
	}
	
	public Material getTradeItem() {
		return tradeItem;
	}
	
	public Material getTradeForItem() {
		return tradeForItem;
	}
	
	public int getTradeQuantity() {
		return tradeQty;
	}
	
	public int getTradeForQuantity() {
		return tradeForQty;
	}
	
	public long getCreationTime() {
		return createTime;
	}
}
