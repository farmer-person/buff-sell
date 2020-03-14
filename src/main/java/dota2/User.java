// *********************************************************
// *  Author: Farmer                                       *
// *  Mail: iceyee.studio@qq.com                           *
// *  Git: https://github.com/iceyee                       *
// *********************************************************
//
package dota2;
import com.google.gson.*;

/**
<pre>
<a target="_blank" href="https://api.buff.163.com/api/market/buy_order/wait_supply?appid=570&page_num=1&page_size=60">purchasing</a>
<a target="_blank" href="https://api.buff.163.com/market/buy_order/history?game=dota2&page_num=">buy log</a>
<a target="_blank" href="https://api.buff.163.com/api/market/backpack?game=dota2&page_num=1&page_size=60">buff backpack</a>
<a target="_blank" href="https://api.buff.163.com/account/api/user/info">user info</a>
<a target="_blank" href="https://api.buff.163.com/api/message/notification">message</a>
<a target="_blank" href="https://api.buff.163.com/api/market/steam_trade">steam trade</a>
<a target="_blank" href="https://api.buff.163.com/api/market/sell_order/on_sale?appid=570&mode=2%2C5&page_num=1&page_size=60">selling</a>
<a target="_blank" href="https://api.buff.163.com/api/market/steam_inventory?appid=570&force=0&game=dota2&page_num=1&page_size=60&state=tradable">steam inventory</a>
<a target="_blank" href="https://api.buff.163.com/api/asset/get_brief_asset">money</a>
<!--
<a target="_blank" href=""></a>
-->
</pre>
*/
public class User {

    /*{{{*/
    /*
    [{"appid":570,"asset_info":{"action_link":"","appid":570,"assetid":"15382485454","classid":"1508406959","contextid":2,"goods_id":1426,"has_tradable_cooldown":false,"info":{"gems":[],"icon_url":"https://g.fp.ps.netease.com/market/file/5a0e9c4e5e6027d66bdaf93asWigU3qk","original_icon_url":"https://g.fp.ps.netease.com/market/file/59926bf596dee4182159f7faB0AI6Kyj","unlock_style":[{"name":"普通"},{"name":"执行者"}]},"instanceid":"0","paintwear":"","tradable_cooldown_text":"立即可取回","tradable_unfrozen_time":null},"created_at":1548740537,"game":"dota2","goods_id":1426,"id":"190129B1107216324","market_hash_name":null,"state":9,"state_text":"不能上架","state_toast":null,"tradable_cooldown_text":"立即可取回","updated_at":1548740537}]
    */
    /*}}}*/
    /**
    buff背包的信息
    @return 异常返回空列表
    */
    public static JsonArray buffBackpack (String stringCookie,
                                          String stringProxy) {
        /*{{{*/
        if (null == stringCookie) {
            return null;
        }
        //
        JsonArray jsonArrayItems = new JsonArray ();
        try {
            String stringUrl
                = "https://api.buff.163.com/api/market/backpack?game=dota2&page_size=50&page_num=1";
            farmer.Http http
                = new farmer.Http ()
                            .setBooleanVerbose (false)
                            .setBooleanRedirected (true)
                            .setStringUrl (stringUrl)
                            .setStringCookie (stringCookie)
                            .setProxy (stringProxy)
                            .request ();
            if (200 != http.getIntResponseCode ()) {
                return jsonArrayItems;
            }
            //
            String stringResponse = http.getStringResponseBody ();
            if (! stringResponse.trim ().startsWith ("{")) {
                stringResponse = new StringBuilder ().append ("{")
                                                     .append (stringResponse)
                                                     .toString ();
            }
            //
            jsonArrayItems
                = new JsonParser ().parse (stringResponse)
                                   .getAsJsonObject ()
                                   .getAsJsonObject ("data")
                                   .getAsJsonArray ("items");
        }
        catch (java.lang.Exception e) {
            e.printStackTrace ();
            System.err.flush ();
        }
        finally {
            System.out.print ("\nbuff-dota2库存数量: ");
            System.out.print (jsonArrayItems.size ());
            System.out.flush ();
            return jsonArrayItems;
        }
        /*}}}*/
    }

