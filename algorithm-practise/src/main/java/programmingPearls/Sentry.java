package programmingPearls;


/**
 * Create by xudong
 * Author: xudong
 * Date: 2019-04-29
 */
public class Sentry {


    //顺序搜索 无哨兵
    public static int ssearch1(int t, int n){
        Integer[] arrayIntN = new Integer[n];
        for(int i = 0; i < n; i++){
            arrayIntN[i] = i;
        }

        long timestart = System.nanoTime();
        for(int i = 0; i < n; i++){
            if(arrayIntN[i] == t){
                long timeend = System.nanoTime();
                System.out.println("双重检查时消耗时间为: " + (timeend-timestart) );
                return i;
            }
        }
        long timeend = System.nanoTime();
        System.out.println("双重检查时消耗时间为: " +  (timeend-timestart) );
        return -1;
    }


    //顺序搜索 有哨兵
    public static int ssearch2(int t, int n){
        Integer[] arrayInt = new Integer[n+1];
        for(int i = 0; i < n; i++){
            arrayInt[i] = i;
        }
        //设置哨兵
        arrayInt[n] = t;
        int hold;
        long timestart = System.nanoTime();
        for(int i = 0; ; i++){
            if(arrayInt[i] == t){
                hold = i;
                break;
            }
        }

        if(hold == n){
            long timeend = System.nanoTime();
            System.out.println("有末位哨兵检查时消耗时间为: " + (timeend-timestart) );
            return -1;
        }else {
            long timeend = System.nanoTime();
            System.out.println("有末位哨兵检查时消耗时间为: " + (timeend-timestart) );
            return hold;
        }
    }


    public static void main(String[] args) {

        int i = Sentry.ssearch1(1000000,10000000);
        int j = Sentry.ssearch2(1000000,10000000);
        System.out.println("i = " + i + "; j = " + j );
    }
}
