package com.ferret.reflection;

public class ReflectionTest1{
    public static void main(String[] args){
        //Foo的实例对象如何表示
        Foo foo1 = new Foo();

        //Foo这个类也是一个实例对象，Class类的实例对象
        //任何一个类都是Class类的实例对象，这个实例对象有3中表示方式。

        //第一种表示方式-->实际上在告诉我们任何一个类都有一个隐含的静态成员变量
        Class c1 = Foo.class;

        //第二种方式，已知该类的对象，通过getClass()方法
        Class c2 = foo1.getClass();

        //c1,c2表示了Foo类的类类型（class type）
        //不管c1还是c2都代表了Foo类的类类型。一个类只可能是Class类的一个实例对象
        System.out.println(c1 == c2);

        //第三种表达方式
        Class c3 = null;
        try{
            c3 = Class.forName("com.ferret.reflection.Foo");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        System.out.println(c2 == c3);

        //我们完全可以通过类的类类型创建该类的对象实例-->通过c1或者c2或者c3创建Foo的实例对象
        try{
            Foo foo = (Foo)c1.newInstance();//需要有无参数的构造方法
        }catch(InstantiationException | IllegalAccessException e){
            e.printStackTrace();
        }
    }
}
class Foo{
    public Foo() {
    }
}