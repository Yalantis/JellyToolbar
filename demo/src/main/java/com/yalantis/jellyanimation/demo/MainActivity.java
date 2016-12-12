package com.yalantis.jellyanimation.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.yalantis.jellytoolbar.listener.JellyListener;
import com.yalantis.jellytoolbar.widget.JellyToolbar;

public class MainActivity extends AppCompatActivity implements JellyListener {

    private static final String TEXT_KEY = "text";

    private JellyToolbar mToolbar;
    private AppCompatEditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (JellyToolbar) findViewById(R.id.toolbar);
        mToolbar.getToolbar().setLogo(R.drawable.ic_menu);
        mToolbar.getToolbar().setPadding(0, getStatusBarHeight(), 0, 0);
        mToolbar.setJellyListener(this);

        mEditText = (AppCompatEditText) LayoutInflater.from(this).inflate(R.layout.edit_text, null);
        mEditText.setBackgroundResource(R.color.colorTransparent);
        mToolbar.setContentView(mEditText);

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
    public void onToolbarExpandingStarted() {

    }

    @Override
    public void onToolbarCollapsingStarted() {

    }

    @Override
    public void onToolbarExpanded() {

    }

    @Override
    public void onToolbarCollapsed() {

    }

    @Override
    public void onCancelIconClicked() {
        if (TextUtils.isEmpty(mEditText.getText())) {
            mToolbar.collapse();
        } else {
            mEditText.getText().clear();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(TEXT_KEY, mEditText.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mEditText.setText(savedInstanceState.getString(TEXT_KEY));
        mEditText.setSelection(mEditText.getText().length());
    }

}
