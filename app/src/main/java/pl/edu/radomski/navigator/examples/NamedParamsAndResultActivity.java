package pl.edu.radomski.navigator.examples;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import pl.edu.radomski.navigator.Navigable;
import pl.edu.radomski.navigator.Param;
import pl.edu.radomski.navigator.Result;
import pl.edu.radomski.navigator.params.Book;

/**
 * Created by adam on 10.01.16.
 */
@Navigable(name = "customNameWithParams")
public class NamedParamsAndResultActivity extends BaseActivity {

    @Param(forResult = true)
    Integer integerParam;
    @Param(forResult = true)
    String stringParam;
    @Param(group = "OtherGroup")
    Book parcelableParam;

    @Result
    String stringResult = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomNameWithParamsParamLoader.load(this);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getCanonicalName());
        stringBuilder.append("\n");
        stringBuilder.append(this.toString());
        classInfo.setText(stringBuilder);
    }

    @Override
    protected View.OnClickListener getOnCloseClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CustomNameWithParamsResultFiller.fillResult(NamedParamsAndResultActivity.this);
                setResult(RESULT_OK, intent);
                finish();
            }
        };
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ParamsForResultActivity{");
        sb.append("integerParam=").append(integerParam);
        sb.append(", stringParam='").append(stringParam).append('\'');
        sb.append(", parcelableParam=").append(parcelableParam);
        sb.append(", stringResult='").append(stringResult).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
