package com.tangjianghua.io;

import java.util.concurrent.atomic.AtomicLong;

class Data {
    private AtomicLong count = new AtomicLong(0);
    private AtomicLong amount = new AtomicLong(0);

    public Long addCount(Long value) {
        return count.addAndGet(value);
    }

    public Long addAmount(Long value) {
        return amount.addAndGet(value);
    }

    public AtomicLong getCount() {
        return count;
    }

    public AtomicLong getAmount() {
        return amount;
    }
}