    /*{{{*/
    /*
		[
		  {
			"action_link": null,
			"amount": 1,
			"appid": 570,
			"asset_info": {},
			"assetid": "15380002799",
			"classid": "1326926159",
			"contextid": 2,
			"deposit_index": null,
			"equipped": false,
			"game": "dota2",
			"goods_id": 14435,
			"icon_url": "https://g.fp.ps.netease.com/market/file/5a0e9529a7f25296f640d777P20Ukf9o",
			"instanceid": "0",
			"item_id": 11645,
			"market_hash_name": "Ferocious Heart Loading Screen",
			"name": "猛兽之心载入画面",
			"original_icon_url": "https://g.fp.ps.netease.com/market/file/5992943496dee4213b5ff614AjStOaOi",
			"progress": 0,
			"progress_text": "库存中",
			"punish_end_time": null,
			"sell_order_price": "0",
			"state": 3,
			"state_text": "可出售",
			"state_toast": null,
			"steam_price": "0.03",
			"tradable": true,
			"tradable_text": null,
			"tradable_time": null
		  }
		]

        [{"action_link":null,"amount":1,"appid":570,"asset_info":{"action_link":"","appid":570,"assetid":"15380002799","classid":"1326926159","contextid":2,"goods_id":14435,"info":{"gems":[],"icon_url":"https://g.fp.ps.netease.com/market/file/5a0e9529a7f25296f640d777P20Ukf9o","original_icon_url":"https://g.fp.ps.netease.com/market/file/5992943496dee4213b5ff614AjStOaOi","unlock_style":[]},"instanceid":"0","paintwear":""},"assetid":"15380002799","classid":"1326926159","contextid":2,"deposit_index":null,"equipped":false,"game":"dota2","goods_id":14435,"icon_url":"https://g.fp.ps.netease.com/market/file/5a0e9529a7f25296f640d777P20Ukf9o","instanceid":"0","item_id":11645,"market_hash_name":"Ferocious Heart Loading Screen","name":"猛兽之心载入画面","original_icon_url":"https://g.fp.ps.netease.com/market/file/5992943496dee4213b5ff614AjStOaOi","progress":1,"progress_text":"出售中","punish_end_time":null,"sell_order_price":"1","state":3,"state_text":"可出售","state_toast":"该物品正在出售中","steam_price":"0.03","tags":{"custom":{"category":"custom","internal_name":"2015_autumn","localized_name":"2015秋季赛"},"quality":{"category":"quality","internal_name":"standard","localized_name":"普通"},"rarity":{"category":"rarity","internal_name":"uncommon","localized_name":"罕见"},"type":{"category":"type","internal_name":"loading_screen","localized_name":"载入画面"}},"tradable":true,"tradable_text":null,"tradable_time":null}]
     */
    /*}}}*/
    /**
    steam库存信息
    @return 异常返回空列表
    */
    public static JsonArray steamInventory (String stringCookie,
                                            String stringProxy) {
        /*{{{*/
        if (null == stringCookie) {
            return null;
        }
        //
        JsonArray jsonArrayItems = new JsonArray ();
        try {
            String stringLast = null;
            String stringUrl
                = "https://api.buff.163.com/api/market/steam_inventory?game=dota2&force=1&page_size=50&state=tradable";
            stringUrl += "&_=";
            stringUrl += farmer.Time.nowTimeMilliSecond ();
            stringUrl += "&page_num=";
            for (int intX = 1; intX <= 20; intX ++) {
                stringUrl += intX;
                farmer.Http http
                    = new farmer.Http ()
                                .setBooleanVerbose (false)
                                .setBooleanRedirected (true)
                                .setProxy (stringProxy)
                                .setStringUrl (stringUrl)
                                .setStringCookie (stringCookie)
                                .request ();
                if (200 != http.getIntResponseCode ()) {
                    continue;
                }
                //
                String stringResponse = http.getStringResponseBody ();
                if (! stringResponse.trim ().startsWith ("{")) {
                    stringResponse = new StringBuilder ().append ("{")
                                                         .append (stringResponse)
                                                         .toString ();
                }
                //
                JsonArray jsonArrayTemporary
                    = new JsonParser ().parse (stringResponse)
                                       .getAsJsonObject ()
                                       .getAsJsonObject ("data")
                                       .getAsJsonArray ("items");
                if (jsonArrayTemporary.size () < 50) {
                    jsonArrayItems.addAll (jsonArrayTemporary);
                    break;
                }
                else if (jsonArrayTemporary.get (0).getAsJsonObject ().get ("goods_id").getAsString ().equals (stringLast)) {
                    break;
                }
                else {
                    jsonArrayItems.addAll (jsonArrayTemporary);
                    stringLast
                        = jsonArrayTemporary.get (0)
                                            .getAsJsonObject ()
                                            .get ("goods_id")
                                            .getAsString ();
                    continue;
                }
                //
            }
        }
        catch (java.lang.Exception e) {
            e.printStackTrace ();
            System.err.flush ();
        }
        finally {
            System.out.print ("\nsteam-dota2库存数量: ");
            System.out.print (jsonArrayItems.size ());
            System.out.flush ();
            return jsonArrayItems;
        }
        /*}}}*/
    }
}
