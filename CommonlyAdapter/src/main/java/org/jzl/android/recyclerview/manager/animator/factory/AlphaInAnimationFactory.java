package org.jzl.android.recyclerview.manager.animator.factory;

import android.animation.Animator;
import android.animation.ObjectAnimator;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.manager.animator.AnimatorFactory;

public class AlphaInAnimationFactory<VH extends RecyclerView.ViewHolder> implements AnimatorFactory<VH> {

    private final float mFromValue;
    private final float mToValue;

    private AlphaInAnimationFactory(float mFromValue, float mToValue) {
        this.mFromValue = mFromValue;
        this.mToValue = mToValue;
    }

    @Override
    public Animator animator(VH holder) {
        return ObjectAnimator.ofFloat(holder.itemView, "alpha", mFromValue, mToValue);
    }

    public static <VH extends RecyclerView.ViewHolder> AlphaInAnimationFactory<VH> of(float mFromValue, float mToValue){
        return new AlphaInAnimationFactory<>(mFromValue, mToValue);
    }

    public static <VH extends RecyclerView.ViewHolder> AlphaInAnimationFactory<VH> of(){
        return of(0, 1);
    }

}
