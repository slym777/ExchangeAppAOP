package aspects.caching;

import java.util.HashMap;

public aspect CurrencyCachingAspect extends SimpleCachingAspect {

    public CurrencyCachingAspect() {
        setCache(new HashMap());
    }

    pointcut cachedOperation(Object key): execution(* dao.daoimpl.CurrencyDaoImpl.select*(*))
            && !execution(* dao.daoimpl.CurrencyDaoImpl.selectAllCurrencies())
            && args(key);

    pointcut resetCache(): execution(* dao.daoimpl.CurrencyDaoImpl.update(..));

}