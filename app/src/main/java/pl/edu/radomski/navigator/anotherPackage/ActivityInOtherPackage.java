package pl.edu.radomski.navigator.anotherPackage;

import android.os.Bundle;

import pl.edu.radomski.navigator.Navigable;
import pl.edu.radomski.navigator.examples.BaseActivity;

/**
 * Created by adam on 30.01.16.
 */
@Navigable
public class ActivityInOtherPackage extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getCanonicalName());
        classInfo.setText(stringBuilder);
    }
}
