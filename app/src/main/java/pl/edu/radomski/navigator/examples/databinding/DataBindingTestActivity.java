package pl.edu.radomski.navigator.examples.databinding;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import pl.edu.radomski.navigator.Navigable;
import pl.edu.radomski.navigator.R;
import pl.edu.radomski.navigator.databinding.BindingTestActivityBinding;

/**
 * Created by adam on 24.01.16.
 */
@Navigable
public class DataBindingTestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.binding_test_activity);

        BindingTestActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.binding_test_activity);
        BindingData bindingData = new BindingData();
        bindingData.setName("Teest");
        binding.setBindingData(bindingData);
        binding.invalidateAll();

    }
}
