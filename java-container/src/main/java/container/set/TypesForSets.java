package container.set;

import sun.reflect.generics.tree.Tree;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

class SetType {
    int i;
    public SetType(int n){ i =n; }
    // Set 需要实现 equals 方法
    public boolean equals(Object o){
        return o instanceof SetType && (i == ((SetType) o).i);
    }
    public String toString(){
        return Integer.toString(i);
    }
}

class HashType extends SetType{

    public HashType(int n) {
        super(n);
    }
    //Hashset 需要实现hashcode方法
    public int hashCode(){ return i; }

}

class TreeType extends SetType implements Comparable<TreeType>{

    public TreeType(int n) {
        super(n);
    }

    public int compareTo(TreeType arg){
        return (arg.i < i ? -1 : (arg.i == i ? 0 : 1));
    }
}

public class TypesForSets{
    static <T> Set<T> fill(Set<T> set, Class<T> type){
        try{
            for(int i = 0; i < 10; i++){
                set.add(type.getConstructor(int.class).newInstance(i));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return  set;
    }

    static <T> void test(Set<T> set, Class<T> type){
        fill(set, type);
        fill(set, type);  //加入重复的值
        fill(set, type);
        System.out.println(set);
    }

    public static void main(String[] args) {

        //hashSet 保存无序
        test(new HashSet<HashType>(), HashType.class);
        //linkedHashSet 按插入顺序保存
        test(new LinkedHashSet<HashType>(), HashType.class);
        // treeSet按降序保存，取决于compared方法的实现
        test(new TreeSet<TreeType>(), TreeType.class);

        //对于未实现hashCode方法的 setType类，放到HashSet中会产生重复值
        test(new HashSet<SetType>(), SetType.class);
        test(new HashSet<TreeType>(), TreeType.class);
        test(new LinkedHashSet<SetType>(), SetType.class);
        test(new LinkedHashSet<TreeType>(), TreeType.class);


        //在TreeSet中保存未实现Comparable的对象会导致抛出异常
        try{
            test(new TreeSet<SetType>(), SetType.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            test(new TreeSet<HashType>(), HashType.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

