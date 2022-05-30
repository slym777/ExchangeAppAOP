package aspects.caching;

import java.util.Map;
import java.util.logging.Logger;

public abstract aspect SimpleCachingAspect {
    private static Logger logger = Logger.getLogger(SimpleCachingAspect.class.getName());

    private Map cache;

    public void setCache(Map cache) { this.cache = cache; }

    abstract pointcut cachedOperation(Object key);

    abstract pointcut resetCache();

    Object around(Object key) : cachedOperation(key) {
        Object ret;
        CacheKey cacheKey = new CacheKey(thisJoinPoint);
        if (cache.containsKey(cacheKey)) {
            ret = cache.get(cacheKey);
            logger.info("Value for " + thisJoinPoint.getSignature() + " with params: " + key + " was returned from cache");
        } else {
            ret = proceed(key);
            cache.put(cacheKey,ret);
        }
        return ret;
    }

    after(): resetCache() {
       cache.clear();
    }


}