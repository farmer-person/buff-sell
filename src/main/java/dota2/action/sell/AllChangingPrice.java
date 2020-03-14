// *********************************************************
// *  Author: Farmer                                       *
// *  Mail: iceyee.studio@qq.com                           *
// *  Git: https://github.com/iceyee                       *
// *********************************************************
//                                                          
package dota2.action.sell;
import com.google.gson.*;

/**
08.改价<br>
@see dota2.x.Sell
*/
public class AllChangingPrice implements Runnable {

    static java.util.List <String> listStringData = null;

    public AllChangingPrice () {
        /*{{{*/
        return;
        /*}}}*/
    }

    @Override
    public void run () {
        /*{{{*/
        try {
            main ();
            return;
        }
        catch (java.lang.Exception e) {
            e.printStackTrace ();
            System.err.flush ();
        }
        finally {
            //
        }
        /*}}}*/
    }

    private void main ()
                throws java.lang.InterruptedException {
        /*{{{*/
        listStringData = new java.util.LinkedList ();
        JsonArray jsonArrayInventory = dota2.User.steamInventory (buff.Global.stringCookie,
                                                                  null);
        Thread.sleep (2000L);
        for (int intX = 0; null != jsonArrayInventory && intX < jsonArrayInventory.size (); intX ++) {
            try {
                JsonObject jsonObjectItem = jsonArrayInventory.get (intX)
                                                              .getAsJsonObject ();
                if (1 != jsonObjectItem.get ("progress").getAsInt ()) {
                    continue;
                }
                //
                analysis (jsonObjectItem);
            }
            catch (java.lang.Exception e) {
                e.printStackTrace ();
                System.err.flush ();
            }
            finally {
                //
            }
        }
        changePrice ();
        System.out.print ("\n");
        System.out.flush ();
        return;
        /*}}}*/
    }

    private void analysis (JsonObject jsonObjectItem)
                throws java.lang.InterruptedException {
        /*{{{*/
        StringBuilder builderPrint
            = new StringBuilder ().append ("\n")
                                  .append (jsonObjectItem.get ("name").getAsString ())
                                  .append (" | https://buff.163.com/market/goods?goods_id=")
                                  .append (jsonObjectItem.get ("goods_id").getAsString ());
        JsonArray jsonArraySell
            = dota2.Market.sellInformation (jsonObjectItem.get ("goods_id").getAsString (),
                                            null);
        Thread.sleep (1200L);
        double doubleSell = 2 * jsonObjectItem.get ("steam_price_custom")
                                              .getAsDouble ();
        for (int intX = 0; null != jsonArraySell && intX < jsonArraySell.size (); intX ++) {
            JsonObject jsonObjectSell = jsonArraySell.get (intX)
                                                     .getAsJsonObject ();
            // 过滤自己的饰品 取出售价
            if (! jsonObjectSell.get ("user_id").getAsString ().equals (buff.Global.stringUserId)) {
                doubleSell = jsonObjectSell.get ("price")
                                           .getAsDouble ();
                break;
            }
            //
        }
        double doubleChange = Double.valueOf (String.format ("%.2f", doubleSell -0.01))
                                    .doubleValue ();
        // 改价
        String stringA
            = String.format ("{\"sell_order_id\":\"%s\",\"price\":%s,\"goods_id\":%s,\"desc\":\"\"}",
                             jsonObjectItem.get ("sell_order_id").getAsString (),
                             String.valueOf (doubleChange),
                             jsonObjectItem.get ("goods_id").getAsString ());
        if (doubleChange != jsonObjectItem.get ("sell_order_price").getAsDouble ()) {
            builderPrint.append (" | ")
                        .append (jsonObjectItem.get ("sell_order_price").getAsDouble ())
                        .append (" ==> ")
                        .append (doubleChange);
            listStringData.add (stringA);
        }
        // 正常
        else {
            builderPrint.append (" | 正常");
        }
        //
        return;
        /*}}}*/
    }

    private void changePrice ()
                throws java.lang.InterruptedException {
        /*{{{*/
        if (0 == listStringData.size ()) {
            return;
        }
        //
        // 最终执行改价
        String stringUrl = "https://buff.163.com/api/market/sell_order/change";
        String stringHeader
            = "Content-Type: application/json                                                  \r\n" +
              "X-CSRFToken: %s                                                                 \r\n" +
              "Cookie: %s                                                                      \r\n" +
              "Referer: https://buff.163.com/market/sell_order/on_sale?game=dota2&mode=2,5 \r\n";
        stringHeader = String.format (stringHeader,
                                      buff.Global.stringCsrfToken,
                                      buff.Global.stringCookie);
        int intA = listStringData.size () / 50;
        int intB = listStringData.size () % 50;
        for (int intX = intA; 0 <= intX; intX --) {
            int intIndexA = intX * 50;
            int intIndexB = intX * 50 + intB;
            String stringData
                = listStringData.subList (intIndexA, intIndexB)
                                .stream ()
                                .collect (java.util.stream.Collectors.joining (","));
            stringData
                = new StringBuilder ().append ("{\"game\":\"dota2\",\"sell_orders\":[")
                                      .append (stringData)
                                      .append ("]}")
                                      .toString ();
            farmer.Http http
                = new farmer.Http ()
                            .setBooleanVerbose (false)
                            .setStringUrl (stringUrl)
                            .setStringHeader (stringHeader)
                            .setStringData (stringData)
                            .request ();
            Thread.sleep (2000L);
            System.out.print (new JsonParser ().parse (http.getStringResponseBody ()));
            System.out.flush ();
            intB = 50;
        }
        System.out.print ("\n");
        System.out.flush ();
        return;
        /*}}}*/
    }
}
