package com.hoyt_tian.dwalf.event;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class EventLoopImpl<E extends Event<T>, T> implements EventLoop<E, T>{

    private final BlockingQueue<E> queue = new LinkedBlockingQueue<>();
    private Map<T, LinkedList<Consumer<E>>> subscribeMap = new HashMap<>();

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void enqueue(E data) throws InterruptedException {
        queue.put(data);
    }

    @Override
    public E take() throws InterruptedException {
        return queue.take();
    }


    @Override
    public boolean subscribe(T type, Consumer<E> handler) {
        synchronized (subscribeMap) {
            if (subscribeMap.containsKey(type)) {
                LinkedList<Consumer<E>> linkedList = subscribeMap.get(type);
                Optional<Consumer<E>> optionalCallable = linkedList.stream().filter(callable -> callable == handler).findAny();
                if (optionalCallable.isPresent()) {
                    return false;
                }
                linkedList.add(handler);
                return true;
            } else {
                LinkedList<Consumer<E>> linkedList = new LinkedList<>();
                linkedList.add(handler);
                subscribeMap.put(type, linkedList);
                return true;
            }
        }
    }

    @Override
    public void start() {
        executorService.submit(() -> {
            try {
                E event = EventLoopImpl.this.take();
                if (subscribeMap.containsKey(event.getType())) {
                    LinkedList<Consumer<E>> linkedList = subscribeMap.get(event.getType());
                    for(Consumer<E> consumer : linkedList) {
                        consumer.accept(event);
                        if (event.isStopped()) {
                            break;
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}
