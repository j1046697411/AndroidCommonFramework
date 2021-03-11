package org.jzl.android.recyclerview.manager.animator.factory;

import android.animation.Animator;
import android.animation.ObjectAnimator;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.manager.animator.AnimatorFactory;

public class SlideInBottomAnimationFactory<VH extends RecyclerView.ViewHolder> implements AnimatorFactory<VH> {
    private SlideInBottomAnimationFactory() {
    }

    @Override
    public Animator animator(VH holder) {
        return ObjectAnimator.ofFloat(holder.itemView, "translationY", holder.itemView.getMeasuredHeight(), 0);
    }

    public static <VH extends RecyclerView.ViewHolder> SlideInBottomAnimationFactory<VH> of(){
        return new SlideInBottomAnimationFactory<>();
    }

}
