package aspects.caching;

import java.util.HashMap;

public aspect UserCachingAspect extends SimpleCachingAspect {

    public UserCachingAspect() {
        setCache(new HashMap());
    }

    pointcut cachedOperation(Object key): execution(* dao.daoimpl.UserDaoImpl.select*(*))
            && !execution(* dao.daoimpl.UserDaoImpl.selectAll())
            && args(key);

    pointcut resetCache(): execution(* dao.daoimpl.UserDaoImpl.update(..));

}
