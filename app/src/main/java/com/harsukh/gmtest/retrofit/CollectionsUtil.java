package com.harsukh.gmtest.retrofit;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by harsukh on 4/3/17.
 */

public class CollectionsUtil {
    public static <T> Collection<T> filter(Collection<T> target, IPredicate<T> predicate) {
        Collection<T> result = new ArrayList<T>();
        for (T element : target) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }
        return result;
    }

    public interface IPredicate<T> {
        boolean apply(T type);
    }
}
