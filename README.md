# TextFloatingActionButton
Android floating action button (fab) with text

![sample](https://github.com/EdSergeev/TextFloatingActionButton/blob/master/testapp/text-fab-sample.gif?raw=true)

## Download

Download via Gradle:
```groovy
compile 'com.github.edsergeev:text-fab:1.0.0'
```

## Usage

You can use as regular _FloatingActionButton_, but with additional attributes

```xml
    <com.github.edsergeev.TextFloatingActionButton
        android:id="@+id/fab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_margin="@dimen/fab_margin"
        android:scaleType="center"
        android:src="@drawable/ic_draft_n_black"
        app:backgroundTint="@android:color/white"
        app:useCompatPadding="true"
        
        android:text="10"
        app:text_x_offset="7dp"
        app:text_y_offset="10dp"
        android:textColor="#000"
        android:textSize="18sp"
        android:textStyle="bold"
        android:fontFamily="sans-serif-medium" />
```

## License

    Licensed under the Apache License, Version 2.0 (the "License");
    
        http://www.apache.org/licenses/LICENSE-2.0
