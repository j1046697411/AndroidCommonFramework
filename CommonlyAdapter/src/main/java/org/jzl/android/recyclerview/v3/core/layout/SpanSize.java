package org.jzl.android.recyclerview.v3.core.layout;

import org.jzl.android.recyclerview.v3.model.ISpanSizable;

import java.io.Serializable;

public enum SpanSize implements ISpanSizable, Serializable {

    ALL {
        @Override
        public int getSpanSize(int spanCount) {
            return spanCount;
        }
    }, HALF {
        @Override
        public int getSpanSize(int spanCount) {
            return Math.max(1, spanCount / 2);
        }
    }, THIRD {
        @Override
        public int getSpanSize(int spanCount) {
            return Math.max(1, spanCount / 3);
        }
    }, QUARTER {
        @Override
        public int getSpanSize(int spanCount) {
            return Math.max(1, spanCount / 4);
        }
    }, ONE {
        @Override
        public int getSpanSize(int spanCount) {
            return Math.min(spanCount, 1);
        }
    }, TWO {
        @Override
        public int getSpanSize(int spanCount) {
            return Math.min(spanCount, 2);
        }
    }, THREE {
        @Override
        public int getSpanSize(int spanCount) {
            return Math.min(spanCount, 3);
        }
    }

}