// *********************************************************
// *  Author: Farmer                                       *
// *  Mail: iceyee.studio@qq.com                           *
// *  Git: https://github.com/iceyee                       *
// *********************************************************
//                                                          
package buff.x;

public class Sell {

    public static void main (String[] args)
                throws java.lang.Exception {
        Class.forName ("buff.Global");
        Activate.start ();
        XSell.start ();
        while (true) {
            System.in.read ();
        }
    }
}

class XSell extends java.util.TimerTask {

    static java.util.Timer timer = new java.util.Timer (true);

    static String stringCooldownTimerFileName
        = buff.Global.stringUserHome + buff.Global.stringSeparator + ".buff.sell.cooldown.timer";

    public static void start () {
        long longTime = 0L;
        // 如果计时文件存在, 则读时间
        if (new java.io.File (stringCooldownTimerFileName).exists ()) {
            String stringTime = farmer.Io.read (stringCooldownTimerFileName)
                                         .trim ();
            try {
                longTime = Long.valueOf (stringTime)
                               .longValue ();
            }
            catch (java.lang.Exception e) {
                e.printStackTrace ();
                System.err.flush ();
            }
            finally {
                //
            }
        }
        //
        longTime = Math.max (longTime, System.currentTimeMillis () + 3000L);
        // 开始任务
        timer.schedule (new XSell (), new java.util.Date (longTime));
        // 时间提示
        String stringTime
            = java.time.LocalDateTime.ofEpochSecond (longTime / 1000L,
                                                     0,
                                                     java.time.ZoneOffset.of ("+8"))
                                     .toString ();
        System.out.print ("\n\n下次任务开始于: ");
        System.out.print (stringTime);
        System.out.flush ();
        // 写入计时文件中
        farmer.Io.write (stringCooldownTimerFileName, String.valueOf (longTime));
        return;
    }

    @Override
    public void run () {
        // 读时间
        String stringTime = farmer.Io.read (stringCooldownTimerFileName)
                                     .trim ();
        long longTime = Long.valueOf (stringTime)
                            .longValue ();
        // 加两小时
        longTime += 2 * farmer.Time.longOneHour;
        // 加入定时任务
        timer.schedule (new XSell (), new java.util.Date (longTime));
        // 写入计时文件
        farmer.Io.write (stringCooldownTimerFileName, String.valueOf (longTime));
        /* 开始本次任务 */
        // dota2改价
        new dota2.action.sell.AllChangingPrice ()
                             .run ();
        System.out.flush ();
        // csgo改价
        new csgo.action.sell.AllChangingPrice ()
                            .run ();
        System.out.flush ();
        /* 本次任务完成 */
        // 提示下次任务的时间
        stringTime
            = java.time.LocalDateTime.ofEpochSecond (longTime / 1000L,
                                                     0,
                                                     java.time.ZoneOffset.of ("+8"))
                                     .toString ();
        System.out.print ("\n下次任务开始于: ");
        System.out.print (stringTime);
        System.out.flush ();
    }
}

class Activate extends java.util.TimerTask {

    static java.util.Timer timer = new java.util.Timer (true);

    public static void start () {
        timer.schedule (new Activate (), new java.util.Date (System.currentTimeMillis () + 2000L));
        return;
    }

    @Override
    public void run () {
        new farmer.Http ()
                  .setStringUrl ("https://buff.163.com/")
                  .setStringCookie (buff.Global.stringCookie)
                  .request ();
        timer.schedule (new Activate (),
                        new java.util.Date (System.currentTimeMillis () + 20 * farmer.Time.longOneMinute));
    }
}
