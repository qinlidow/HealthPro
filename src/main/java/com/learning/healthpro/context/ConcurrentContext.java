package com.learning.healthpro.context;

public class ConcurrentContext {

    private final static ThreadLocal<Integer> context = new ThreadLocal<>();

    public static Integer get() {
        return context.get();
    }

    public static void set(Integer id) {
        context.set(id);
    }

    public static  void remove(){
        context.remove();
    }
}
