package com.sixin.police.market.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by malia on 2017/6/1 15:17.
 *
 * @project SixinIMAndroidProject
 * @package：com.sixinfor.common.utils
 * @describe：集合帮助类
 * @change
 * @chang time
 */

public class ListHelper {

    public static <T> List<T> removeDuplicateList(List<T> list) {
        // 新定义一个集合
        List<T> al = new ArrayList<T>();

        Iterator<T> it = list.iterator();

        while (it.hasNext()) {
            T t = it.next();
            // contains(), remove()方法底层依赖的都是equals()方法, 请查看JDK源代码
            if (!al.contains(t)) {
                al.add(t);
            }
        }
        return al;
    }

    /**
     * 删除重复元素，并保持顺序
     * 无效
     *
     * @param list
     */
    public static <T> List<T> removeDuplicateWithOrder(List<T> list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object element = iter.next();
            if (set.add(element)) {
                newList.add(element);
            }
        }
        list.clear();
        if (list.addAll(newList)) {
            return list;
        }
        return list;
    }

    /**
     * 去除list中重复的值
     *
     * @param li
     * @return
     */
    public static <T> List<T> getNewList(List<T> li) {
        List<T> list = new ArrayList<T>();
        for (int i = 0; i < li.size(); i++) {
            T str = li.get(i);  //获取传入集合对象的每一个元素
            if (!list.contains(str)) {   //查看新集合中是否有指定的元素，如果没有则加入
                list.add(str);
            }
        }
        return list;  //返回集合
    }

    /**
     * 通过HashSet删除重复数据
     *
     * @param list
     */
    public static List removeDuplicate(List list) {
        HashSet hashSet = new HashSet(list);
        list.clear();
        list.addAll(hashSet);
        return list;
    }

    /**
     * 通过循环进行删除重复数据
     * @param list
     */
    public static List removeDuplicateCycle(List list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    /**
     * 删除Arraylist中值为"**"的元素
     * 这种方式：
     * 不管值为"**"的元素在list中是否连续，都可以把值为"**"的元素全部删除
     * 需保证没有其他线程同时在修改
     */
    public static <T> List<T> removeListElement (List<T> list, String fUserId, String fUserName) {
        Iterator<T> iterator = list.iterator();
        while(iterator.hasNext()) {
            T t = iterator.next();
            String json = Convert.toJson(t);
            if(json.contains(fUserId) && json.contains(fUserName)) {
                iterator.remove();
            }
        }
        return list;
    }

    /**
     * 对List对象按照某个成员变量进行排序
     *
     * @param list List对象
     * @param sortField 排序的属性名称
     * @param sortMode 排序方式：ASC 升序排列，DESC 降序排列 任选其一
     * @param <T>
     */
    public static <T> void sortList(List<T> list, final String sortField, final String sortMode) {
        if(list == null || list.size() < 2) {
            return;
        }
        Collections.sort(list, new Comparator<T>() {

            @Override
            public int compare(T o1, T o2) {
                try {
                    Class clazz = o1.getClass();
                    Field field = clazz.getDeclaredField(sortField); //获取成员变量
                    field.setAccessible(true); //设置成可访问状态
                    String typeName = field.getType().getName().toLowerCase(); //转换成小写

                    Object v1 = field.get(o1); //获取field的值
                    Object v2 = field.get(o2); //获取field的值

                    boolean ASC_order = (sortMode == null || "ASC".equalsIgnoreCase(sortMode));

                    //判断字段数据类型，并比较大小
                    if(typeName.endsWith("string")) {
                        String value1 = v1.toString();
                        String value2 = v2.toString();
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
                    }
                    else if(typeName.endsWith("short")) {
                        Short value1 = Short.parseShort(v1.toString());
                        Short value2 = Short.parseShort(v2.toString());
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
                    }
                    else if(typeName.endsWith("byte")) {
                        Byte value1 = Byte.parseByte(v1.toString());
                        Byte value2 = Byte.parseByte(v2.toString());
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
                    }
                    else if(typeName.endsWith("char")) {
                        Integer value1 = (int)(v1.toString().charAt(0));
                        Integer value2 = (int)(v2.toString().charAt(0));
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
                    }
                    else if(typeName.endsWith("int") || typeName.endsWith("integer")) {
                        Integer value1 = Integer.parseInt(v1.toString());
                        Integer value2 = Integer.parseInt(v2.toString());
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
                    }
                    else if(typeName.endsWith("long")) {
                        Long value1 = Long.parseLong(v1.toString());
                        Long value2 = Long.parseLong(v2.toString());
                        //PartyLog.e("getTime desc");
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
                    }
                    else if(typeName.endsWith("float")) {
                        Float value1 = Float.parseFloat(v1.toString());
                        Float value2 = Float.parseFloat(v2.toString());
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
                    }
                    else if(typeName.endsWith("double")) {
                        Double value1 = Double.parseDouble(v1.toString());
                        Double value2 = Double.parseDouble(v2.toString());
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
                    }
                    else if(typeName.endsWith("boolean")) {
                        Boolean value1 = Boolean.parseBoolean(v1.toString());
                        Boolean value2 = Boolean.parseBoolean(v2.toString());
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
                    }
                    else if(typeName.endsWith("date")) {
                        Date value1 = (Date)(v1);
                        Date value2 = (Date)(v2);
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
                    }
                    else if(typeName.endsWith("timestamp")) {
                        Timestamp value1 = (Timestamp)(v1);
                        Timestamp value2 = (Timestamp)(v2);
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
                    }
                    else {
                        //调用对象的compareTo()方法比较大小
                        Method method = field.getType().getDeclaredMethod("compareTo", new Class[]{field.getType()});
                        method.setAccessible(true); //设置可访问权限
                        int result  = (Integer)method.invoke(v1, new Object[]{v2});
                        return ASC_order ? result : result*(-1);
                    }
                }
                catch (Exception e) {
                    String err = e.getLocalizedMessage();
                    System.out.println(err);
                    e.printStackTrace();
                }

                return 0; //未知类型，无法比较大小
            }
        });
    }

}
