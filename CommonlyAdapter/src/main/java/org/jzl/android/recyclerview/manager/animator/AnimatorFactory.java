package org.jzl.android.recyclerview.manager.animator;

import android.animation.Animator;

import androidx.recyclerview.widget.RecyclerView;

public interface AnimatorFactory<VH extends RecyclerView.ViewHolder> {

    Animator animator(VH holder);

}
