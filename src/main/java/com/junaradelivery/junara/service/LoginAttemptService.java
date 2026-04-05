package com.junaradelivery.junara.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS = 5;
    private static final long BLOCK_DURATION_SECONDS = 15 * 60; // 15 minutes

    private record AttemptRecord(int count, Instant blockedUntil) {
    }

    private final Map<String, AttemptRecord> attempts = new ConcurrentHashMap<>();

    public void registerFailure(String key) {
        attempts.compute(key, (k, rec) -> {
            int count = (rec == null) ? 1 : rec.count() + 1;
            Instant blockedUntil = (count >= MAX_ATTEMPTS)
                    ? Instant.now().plusSeconds(BLOCK_DURATION_SECONDS)
                    : (rec != null ? rec.blockedUntil() : null);
            return new AttemptRecord(count, blockedUntil);
        });
    }

    public void registerSuccess(String key) {
        attempts.remove(key);
    }

    public boolean isBlocked(String key) {
        AttemptRecord rec = attempts.get(key);
        if (rec == null || rec.blockedUntil() == null)
            return false;
        if (Instant.now().isAfter(rec.blockedUntil())) {
            attempts.remove(key);
            return false;
        }
        return true;
    }
}
