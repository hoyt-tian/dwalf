package com.hoyt_tian.dwalf.event;

import java.util.function.Consumer;

public interface EventLoop<E extends Event<T>, T> {

    public void enqueue(E data) throws InterruptedException;

    E take() throws InterruptedException;

    boolean subscribe(T type, Consumer<E> handler);

    void start();
}
