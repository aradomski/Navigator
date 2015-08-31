/*
 * Navigator
 * Copyright (C)  2015 Adam Radomski
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 * USA
 */

package pl.edu.radomski.navigator.examples;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import pl.edu.radomski.navigator.Navigable;
import pl.edu.radomski.navigator.Param;
import pl.edu.radomski.navigator.Result;
import pl.edu.radomski.navigator.params.Book;

/**
 * Created by adam on 8/24/15.
 */
@Navigable
public class GroupedParamsForResultActivity extends BaseActivity {

    @Param(forResult = true, group = "group1")
    Integer integerParam;
    @Param(forResult = true, group = "group1")
    String stringParam;
    @Param(forResult = true, group = "group2")
    Book parcelableParam;

    @Result
    String stringResult = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GroupedParamsForResultActivityParamLoader.load(this);
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
                Intent intent = GroupedParamsForResultActivityResultFiller.fillResult(GroupedParamsForResultActivity.this);
                setResult(RESULT_OK, intent);
                finish();
            }
        };
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GroupedParamsForResultActivity{");
        sb.append("integerParam=").append(integerParam);
        sb.append(", stringParam='").append(stringParam).append('\'');
        sb.append(", parcelableParam=").append(parcelableParam);
        sb.append(", stringResult='").append(stringResult).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
