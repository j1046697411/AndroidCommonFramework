package org.jzl.android.recyclerview.util.datablock;

public enum PositionType {
    HEADER(0), CONTENT(1), FOOTER(2);

    public final int sequence;

    PositionType(int sequence) {
        this.sequence = sequence;
    }
}
