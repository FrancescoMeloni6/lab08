package it.unibo.deathnote.impl;

public class Pair<T1, T2> {
    private T1 first;
    private T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public Pair() {
        this(null, null);
    }

    public T1 getFirst() {
        return this.first;
    }
    public void setFirst(T1 first) {
        this.first = first;
    }
    public T2 getSecond() {
        return this.second;
    }
    public void setSecond(T2 second) {
        this.second = second;
    }
}
