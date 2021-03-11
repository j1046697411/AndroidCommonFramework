package org.jzl.android.recyclerview.manager.animator.factory;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.manager.animator.AnimatorFactory;

public class ScaleInAnimationFactory<VH extends RecyclerView.ViewHolder> implements AnimatorFactory<VH> {

    private final ScaleType scaleType;
    private final float fromValue;

    private ScaleInAnimationFactory(ScaleType scaleType, float fromValue) {
        this.scaleType = scaleType;
        this.fromValue = fromValue;
    }

    @Override
    public Animator animator(VH holder) {
        switch (scaleType){
            case X:{
                return ObjectAnimator.ofFloat(holder.itemView, "scaleX", fromValue, 1);
            }
            case Y:{
                return ObjectAnimator.ofFloat(holder.itemView, "scaleY", fromValue, 1);
            }
            default:{
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(
                        ObjectAnimator.ofFloat(holder.itemView, "scaleX", fromValue, 1),
                        ObjectAnimator.ofFloat(holder.itemView, "scaleY", fromValue, 1)
                );
                return animatorSet;
            }
        }
    }

    public enum ScaleType {
        X, Y, XY
    }

    public static <VH extends RecyclerView.ViewHolder> ScaleInAnimationFactory<VH> of(ScaleType scaleType, float fromValue){
        return new ScaleInAnimationFactory<>(scaleType, fromValue);
    }

    public static <VH extends RecyclerView.ViewHolder> ScaleInAnimationFactory<VH> of(float fromValue){
        return of(ScaleType.XY, fromValue);
    }

}
