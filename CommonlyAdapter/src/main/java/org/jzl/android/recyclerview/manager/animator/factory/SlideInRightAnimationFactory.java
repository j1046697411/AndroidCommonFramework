package org.jzl.android.recyclerview.manager.animator.factory;

import android.animation.Animator;
import android.animation.ObjectAnimator;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.manager.animator.AnimatorFactory;

public class SlideInRightAnimationFactory<VH extends RecyclerView.ViewHolder> implements AnimatorFactory<VH> {

    private SlideInRightAnimationFactory() {
    }

    @Override
    public Animator animator(VH holder) {
        return ObjectAnimator.ofFloat(holder.itemView, "translationX", holder.itemView.getMeasuredWidth(), 0);
    }

    public static <VH extends RecyclerView.ViewHolder> SlideInRightAnimationFactory<VH> of() {
        return new SlideInRightAnimationFactory<>();
    }

}
