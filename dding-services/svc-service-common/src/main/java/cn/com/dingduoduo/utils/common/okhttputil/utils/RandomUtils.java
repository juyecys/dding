package cn.com.dingduoduo.utils.common.okhttputil.utils;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机数工具类
 *
 * @author Nate
 */
public class RandomUtils {

    public static int nextInt() {
        return ThreadLocalRandom.current().nextInt();
    }

    public static int nextInt(int n) {
        return ThreadLocalRandom.current().nextInt(n);
    }

    /**
     * 指定范围内随机一个整数，包含origin，不包含bound
     *
     * @param origin
     * @param bound
     * @return
     */
    public static int nextInt(int origin, int bound) {
        return ThreadLocalRandom.current().nextInt(origin, bound);
    }

    public static void main(String args[]) {
        System.out.println(nextInt(3, 20));
    }

}
