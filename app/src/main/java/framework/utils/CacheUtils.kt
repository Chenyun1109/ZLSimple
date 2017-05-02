package framework.utils

import java.util.*

class CacheUtils private constructor() {

    private val hashMap = HashMap<String, Any>()

    fun put(key: String, value: Any) {
        hashMap.put(key, value)
    }

    operator fun <T> get(key: String): T {
        return hashMap[key] as T
    }

    fun remove(key: String) {
        hashMap.remove(key)
    }

    private object CacheHolder {
        internal val CACHE_UITLS = CacheUtils()
    }

    companion object {

        val instance: CacheUtils
            get() = CacheHolder.CACHE_UITLS
    }
}
