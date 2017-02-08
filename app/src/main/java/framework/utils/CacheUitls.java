package framework.utils;

import java.util.HashMap;

public class CacheUitls {

    private final HashMap<String, Object> hashMap = new HashMap<>();

    private static class CacheHolder {
        static final CacheUitls CACHE_UITLS = new CacheUitls();
    }

    private CacheUitls() {
    }


    public static CacheUitls getInstance() {
        return CacheHolder.CACHE_UITLS;
    }

    public void put(String key, Object value) {
        hashMap.put(key, value);
    }

    public <T> T get(String key) {
        //noinspection unchecked
        return (T) hashMap.get(key);
    }

    public void remove(String key) {
        hashMap.remove(key);
    }
}
