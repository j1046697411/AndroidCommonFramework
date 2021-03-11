package org.jzl.android.mvvm.adapter;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

import androidx.databinding.BindingAdapter;

import org.jzl.android.mvvm.command.BooleanReplyCommand;
import org.jzl.android.mvvm.command.Command;
import org.jzl.android.mvvm.command.ReplyCommand;
import org.jzl.lang.util.ObjectUtils;

public final class ViewBindingAdapter {

    @BindingAdapter({"bind:click"})
    public static void bindClickCommand(View view, Command command) {
        if (ObjectUtils.isNull(command)) {
            return;
        }
        view.setOnClickListener(v -> command.execute());
    }

    @BindingAdapter({"bind:longClick"})
    public static void bindLongClickCommand(View view, Command command) {
        if (ObjectUtils.isNull(command)) {
            return;
        }
        view.setOnLongClickListener(v -> command.execute());
    }

    @BindingAdapter({"bind:focusChange"})
    public static void bindFocusChangeCommand(View view, BooleanReplyCommand command) {
        if (ObjectUtils.isNull(command)) {
            return;
        }
        view.setOnFocusChangeListener((v, hasFocus) -> command.reply(hasFocus).execute());
    }

    @SuppressLint("ClickableViewAccessibility")
    @BindingAdapter({"bind:touch"})
    public static void bindTouchCommand(View view, ReplyCommand<MotionEvent> command){
        if (ObjectUtils.isNull(command)){
            return;
        }
        view.setOnTouchListener((v, event)-> command.reply(event).execute());
    }

    @BindingAdapter("bind:bindHover")
    public static void bindHover(View view, ReplyCommand<MotionEvent> command){
        if (ObjectUtils.isNull(command)){
            return;
        }
        view.setOnHoverListener((v, event)->command.reply(event).execute());
    }


}
