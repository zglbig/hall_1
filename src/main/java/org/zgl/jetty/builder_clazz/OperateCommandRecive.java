package org.zgl.jetty.builder_clazz;
import org.zgl.logic.login.logic.LoginHandler;
import org.zgl.logic.login.logic.LoginHandler_2;
import org.zgl.logic.hall.giftBag.GiftBagCmd;
import org.zgl.logic.hall.frineds.cmd.FriendRequestList;
import org.zgl.logic.hall.frineds.cmd.FriendSendGift;
import org.zgl.logic.hall.frineds.cmd.FriendHandler;
import org.zgl.logic.hall.shop.cmd.ShopBuy_Vip;
import org.zgl.logic.hall.weath.cmd.MoneyTree;
import org.zgl.logic.hall.onlineAward.OnlineAwardCmd;
import org.zgl.logic.hall.siginin.logic.AcitivityCmd;
import org.zgl.logic.hall.ranking.RankingCmd;
import org.zgl.logic.hall.siginin.logic.FirstBuyCmd;
import org.zgl.logic.hall.onlineAward.DialCmd;
import org.zgl.logic.hall.task.cmd.RequestTaskList;
import org.zgl.logic.hall.task.cmd.RequestTaskGetAward;
import org.zgl.logic.hall.siginin.logic.SignIn;
import org.zgl.logic.room_connection.IntoRoom;
import org.zgl.logic.hall.weath.cmd.AutoUse;
import org.zgl.logic.hall.weath.cmd.PlayerWeath;
import org.zgl.player.RequestPlayerInfo;
import org.zgl.logic.hall.shop.cmd.ShopBuy_Auto;
import org.zgl.logic.hall.shop.cmd.ShopBuy_Exchange;
import org.zgl.logic.hall.shop.cmd.ShopBuy_Gold;
import org.zgl.logic.hall.shop.cmd.ShopBuy_MoneyTree;
import org.zgl.logic.hall.shop.cmd.ShopBuy_Prop;
import org.zgl.logic.hall.shop.cmd.ShopBuy_Diamond;
import org.zgl.logic.login.logic.LoginHandler_3;
import org.zgl.logic.login.logic.LoginHandler_4;
import org.zgl.bank.BankInfoOperation;
import org.zgl.bank.BankInsertOperation;
import org.zgl.generalize.GeneralizeOperation;
import org.zgl.generalize.LookGeneralizeInfoOperation;
import org.zgl.generalize.GenreralizeAwardOperation;
import org.zgl.logic.hall.exchangeinfo.cmd.Exchange_Info;
import org.zgl.logic.hall.exchangeinfo.cmd.Exchange_HeadIcon;
import org.zgl.logic.hall.giftBag.GiftBagRequestOperation;
import org.zgl.logic.hall.onlineAward.OnlineAwardRequestOperation;
import org.zgl.logic.hall.pawnshop.PawnShopOperation;
import org.zgl.logic.room_connection.IntoHall;
import org.zgl.jetty.operation.OperateCommandAbstract;
public class OperateCommandRecive{
	private static OperateCommandRecive instance;
	public static OperateCommandRecive getInstance(){
		if(instance == null)
			instance = new OperateCommandRecive();
		return instance;
	}
	public OperateCommandAbstract recieve(int id,String[] params){
		switch (id){
			case 1:
				return getLoginHandler(params);
			case 2:
				return getLoginHandler_2(params);
			case 3:
				return getGiftBagCmd(params);
			case 4:
				return getFriendRequestList(params);
			case 5:
				return getFriendSendGift(params);
			case 6:
				return getFriendHandler(params);
			case 7:
				return getShopBuy_Vip(params);
			case 8:
				return getMoneyTree(params);
			case 9:
				return getOnlineAwardCmd(params);
			case 10:
				return getAcitivityCmd(params);
			case 11:
				return getRankingCmd(params);
			case 12:
				return getFirstBuyCmd(params);
			case 13:
				return getDialCmd(params);
			case 14:
				return getRequestTaskList(params);
			case 15:
				return getRequestTaskGetAward(params);
			case 16:
				return getSignIn(params);
			case 18:
				return getIntoRoom(params);
			case 19:
				return getAutoUse(params);
			case 20:
				return getPlayerWeath(params);
			case 21:
				return getRequestPlayerInfo(params);
			case 22:
				return getShopBuy_Auto(params);
			case 23:
				return getShopBuy_Exchange(params);
			case 24:
				return getShopBuy_Gold(params);
			case 25:
				return getShopBuy_MoneyTree(params);
			case 26:
				return getShopBuy_Prop(params);
			case 27:
				return getShopBuy_Diamond(params);
			case 28:
				return getLoginHandler_3(params);
			case 29:
				return getLoginHandler_4(params);
			case 30:
				return getBankInfoOperation(params);
			case 31:
				return getBankInsertOperation(params);
			case 32:
				return getGeneralizeOperation(params);
			case 33:
				return getLookGeneralizeInfoOperation(params);
			case 34:
				return getGenreralizeAwardOperation(params);
			case 35:
				return getExchange_Info(params);
			case 36:
				return getExchange_HeadIcon(params);
			case 37:
				return getGiftBagRequestOperation(params);
			case 38:
				return getOnlineAwardRequestOperation(params);
			case 39:
				return getPawnShopOperation(params);
			case 10002:
				return getIntoHall(params);
			default:
				return null;
		}
	}
	private OperateCommandAbstract getLoginHandler(String[] params){
		int value0 = Integer.parseInt(params[0]);
		String value1 = params[1];
		String value2 = params[2];
		String value3 = params[3];
		String value4 = params[4];
		String value5 = params[5];
		return new LoginHandler(value0,value1,value2,value3,value4,value5);
	}
	private OperateCommandAbstract getLoginHandler_2(String[] params){
		String value0 = params[0];
		String value1 = params[1];
		return new LoginHandler_2(value0,value1);
	}
	private OperateCommandAbstract getGiftBagCmd(String[] params){
		long value0 = Long.parseLong(params[0]);
		return new GiftBagCmd(value0);
	}
	private OperateCommandAbstract getFriendRequestList(String[] params){
		long value0 = Long.parseLong(params[0]);
		return new FriendRequestList(value0);
	}
	private OperateCommandAbstract getFriendSendGift(String[] params){
		long value0 = Long.parseLong(params[0]);
		int value1 = Integer.parseInt(params[1]);
		int value2 = Integer.parseInt(params[2]);
		long value3 = Long.parseLong(params[3]);
		return new FriendSendGift(value0,value1,value2,value3);
	}
	private OperateCommandAbstract getFriendHandler(String[] params){
		int value0 = Integer.parseInt(params[0]);
		long value1 = Long.parseLong(params[1]);
		long value2 = Long.parseLong(params[2]);
		return new FriendHandler(value0,value1,value2);
	}
	private OperateCommandAbstract getShopBuy_Vip(String[] params){
		int value0 = Integer.parseInt(params[0]);
		long value1 = Long.parseLong(params[1]);
		return new ShopBuy_Vip(value0,value1);
	}
	private OperateCommandAbstract getMoneyTree(String[] params){
		int value0 = Integer.parseInt(params[0]);
		long value1 = Long.parseLong(params[1]);
		return new MoneyTree(value0,value1);
	}
	private OperateCommandAbstract getOnlineAwardCmd(String[] params){
		long value0 = Long.parseLong(params[0]);
		return new OnlineAwardCmd(value0);
	}
	private OperateCommandAbstract getAcitivityCmd(String[] params){
		int value0 = Integer.parseInt(params[0]);
		long value1 = Long.parseLong(params[1]);
		return new AcitivityCmd(value0,value1);
	}
	private OperateCommandAbstract getRankingCmd(String[] params){
		long value0 = Long.parseLong(params[0]);
		return new RankingCmd(value0);
	}
	private OperateCommandAbstract getFirstBuyCmd(String[] params){
		long value0 = Long.parseLong(params[0]);
		return new FirstBuyCmd(value0);
	}
	private OperateCommandAbstract getDialCmd(String[] params){
		long value0 = Long.parseLong(params[0]);
		return new DialCmd(value0);
	}
	private OperateCommandAbstract getRequestTaskList(String[] params){
		long value0 = Long.parseLong(params[0]);
		return new RequestTaskList(value0);
	}
	private OperateCommandAbstract getRequestTaskGetAward(String[] params){
		int value0 = Integer.parseInt(params[0]);
		long value1 = Long.parseLong(params[1]);
		return new RequestTaskGetAward(value0,value1);
	}
	private OperateCommandAbstract getSignIn(String[] params){
		long value0 = Long.parseLong(params[0]);
		return new SignIn(value0);
	}
	private OperateCommandAbstract getIntoRoom(String[] params){
		int value0 = Integer.parseInt(params[0]);
		long value1 = Long.parseLong(params[1]);
		return new IntoRoom(value0,value1);
	}
	private OperateCommandAbstract getAutoUse(String[] params){
		int value0 = Integer.parseInt(params[0]);
		long value1 = Long.parseLong(params[1]);
		return new AutoUse(value0,value1);
	}
	private OperateCommandAbstract getPlayerWeath(String[] params){
		long value0 = Long.parseLong(params[0]);
		return new PlayerWeath(value0);
	}
	private OperateCommandAbstract getRequestPlayerInfo(String[] params){
		long value0 = Long.parseLong(params[0]);
		return new RequestPlayerInfo(value0);
	}
	private OperateCommandAbstract getShopBuy_Auto(String[] params){
		int value0 = Integer.parseInt(params[0]);
		long value1 = Long.parseLong(params[1]);
		return new ShopBuy_Auto(value0,value1);
	}
	private OperateCommandAbstract getShopBuy_Exchange(String[] params){
		int value0 = Integer.parseInt(params[0]);
		long value1 = Long.parseLong(params[1]);
		return new ShopBuy_Exchange(value0,value1);
	}
	private OperateCommandAbstract getShopBuy_Gold(String[] params){
		int value0 = Integer.parseInt(params[0]);
		long value1 = Long.parseLong(params[1]);
		return new ShopBuy_Gold(value0,value1);
	}
	private OperateCommandAbstract getShopBuy_MoneyTree(String[] params){
		int value0 = Integer.parseInt(params[0]);
		long value1 = Long.parseLong(params[1]);
		return new ShopBuy_MoneyTree(value0,value1);
	}
	private OperateCommandAbstract getShopBuy_Prop(String[] params){
		int value0 = Integer.parseInt(params[0]);
		long value1 = Long.parseLong(params[1]);
		return new ShopBuy_Prop(value0,value1);
	}
	private OperateCommandAbstract getShopBuy_Diamond(String[] params){
		int value0 = Integer.parseInt(params[0]);
		long value1 = Long.parseLong(params[1]);
		return new ShopBuy_Diamond(value0,value1);
	}
	private OperateCommandAbstract getLoginHandler_3(String[] params){
		int value0 = Integer.parseInt(params[0]);
		String value1 = params[1];
		String value2 = params[2];
		String value3 = params[3];
		String value4 = params[4];
		String value5 = params[5];
		return new LoginHandler_3(value0,value1,value2,value3,value4,value5);
	}
	private OperateCommandAbstract getLoginHandler_4(String[] params){
		long value0 = Long.parseLong(params[0]);
		return new LoginHandler_4(value0);
	}
	private OperateCommandAbstract getBankInfoOperation(String[] params){
		long value0 = Long.parseLong(params[0]);
		return new BankInfoOperation(value0);
	}
	private OperateCommandAbstract getBankInsertOperation(String[] params){
		long value0 = Long.parseLong(params[0]);
		int value1 = Integer.parseInt(params[1]);
		long value2 = Long.parseLong(params[2]);
		return new BankInsertOperation(value0,value1,value2);
	}
	private OperateCommandAbstract getGeneralizeOperation(String[] params){
		long value0 = Long.parseLong(params[0]);
		long value1 = Long.parseLong(params[1]);
		return new GeneralizeOperation(value0,value1);
	}
	private OperateCommandAbstract getLookGeneralizeInfoOperation(String[] params){
		long value0 = Long.parseLong(params[0]);
		return new LookGeneralizeInfoOperation(value0);
	}
	private OperateCommandAbstract getGenreralizeAwardOperation(String[] params){
		long value0 = Long.parseLong(params[0]);
		return new GenreralizeAwardOperation(value0);
	}
	private OperateCommandAbstract getExchange_Info(String[] params){
		long value0 = Long.parseLong(params[0]);
		String value1 = params[1];
		String value2 = params[2];
		String value3 = params[3];
		String value4 = params[4];
		return new Exchange_Info(value0,value1,value2,value3,value4);
	}
	private OperateCommandAbstract getExchange_HeadIcon(String[] params){
		long value0 = Long.parseLong(params[0]);
		String value1 = params[1];
		return new Exchange_HeadIcon(value0,value1);
	}
	private OperateCommandAbstract getGiftBagRequestOperation(String[] params){
		long value0 = Long.parseLong(params[0]);
		return new GiftBagRequestOperation(value0);
	}
	private OperateCommandAbstract getOnlineAwardRequestOperation(String[] params){
		long value0 = Long.parseLong(params[0]);
		return new OnlineAwardRequestOperation(value0);
	}
	private OperateCommandAbstract getPawnShopOperation(String[] params){
		long value0 = Long.parseLong(params[0]);
		int value1 = Integer.parseInt(params[1]);
		return new PawnShopOperation(value0,value1);
	}
	private OperateCommandAbstract getIntoHall(String[] params){
		long value0 = Long.parseLong(params[0]);
		return new IntoHall(value0);
	}
}
