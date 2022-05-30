package aspects.caching;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Arrays;
import java.util.Objects;

public class CacheKey {
    private final String signature;
    private final Object[] args;

    public CacheKey(JoinPoint pjp) {
        signature = pjp.getSignature().toString();
        args = pjp.getArgs();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheKey cacheKey = (CacheKey) o;
        return Objects.equals(signature, cacheKey.signature) && Arrays.equals(args, cacheKey.args);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(signature);
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }
}
