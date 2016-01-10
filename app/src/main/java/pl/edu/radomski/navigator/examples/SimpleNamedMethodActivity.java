package pl.edu.radomski.navigator.examples;

import android.os.Bundle;

import pl.edu.radomski.navigator.Navigable;

/**
 * Created by adam on 10.01.16.
 */
@Navigable(name = "customName")
public class SimpleNamedMethodActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getCanonicalName());
        classInfo.setText(stringBuilder);
    }
}
