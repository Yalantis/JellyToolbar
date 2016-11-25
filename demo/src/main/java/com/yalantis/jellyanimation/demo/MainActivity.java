package com.yalantis.jellyanimation.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;

import com.yalantis.jellytoolbar.widget.JellyToolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JellyToolbar toolbar = (JellyToolbar) findViewById(R.id.toolbar);
        toolbar.getToolbar().setLogo(R.drawable.ic_menu);
        toolbar.getToolbar().setPadding(0, getStatusBarHeight(), 0, 0);

        AppCompatEditText editText = new AppCompatEditText(this);
        editText.setBackgroundResource(R.color.colorTransparent);
        toolbar.setContentView(LayoutInflater.from(this).inflate(R.layout.edit_text, null));

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
}
