package com.example.thirdapp.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * �������
 *
 * @author dds
 */
public final class DateUtil implements Serializable {

    private static final long serialVersionUID = -3098985139095632110L;

    private DateUtil() {
    }

    /**
     * ��ʽ�������ַ���
     *
     * @param sdate  �����ַ���
     * @param format ��ʽ
     * @return
     */
    public static String dateFormat(String sdate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        java.sql.Date date = java.sql.Date.valueOf(sdate);
        String dateString = formatter.format(date);

        return dateString;
    }

    /**
     * �������ڼ������
     *
     * @param sd ��ʼ����
     * @param ed ��������
     * @return
     */
    public static long getIntervalDays(String sd, String ed) {
        return ((java.sql.Date.valueOf(ed)).getTime() - (java.sql.Date
                .valueOf(sd)).getTime()) / (3600 * 24 * 1000);
    }

    /**
     * ���ؼ���·�
     *
     * @param beginMonth
     * @param endMonth
     * @return
     */
    public static int getInterval(String beginMonth, String endMonth) {
        int intBeginYear = Integer.parseInt(beginMonth.substring(0, 4));
        int intBeginMonth = Integer.parseInt(beginMonth.substring(beginMonth
                .indexOf("-") + 1));
        int intEndYear = Integer.parseInt(endMonth.substring(0, 4));
        int intEndMonth = Integer.parseInt(endMonth.substring(endMonth
                .indexOf("-") + 1));

        return ((intEndYear - intBeginYear) * 12)
                + (intEndMonth - intBeginMonth) + 1;
    }

    /**
     * ���ظ�ʽ�� ����
     *
     * @param sDate
     * @param dateFormat
     * @return
     */
//    public static Date getDate(String sDate, String dateFormat) {
//        SimpleDateFormat fmt = new SimpleDateFormat(dateFormat);
//        ParsePosition pos = new ParsePosition(0);
//
//        return fmt.parse(sDate, pos);
//    }


    /**
     * ���ص�ǰ���� ��ʱ��
     *
     * @return
     */
    public static String getCurrentDate() {
        return getFormatDateTime(new Date(), "yyyy-MM-dd");
    }

    /**
     * ���ص�ǰ����+ʱ��
     *
     * @return
     */
    public static String getCurrentDateTime() {
        return getFormatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * ��ʽ������ yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getFormatDate(Date date) {
        return getFormatDateTime(date, "yyyy-MM-dd");
    }

    /**
     * ��ʽ����ǰ����
     *
     * @param format ��ʽ��
     * @return
     */
    public static String getFormatDate(String format) {
        return getFormatDateTime(new Date(), format);
    }


    /**
     * ��ʽ��
     *
     * @param date
     * @param format
     * @return
     */
    public static String getFormatDateTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * ��ʱ���ַ���ת�����ƶ���ʽ
     */
    public static String getUndoDate(String date) {
        try {
            if (!StringUtil.isNullOrEmpty(date)) {
                String year = date.substring(0, 4);
                String month = date.substring(4, 6);
                String day = date.substring(6, 8);
                String hour = date.substring(8, 10);
                String min = date.substring(10, 12);
                return year + "��" + Integer.parseInt(month) + "��" + Integer.parseInt(day) + "�� " + hour + ":" + min;
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * �������ڻ�ú���ֵ
     * @param str
     * @return
     */
    public static long getMs(String str, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(str);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getDate(String time, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date(Long.parseLong(time));
        return sdf.format(date);
    }

}