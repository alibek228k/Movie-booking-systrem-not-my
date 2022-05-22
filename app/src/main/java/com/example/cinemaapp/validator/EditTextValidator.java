package com.example.cinemaapp.validator;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputEditText;

public abstract class EditTextValidator implements TextWatcher {
    private TextInputEditText editText;

    public EditTextValidator(TextInputEditText editText){
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String str = editText.getText().toString();
        validate(editText, str);
    }

    public abstract void validate(TextInputEditText editText, String text);
}
