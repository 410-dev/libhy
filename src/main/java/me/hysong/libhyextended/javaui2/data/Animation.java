package me.hysong.libhyextended.javaui2.data;

import lombok.Getter;

import java.util.ArrayList;

public class Animation {
    public static Integer LOOP_INFINITE = -1;

    @Getter private ArrayList<Integer> loops = new ArrayList<>();
    @Getter private ArrayList<Thread> animateFunction = new ArrayList<>();

    public Animation add(Integer loop, Thread t) {
        this.loops.add(loop);
        this.animateFunction.add(t);
        return this;
    }

    public Animation add(Thread t) {
        this.add(1, t);
        return this;
    }

}
