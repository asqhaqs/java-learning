package com.brianway.learning.java8.serializable;

import java.io.*;

/**
 * 测试序列化和反序列化
 * Create by xudong
 * Date: 2019-03-14
 */
public class ObjSerializeAndDeserializeTest {

    public static void main(String[] args) {

        //serializePerson();
        deserializePerson();
    }

    /**
     * 序列化Person对象，将其存到 D:\\ world.txt
     */
    private static void serializePerson(){
        Person person = new Person();
        person.setAge(1);
        person.setHobby("oh ha ha");
        person.setName("my boy");
        person.setSex("man");

        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream( new FileOutputStream("D:\\world.txt"));
            outputStream.writeObject(person);
            System.out.println("序列化成功");
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                outputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     *  执行反序列化过程生产Person对象
     */
    private static Person deserializePerson(){
        Person person = null;
        ObjectInputStream inputStream = null;

        try{
            inputStream = new ObjectInputStream(new FileInputStream("D:\\world.txt"));
            person = (Person) inputStream.readObject();
            System.out.println(person.getName());
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            try{
                inputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
            return person;
        }
    }

}
