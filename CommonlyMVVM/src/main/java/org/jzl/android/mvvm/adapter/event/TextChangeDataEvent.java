package org.jzl.android.mvvm.adapter.event;

public class TextChangeDataEvent implements Event {

    public final CharSequence text;
    public final int start;
    public final int before;
    public final int count;

    private TextChangeDataEvent(CharSequence text, int start, int before, int count) {
        this.text = text;
        this.start = start;
        this.before = before;
        this.count = count;
    }

    public static TextChangeDataEvent of(CharSequence text, int start, int before, int count){
        return new TextChangeDataEvent(text, start, before, count);
    }
}
