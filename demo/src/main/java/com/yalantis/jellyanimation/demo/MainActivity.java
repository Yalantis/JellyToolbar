package com.yalantis.jellyanimation.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.yalantis.jellytoolbar.listener.JellyListener;
import com.yalantis.jellytoolbar.widget.JellyToolbar;

public class MainActivity extends AppCompatActivity {

    private static final String TEXT_KEY = "text";

    private JellyToolbar toolbar;
    private AppCompatEditText editText;
    private JellyListener jellyListener = new JellyListener() {
        @Override
        public void onCancelIconClicked() {
            if (TextUtils.isEmpty(editText.getText())) {
                toolbar.collapse();
            } else {
                editText.getText().clear();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (JellyToolbar) findViewById(R.id.toolbar);
        toolbar.getToolbar().setNavigationIcon(R.drawable.ic_menu);
        toolbar.setJellyListener(jellyListener);
        toolbar.getToolbar().setPadding(0, getStatusBarHeight(), 0, 0);

        editText = (AppCompatEditText) LayoutInflater.from(this).inflate(R.layout.edit_text, null);
        editText.setBackgroundResource(R.color.colorTransparent);
        toolbar.setContentView(editText);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(TEXT_KEY, editText.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        editText.setText(savedInstanceState.getString(TEXT_KEY));
        editText.setSelection(editText.getText().length());
    }

}
