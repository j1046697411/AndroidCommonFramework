package org.jzl.android.mvvm.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.databinding.BindingAdapter;

import org.jzl.android.mvvm.adapter.event.TextChangeDataEvent;
import org.jzl.android.mvvm.command.ReplyCommand;
import org.jzl.lang.util.ObjectUtils;


public final class EditTextBindingAdapter {

    @BindingAdapter(value = {"bind:beforeTextChanged", "bind:textChanged", "bind:afterTextChanged"}, requireAll = false)
    public static void editTextCommand(EditText editText,
                                       final ReplyCommand<TextChangeDataEvent> beforeTextChangedCommand,
                                       final ReplyCommand<TextChangeDataEvent> onTextChangedCommand,
                                       final ReplyCommand<Editable> afterTextChangedCommand) {
        if (ObjectUtils.nonNull(beforeTextChangedCommand) && ObjectUtils.nonNull(onTextChangedCommand) && ObjectUtils.nonNull(afterTextChangedCommand)) {
            return;
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                if (ObjectUtils.isNull(beforeTextChangedCommand)) {
                    return;
                }
                beforeTextChangedCommand.reply(TextChangeDataEvent.of(text, start, count, after)).execute();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ObjectUtils.isNull(onTextChangedCommand)) {
                    return;
                }
                onTextChangedCommand.reply(TextChangeDataEvent.of(s, start, before, count)).execute();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (ObjectUtils.isNull(afterTextChangedCommand)) {
                    return;
                }
                afterTextChangedCommand.reply(editable).execute();
            }
        });

    }
}
