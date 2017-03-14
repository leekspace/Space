package com.leekli.demo.javase.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author liwei
 * @Date 2017年3月9日 下午2:26:12
 * @Desc
 */
public class ArrayListDemo {
    public static void main(String[] args) {

        Comparator();

    }

    static void Comparator(){
        List<Student> list = new ArrayList<Student>();
        list.add(new Student("e",13));
        list.add(new Student("a",12));
        list.add(new Student("b",12));
        list.add(new Student("c",13));
        list.add(new Student("f",13));
        list.add(new Student("g",12));
        list.add(new Student("h",14));
        
        Collections.sort(list, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getAge()>o2.getAge()?1:-1;
            }
        });
        System.out.println(list);
    }
    
    
    /**
     * 不要在 foreach 循环里进行元素的 remove / add 操作。 remove 元素请使用 Iterator 方式，
     * 如果并发操作，需要对 Iterator 对象加锁。
     */
    static void foreach() {
        List<String> a = new ArrayList<String>();
        a.add("1");
        a.add("2");
        for (String temp : a) {
            if ("1".equals(temp)) {
                a.remove(temp);
            }
        }
        System.out.println(a);

        // 正例：
        Iterator<String> it = a.iterator();
        while (it.hasNext()) {
            String temp = it.next();
            if ("1".equals(temp)) {
                it.remove();
            }
        }
        System.out.println(a);
    }

    /**
     * extends 可用于的返回类型限定，不能用于参数类型限定。 super 可用于参数类型限定，不能用于返回类型限定。
     * 带有super超类型限定的通配符可以向泛型对易用写入，带有extends子类型限定的通配符可以向泛型对象读取
     */
    static void extendsTest() {
        // ==============================================================extends===========================
        Apple a = new Apple();
        List<? extends Fruit> flist = new ArrayList<Apple>();
        // complie error: 不可向返回的<? extends Fruit>对象添加元素
        // flist.add(new Apple());
        // flist.add(new Fruit());
        // flist.add(new Object());

        // ==============================================================supper===========================

        List<? super Fruit> list = new ArrayList<Fruit>();
        list.add(new Fruit());
        list.add(new Apple());
        list.add(new RedApple());

        // compile error: 不可用于返回类型的限定
        // List<? super Fruit> flist3 = new ArrayList<Apple>();

        // compile error: 不可读取
        // Fruit item = list.get(0);

    }

    /**
     * 使用工具类 Arrays . asList() 把数组转换成集合时，不能使用其修改集合相关的方 法，它的 add / remove / clear
     * 方法会抛出 UnsupportedOperationException 异常
     * 
     * 说明：asList 的返回对象是一个 Arrays 内部类，并没有实现集合的修改方法。 Arrays .
     * asList体现的是适配器模式，只是转换接口，后台的数据仍是数组
     */
    static void asList() {
        String[] str = new String[] { "a", "b" };
        List<String> list = Arrays.asList(str);

        try {
            list.add("c");// 异常
        } catch (Exception e) {
            e.printStackTrace();
        }
        str[0] = "aa";
        System.out.println(list.get(0));// 也输出aa，证明了aslist是适配器模式，并非生成新的数组。

    }

    /**
     * 集合转数组 list或set 转array 使用集合转数组的方法，必须使用集合的toArray(T[]
     * array),传入的类型完全一样的数组。大小是list.size();
     * 
     * @return
     */
    static void setToArray() {
        Set<String> set = new HashSet<String>();
        set.add("1");
        set.add("2");

        // 1 需要强转
        Object[] array1 = set.toArray();
        // 2
        String[] array2 = new String[set.size()];
        set.toArray(array2);
        // 打印数组
        System.out.println(Arrays.toString(array1));
        System.out.println(Arrays.toString(array2));

        List<String> list = new ArrayList<String>(2);
        list.add("guan");
        list.add("bao");
        String[] array = new String[list.size()];
        array = list.toArray(array);
        System.out.println(Arrays.toString(array));
    }

    /**
     * ArrayList的sublist 结果不可强转成ArrayList,否则会抛出 ClassCastException异常。 SubList
     * 返回的是ArrayList的一个视图。对于subList 子列表的所有操作都会反应到ArrayList原列表上。
     */
    void subList() {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        List<String> subList = list.subList(0, 1);
        subList.remove(0);
        System.out.println(list);
    }

    static class Food {
    }

    static class Fruit extends Food {
    }

    static class Apple extends Fruit {
    }

    static class RedApple extends Apple {
    }
    static class Student{
        private String name;
        private int age ;
        Student(String name,int age){
            this.name = name;
            this.age = age;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }
        @Override
        public String toString() {
            return "Student [name=" + name + ", age=" + age + "]";
        }
        
    }
}
