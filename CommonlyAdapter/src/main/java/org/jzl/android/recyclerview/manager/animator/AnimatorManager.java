package org.jzl.android.recyclerview.manager.animator;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.view.animation.LinearInterpolator;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.Component;
import org.jzl.android.recyclerview.core.configuration.Configuration;
import org.jzl.android.recyclerview.core.configuration.ConfigurationBuilder;
import org.jzl.android.recyclerview.manager.AbstractManager;
import org.jzl.android.recyclerview.manager.listener.OnViewAttachedToWindowListener;
import org.jzl.lang.util.ObjectUtils;

public class AnimatorManager<T, VH extends RecyclerView.ViewHolder> extends AbstractManager<T, VH> implements Component<T, VH>, OnViewAttachedToWindowListener<T, VH> {

    private static final LinearInterpolator INTERPOLATOR = new LinearInterpolator();

    private boolean enableAnimator = true;
    private long duration = 400;
    private long startDelay = 0;
    private TimeInterpolator interpolator = INTERPOLATOR;
    private int lastPosition = -1;
    private final AnimatorFactory<VH> animatorFactory;

    AnimatorManager(AnimatorFactory<VH> animatorFactory) {
        this.animatorFactory = animatorFactory;
    }

    @Override
    public void setup(ConfigurationBuilder<T, VH> builder) {
        super.setup(builder);
        builder.addOnViewAttachedToWindowListener(this);
    }

    @Override
    public void initialise(RecyclerView recyclerView, Configuration<T, VH> configuration) {
    }

    @Override
    public void onViewAttachedToWindow(Configuration<T, VH> configuration, VH holder) {
        if (lastPosition < holder.getAdapterPosition() && isEnableAnimator()) {
            Animator animator = animatorFactory.animator(holder);
            animator.setDuration(duration);
            animator.setStartDelay(startDelay);
            animator.setInterpolator(interpolator);
            animator.start();
            this.lastPosition = holder.getAdapterPosition();
        }
    }

    private boolean isEnableAnimator(){
        return enableAnimator;
    }

    public AnimatorManager<T, VH> setAnimatorFactory(AnimatorFactory<VH> animatorFactory){
        return this;
    }

    public AnimatorManager<T, VH> setDuration(long duration){
        this.duration = duration;
        return this;
    }

    public AnimatorManager<T, VH> setStartDelay(long startDelay){
        this.startDelay = startDelay;
        return this;
    }

    public AnimatorManager<T, VH> setInterpolator(TimeInterpolator interpolator){
        this.interpolator = ObjectUtils.get(interpolator, this.interpolator);
        return this;
    }

    public AnimatorManager<T, VH> enableAnimator(boolean enableAnimator){
        this.enableAnimator = enableAnimator;
        return this;
    }

    public static <T, VH extends RecyclerView.ViewHolder> AnimatorManager<T, VH> of(AnimatorFactory<VH> animatorFactory){
        return new AnimatorManager<T, VH>(animatorFactory);
    }

}
