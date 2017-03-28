# JellyToolbar

[![License](http://img.shields.io/badge/license-MIT-green.svg?style=flat)]()
[![](https://jitpack.io/v/yalantis/jellytoolbar.svg)](https://jitpack.io/#yalantis/jellytoolbar)
[![Yalantis](https://raw.githubusercontent.com/Yalantis/PullToRefresh/develop/PullToRefreshDemo/Resources/badge_dark.png)](https://yalantis.com/?utm_source=github)

<a href="https://play.google.com/store/apps/details?id=com.yalantis.jellyanimation.demo"><img alt="Android app on Google Play" src="https://developer.android.com/images/brand/en_app_rgb_wo_60.png" /></a>

Check this [project on dribbble](https://dribbble.com/shots/2092541-Search-Bar-Animation)

<img src="gif.gif"/>

## Requirements
- Android SDK 16+

## Usage

Add to your root build.gradle:
```Groovy
allprojects {
	repositories {
	  ...
	  maven { url "https://jitpack.io" }
	}
}
```

Add the dependency:
```Groovy
dependencies {
  compile 'com.github.yalantis:jellytoolbar:v1.0'
}
```

## How to use this library in your project?

First of all, add `JellyToolbar` to the xml layout of your activity, so it looks like that:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <com.yalantis.jellytoolbar.widget.JellyToolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/colorPrimary"
        android:paddingLeft="@dimen/activity_horizontal_margin"
        android:paddingStart="@dimen/activity_horizontal_margin"
        app:cancelIcon="@drawable/ic_close"
        app:endColor="@color/colorEnd"
        app:icon="@drawable/ic_search"
        app:startColor="@color/colorStart"
        app:title="@string/str_news_feed"
        app:titleTextColor="@android:color/white" />

</LinearLayout>
```


After that pass an instance of the `JellyListener` and content view 
(the view which would be inserted to the toolbar) to the `JellyToolbar`. 
`JellyToolbar` has `getToolbar()` method to let you use all the methods of the standard `Toolbar`. 


```Java
public class MainActivity extends AppCompatActivity {

    private JellyToolbar toolbar;
    private AppCompatEditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (JellyToolbar) findViewById(R.id.toolbar);
        toolbar.getToolbar().setNavigationIcon(R.drawable.ic_menu);
        toolbar.setJellyListener(jellyListener);

        editText = (AppCompatEditText) LayoutInflater.from(this).inflate(R.layout.edit_text, null);
        editText.setBackgroundResource(R.color.colorTransparent);
        toolbar.setContentView(editText);
    }

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

}
```

To control the animation flow use `collapse()` and `expand()` methods.

Override `onToolbarExpandingStarted()`, `onToolbarCollapsingStarted()`, `onToolbarExpanded()` and `onToolbarCollapsed()`
methods of the `JellyListener` to get all the animation events.

## Let us know!

We’d be really happy if you sent us links to your projects where you use our component. Just send an email to github@yalantis.com And do let us know if you have any questions or suggestion regarding the animation. 

P.S. We’re going to publish more awesomeness wrapped in code and a tutorial on how to make UI for iOS (Android) better than better. Stay tuned!

## License

	The MIT License (MIT)

	Copyright © 2017 Yalantis, https://yalantis.com

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in
	all copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
	THE SOFTWARE.
