package pl.edu.radomski.navigator.examples.databinding;

import android.databinding.Bindable;
import android.databinding.Observable;

/**
 * Created by adam on 24.01.16.
 */
public class BindingData implements Observable {
    private String name;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

    }
}
