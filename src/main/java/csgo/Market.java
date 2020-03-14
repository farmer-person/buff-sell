// *********************************************************
// *  Author: Farmer                                       *
// *  Mail: iceyee.studio@qq.com                           *
// *  Git: https://github.com/iceyee                       *
// *********************************************************
//
package csgo;
import com.google.gson.*;

/**
<pre>
<a target="_blank" href="https://api.buff.163.com/api/market/goods/sell_order?game=csgo&goods_id=759462">sell</a>
<a target="_blank" href="https://api.buff.163.com/api/market/goods/buy_order?game=csgo&goods_id=759462">purchase</a>
<a target="_blank" href="https://api.buff.163.com/api/market/goods/bill_order?game=csgo&goods_id=759462">history</a>
<a target="_blank" href="https://api.buff.163.com/market/goods?goods_id=759462">detail</a>
<a target="_blank" href="https://api.buff.163.com/api/market/goods?game=csgo&page_num=1">market</a>
</pre>
*/
public class Market {

    static String stringSellInformationLink
        = "https://api.buff.163.com/api/market/goods/sell_order?game=csgo&goods_id=";

    /** 
    {can_bargain, allow_bargain, asset_info:{assetid, classid, instanceid, has_tradable_cooldown}, id(product_id), goods_id(class_id), price, user_id} 
    @param stringGoodsId 饰品类id, goods_id
    @param stringProxy 代理ip
    @return 异常返回null
    */
    public static JsonArray sellInformation (String stringGoodsId,
                                             String stringProxy) {
        /*{{{*/
        farmer.Http http
            = new farmer.Http ()
                        .setBooleanVerbose (false)
                        .setIntTimeOutMilliSecond (15000)
                        .setProxy (stringProxy)
                        .setStringUrl (stringSellInformationLink + stringGoodsId)
                        .setStringCookie ("Locale-Supported=zh-Hans;")
                        .request ();
        if (200 != http.getIntResponseCode ()) {
            return null;
        }
        //
        JsonObject jsonObject = new JsonParser ().parse (http.getStringResponseBody ())
                                                 .getAsJsonObject ();
        if (! jsonObject.has ("data")
              || ! jsonObject.getAsJsonObject ("data").has ("items")) {
            return null;
        }
        else {
            return jsonObject.getAsJsonObject ("data")
                             .getAsJsonArray ("items");
        }
        /*}}}*/
    }

    static String stringPurchaseInformationLink
        = "https://api.buff.163.com/api/market/goods/buy_order?game=csgo&goods_id=";

    /** 
    {allow_tradable_cooldown, created_at, frozen_amount, goods_id, id, num, pay_method, price, real_num, updated_at, user_id, state}
    @param stringGoodsId 饰品类id, goods_id
    @param stringProxy 代理ip
    @return 异常返回null
    */
    public static JsonArray purchaseInformation (String stringGoodsId,
                                                 String stringProxy) {
        /*{{{*/
        farmer.Http http
            = new farmer.Http ()
                        .setBooleanVerbose (false)
                        .setIntTimeOutMilliSecond (15000)
                        .setProxy (stringProxy)
                        .setStringUrl (stringPurchaseInformationLink + stringGoodsId)
                        .setStringCookie ("Locale-Supported=zh-Hans;")
                        .request ();
        if (200 != http.getIntResponseCode ()) {
            return null;
        }
        //
        JsonObject jsonObject = new JsonParser ().parse (http.getStringResponseBody ())
                                                 .getAsJsonObject ();
        if (! jsonObject.has ("data")
              || ! jsonObject.getAsJsonObject ("data").has ("items")) {
            return null;
        }
        else {
            return jsonObject.getAsJsonObject ("data")
                             .getAsJsonArray ("items");
        }
        /*}}}*/
    }

    /** 判断是否为标准饰品 */
    public static boolean isStandard (String stringName) {
        /*{{{*/
        if (stringName == null
              || stringName.length () == 0) {
            return false;
        }
        //
        return false;
        // if (stringName.equals ("擂鼓巫蟾")) {
        //     return false;
        // }
        // else if (! stringName.contains (" ")) {
        //     return true;
        // }
        // else if (stringName.contains ("：")
        //            || stringName.contains (":")) {
        //     return true;
        // }
        // else if (stringName.contains ("201")
        //            || stringName.contains ("202")) {
        //     return true;
        // }
        // else if (stringName.contains (" - ")
        //              && stringName.indexOf (" ") == stringName.indexOf (" - ")) {
        //     return true;
        // }
        // else {
        //     return false;
        // }
        // //
        /*}}}*/
    }
}
