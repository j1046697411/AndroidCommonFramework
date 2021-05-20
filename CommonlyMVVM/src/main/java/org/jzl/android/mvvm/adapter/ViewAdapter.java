//package org.jzl.android.mvvm.adapter;
//
//import android.os.Build;
//import android.view.DragEvent;
//import android.view.KeyEvent;
//import android.view.MotionEvent;
//import android.view.View;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.databinding.BindingAdapter;
//
//import org.jzl.android.mvvm.command.IBooleanReplyCommand;
//import org.jzl.android.mvvm.command.ICommand;
//import org.jzl.android.mvvm.command.IReplyCommand;
//import org.jzl.lang.util.ObjectUtils;
//
//public final class ViewAdapter {
//
//    @BindingAdapter("onClockCommand")
//    public static void onClockCommand(View view, ICommand command) {
//        if (ObjectUtils.nonNull(view) && ObjectUtils.nonNull(command)) {
//            view.setOnClickListener(v -> command.execute());
//        }
//    }
//
//    @BindingAdapter("onLongClockCommand")
//    public static void onLongClockCommand(View view, ICommand command) {
//        if (ObjectUtils.nonNull(view) && ObjectUtils.nonNull(command)) {
//            view.setOnLongClickListener(v -> command.execute());
//        }
//    }
//
//    @BindingAdapter("onDragCommand")
//    public static void onDragCommand(View view, IReplyCommand<DragEvent> command) {
//        if (ObjectUtils.nonNull(view) && ObjectUtils.nonNull(command)) {
//            view.setOnDragListener((v, event) -> command.reply(event).execute());
//        }
//    }
//
//    @BindingAdapter("onKeyCommand")
//    public static void onKeyCommand(View view, IReplyCommand<KeyEvent> command) {
//        if (ObjectUtils.nonNull(view) && ObjectUtils.nonNull(command)) {
//            view.setOnKeyListener((v, keyCode, event) -> command.reply(event).execute());
//        }
//    }
//
//    @BindingAdapter("onTouchCommand")
//    public static void onTouchCommand(View view, IReplyCommand<MotionEvent> command) {
//        if (ObjectUtils.nonNull(view) && ObjectUtils.nonNull(command)) {
//            view.setOnTouchListener((v, event) -> {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    v.performClick();
//                }
//                return command.reply(event).execute();
//            });
//        }
//    }
//
//    @BindingAdapter("onFocusChangeCommand")
//    public static void onFocusChangeCommand(@NonNull View view, @NonNull IBooleanReplyCommand command) {
//        view.setOnFocusChangeListener((v, hasFocus) -> command.reply(hasFocus).execute());
//    }
//
////    @BindingAdapter("onApplyWindowCommand")
////    public static void onApplyWindow(@NonNull View view, @NonNull IReplyCommand<WindowInsets> command) {
////        view.setOnApplyWindowInsetsListener((v, insets) -> {
////            command.reply(insets).execute();
////            return insets;
////        });
////    }
//
//    @BindingAdapter("onCapturedPointerCommand")
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static void onCapturedPointerCommand(@NonNull View view, @NonNull IReplyCommand<MotionEvent> command) {
//        view.setOnCapturedPointerListener((v, event) -> command.reply(event).execute());
//    }
//
//}
