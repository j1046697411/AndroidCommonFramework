package org.jzl.android.recyclerview.manager.animator.factory;

import android.animation.Animator;
import android.animation.ObjectAnimator;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.manager.animator.AnimatorFactory;

public class SlideInLeftAnimationFactory<VH  extends RecyclerView.ViewHolder> implements AnimatorFactory<VH> {

    private SlideInLeftAnimationFactory(){
    }

    @Override
    public Animator animator(VH holder) {
        return ObjectAnimator.ofFloat(holder.itemView, "translationX", -holder.itemView.getMeasuredWidth(), 0);
    }

    public static <VH extends RecyclerView.ViewHolder> SlideInLeftAnimationFactory<VH> of(){
        return new SlideInLeftAnimationFactory<>();
    }

}
