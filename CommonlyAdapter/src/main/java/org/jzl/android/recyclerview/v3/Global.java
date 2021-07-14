package org.jzl.android.recyclerview.v3;

public class Global  {

    public static Global getInstance() {
        return Holder.SIN;
    }

    private Global() {
    }

    static class Holder {
        static final Global SIN = new Global();
    }
}
