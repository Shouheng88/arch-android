package me.shouheng.utils.data;

/**
 * 用来根据传入的类型得到输出的类型
 *
 * @param <F> 传入类型
 * @param <T> 输出类型
 */
public interface Function<F, T> {

    /**
     * 将输入类型转换成输出类型
     *
     * @param from 输入
     * @return     输出
     */
    T apply(F from);
}
