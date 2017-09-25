package hive.UDF;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class AccordTimezoneFormatTimestamp extends UDF {

    private SimpleDateFormat formatter;     // 创建Java中格式化对象
    private Text result = new Text();   // 输出结果
    private Text lastFormat = new Text();   // 接收传入的时间格式，可以减少对象的创建
    private Text lastTimezone = new Text();     // 接收传入的时区，可以减少时区的限制
    private Text defaultFormat = new Text("yyyy-MM-dd HH:mm:ss");   // 定义默认输出的日期格式


    /**
     * 方法重载，将传入的时间戳转换为指定格式的字符串,时区为指定的时区
     * @param unixtime 1970-01-01 00:00:00开始的毫秒值
     * @param format 传入的时间格式，Java中SimpleDateFormat支持的格式均可以传入，
     *               详见：
     *               http://java.sun.com/j2se/1.4.2/docs/api/java/text/SimpleDateFormat.html
     * @param timezone 传入的时区，凡是Java中支持的时区都支持，比如GMT+08
     * @return  指定时区指定格式的日期
     */
    public Text evaluate(LongWritable unixtime, Text format , Text timezone){
        if (unixtime == null || timezone == null || format == null) {
            return null;
        }

        return eval(unixtime.get(), format, timezone);
    }


    /**
     * 方法重载，将传入的时间戳转换为指定格式的字符串，默认时区为JVM的本地时区
     *
     * @param unixtime 输入的是时间戳，毫秒值。从1970年1月1日 00:00:00开始的时间戳
     * @param format   Java中SimpleDateFormat支持的格式均可以传入
     *                 详见：
     *                 http://java.sun.com/j2se/1.4.2/docs/api/java/text/SimpleDateFormat
     *                 .html
     * @return 格式化的的字符串
     */
    public Text evaluate(LongWritable unixtime, Text format) {
        if (unixtime == null || format == null) {
            return null;
        }

        return eval(unixtime.get(), format);
    }


    /**
     * 根据传入的1970-01-01 00:00:00开始的毫秒值，转换为默认格式，默认时区的时间输出
     *
     * @param unixtime 1970-01-01 00:00:00开始的毫秒值
     * @return 默认格式，默认时区的时间输出
     */
    public Text evaluate(LongWritable unixtime) {
        if (unixtime == null) {
            return null;
        }

        return eval(unixtime.get(), defaultFormat);
    }


    /**
     * 根据传入的1970-01-01 00:00:00开始的毫秒值，转换为默认格式，默认时区的时间输出
     * @param unixtime 1970-01-01 00:00:00开始的毫秒值
     * @return 默认格式，默认时区的时间输出
     */
    public Text evaluate(IntWritable unixtime) {
        return evaluate(unixtime, defaultFormat);
    }

    /**
     * 方法重载，将出入的时间戳转换为指定格式的日期，时间戳起始于1970-01-01 00:00:00的毫秒值
     *
     * @param unixtime 时间戳，1970-01-01 00:00:00开始的毫秒值
     * @param format   日期格式，详见：
     *                 http://java.sun.com/j2se/1.4.2/docs/api/java/text/SimpleDateFormat
     *                 .html
     * @return 将毫秒值转换为指定格式的时间，时区为系统本地时区。
     */


    public Text evaluate(IntWritable unixtime, Text format) {
        if (unixtime == null || format == null) {
            return null;
        }
        // 直接将int类型当成long类型来使用,居然可以？！Java还是要多学习啊。
        return eval(unixtime.get(), format);
    }


    /**
     * 方法重载，传入的1970-01-01 00:00:00开始的毫秒值转为指定格式，指定时区的时间
     *
     * @param unixtime 传入1970-01-01 00:00:00开始的毫秒值
     * @param timezone 要转换到的时区，凡是Java中支持的时区格式，这里都支持，比如GMT+08
     * @param format   转换的格式
     * @return 将时间戳的值转换为指定时区，指定格式的时间
     */

    public Text evaluate(IntWritable unixtime, Text format, Text timezone) {
        if (unixtime == null || timezone == null || format == null) {
            return null;
        }

        return eval(unixtime.get(), format, timezone);
    }


    /**
     * 方法重载，将给定的来至1970-01-01 00:00:00的时间戳转换为指定格式的日期
     *
     * @param unixtime 来自于1970-01-01 00:00:00的时间戳
     * @param format   指定格式的日期，详见：
     *                 http://java.sun.com/j2se/1.4.2/docs/api/java/text
     *                 /SimpleDateFormat.html
     * @return 指定格式的日期字符串
     */
    private Text eval(long unixtime, Text format) {

        /** 这个设计还是挺有心机的，只需判断一次，不用
         * 每调一次该方法就创建一个新的SimpleDateFormat对象*/

        if (!format.equals(lastFormat)) {
            formatter = new SimpleDateFormat(format.toString());
            lastFormat.set(format);
        }

        // 将时间戳转换为指定格式
        Date date = new Date(unixtime);
        result.set(formatter.format(date));
        return result;
    }


    /**
     * 方法重载，根据传入的时区，将时间戳转换为相应的格式
     *
     * @param unixtime 给定的具体到毫秒的时间戳
     * @param timezone 时区，字符串。采用GMT+08，GMT-08的形式。其实Java中timezone支持的时区格式，他都支持。
     * @param format   Java中SimpleDateFormat类支持的时间格式他都支持。详见：
     *                 http://java.sun.com/j2se/1.4.2/docs/api/java/text/SimpleDateFormat.html
     * @return 返回的指定格式，指定时区的日期字符串
     */
    private Text eval(long unixtime, Text format, Text timezone) {
        if (!format.equals(lastFormat) || !timezone.equals(lastTimezone)) {       // 判断传入的转换格式是否是我们需要的转换格式，如果不是，则转换为指定格式
            formatter = new SimpleDateFormat(format.toString());        // 根据日期格式创建新的SimpleDateFormat对象
            formatter.setTimeZone(TimeZone.getTimeZone(timezone.toString()));   // 获取传入的时区，并进行设置
            lastFormat.set(format);     // 重新设置时间格式
            lastTimezone.set(timezone);     // 重新设置时区
        }

        Date date = new Date(unixtime);     // 根据时间戳获取Date对象
        result.set(formatter.format(date));     // 进行格式和时区的转换
        return result;
    }
}
