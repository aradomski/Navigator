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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import pl.edu.radomski.navigator.R;
import pl.edu.radomski.navigator.params.Book;
import pl.edu.radomski.navigator.params.Person;

/**
 * Created by adam on 8/24/15.
 */
public class BaseActivity extends Activity {
    public static final int PARAMS_FOR_RESULT_ACTIVITY = 5000;
    public static final int GROUPED_FOR_RESULT_ACTIVITY = 5001;
    public static final int FOR_RESULT_ACTIVITY = 5004;
    public static final int GROUPED_RESULTS_FOR_RESULT_ACTIVITY1 = 5005;
    public static final int GROUPED_RESULTS_FOR_RESULT_ACTIVITY2 = 5006;
    public static final int GROUPED_RESULTS_FOR_RESULT_ACTIVITY3 = 5007;
    public static final int WITH_RESULT_OPTION_ACTIVITY = 5008;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Book book = new Book();
            book.setBookName("Navigator");
            book.setPublishTime(2015);
            book.setAuthor("Adam");
            switch (v.getId()) {
                case R.id.simple_activity:
                    Navigator.simpleActivity(BaseActivity.this);
                    break;
                case R.id.params_activity:
                    Navigator.paramsActivity(BaseActivity.this, 2123, "abcdefgh", book);
                    break;
                case R.id.grouped_params_activity1:
                    Navigator.groupedParamsActivitySecondGroup(BaseActivity.this, 32.12, 2145);
                    break;
                case R.id.grouped_params_activity2:
                    Navigator.groupedParamsActivityFirstGroup(BaseActivity.this, "abcdefgh", true);
                    break;
                case R.id.grouped_params_activity3:
                    Navigator.groupedParamsActivityThirdGroup(BaseActivity.this, 32.12, 2145);
                    break;
                case R.id.params_for_result_activity:
                    Navigator.paramsForResultActivityForResult(BaseActivity.this, 123, "abcdefgh", book, PARAMS_FOR_RESULT_ACTIVITY);
                    break;
                case R.id.grouped_params_for_result_activity1:
                    Navigator.groupedParamsForResultActivityGroup1ForResult(BaseActivity.this, 123, "abcdefgh", GROUPED_FOR_RESULT_ACTIVITY);
                    break;
                case R.id.grouped_params_for_result_activity2:
                    Navigator.groupedParamsForResultActivityGroup2ForResult(BaseActivity.this, book, GROUPED_FOR_RESULT_ACTIVITY);
                    break;
                case R.id.for_result_activity:
                    Navigator.forResultActivityForResult(BaseActivity.this, FOR_RESULT_ACTIVITY);
                    break;
                case R.id.grouped_results_for_result_activity1:
                    Navigator.groupedResultsForResultActivityForResult(BaseActivity.this, 1, GROUPED_RESULTS_FOR_RESULT_ACTIVITY1);
                    break;
                case R.id.grouped_results_for_result_activity2:
                    Navigator.groupedResultsForResultActivityForResult(BaseActivity.this, 2, GROUPED_RESULTS_FOR_RESULT_ACTIVITY2);
                    break;
                case R.id.grouped_results_for_result_activity3:
                    Navigator.groupedResultsForResultActivityForResult(BaseActivity.this, 3, GROUPED_RESULTS_FOR_RESULT_ACTIVITY3);
                    break;
                case R.id.all_params_activity:
                    runAllParamsActivity();
                    break;
                case R.id.grouped_activity:
                    Navigator.subgroup.groupedActivity(BaseActivity.this);
                    break;
                case R.id.with_result_option_activity1:
                    Navigator.activityWithResultOptionFirstGroupForResult(BaseActivity.this, "abcdefgh", true, WITH_RESULT_OPTION_ACTIVITY);
                    break;
                case R.id.with_result_option_activity2:
                    Navigator.activityWithResultOptionSecondGroup(BaseActivity.this, 123.53, 25);
                    break;
            }
        }
    };

    protected TextView classInfo;
    private TextView textViewResult;
    private Button closeButton;
    private Button simpleActivity;
    private Button paramsActivity;
    private Button groupedParamsActivity1;
    private Button groupedParamsActivity2;
    private Button groupedParamsActivity3;
    private Button paramsForResultActivity;
    private Button groupedParamsForResultActivity1;
    private Button groupedParamsForResultActivity2;
    private Button forResultActivity;
    private Button groupedResultsActivity1;
    private Button groupedResultsActivity2;
    private Button groupedResultsActivity3;
    private Button allParamsActivity;
    private Button groupedActivity;
    private Button withResultOption1;
    private Button withResultOption2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        classInfo = (TextView) findViewById(R.id.text);
        textViewResult = (TextView) findViewById(R.id.result);


        closeButton = (Button) findViewById(R.id.close);
        closeButton.setOnClickListener(getOnCloseClickListener());

        simpleActivity = (Button) findViewById(R.id.simple_activity);
        simpleActivity.setOnClickListener(onClickListener);

        paramsActivity = (Button) findViewById(R.id.params_activity);
        paramsActivity.setOnClickListener(onClickListener);

        groupedParamsActivity1 = (Button) findViewById(R.id.grouped_params_activity1);
        groupedParamsActivity1.setOnClickListener(onClickListener);

        groupedParamsActivity2 = (Button) findViewById(R.id.grouped_params_activity2);
        groupedParamsActivity2.setOnClickListener(onClickListener);

        groupedParamsActivity3 = (Button) findViewById(R.id.grouped_params_activity2);
        groupedParamsActivity3.setOnClickListener(onClickListener);

        paramsForResultActivity = (Button) findViewById(R.id.params_for_result_activity);
        paramsForResultActivity.setOnClickListener(onClickListener);

        groupedParamsForResultActivity1 = (Button) findViewById(R.id.grouped_params_for_result_activity1);
        groupedParamsForResultActivity1.setOnClickListener(onClickListener);

        groupedParamsForResultActivity2 = (Button) findViewById(R.id.grouped_params_for_result_activity2);
        groupedParamsForResultActivity2.setOnClickListener(onClickListener);

        forResultActivity = (Button) findViewById(R.id.for_result_activity);
        forResultActivity.setOnClickListener(onClickListener);

        groupedResultsActivity1 = (Button) findViewById(R.id.grouped_results_for_result_activity1);
        groupedResultsActivity1.setOnClickListener(onClickListener);

        groupedResultsActivity2 = (Button) findViewById(R.id.grouped_results_for_result_activity2);
        groupedResultsActivity2.setOnClickListener(onClickListener);

        groupedResultsActivity3 = (Button) findViewById(R.id.grouped_results_for_result_activity3);
        groupedResultsActivity3.setOnClickListener(onClickListener);

        allParamsActivity = (Button) findViewById(R.id.all_params_activity);
        allParamsActivity.setOnClickListener(onClickListener);

        groupedActivity = (Button) findViewById(R.id.grouped_activity);
        groupedActivity.setOnClickListener(onClickListener);


        withResultOption1 = (Button) findViewById(R.id.with_result_option_activity1);
        withResultOption1.setOnClickListener(onClickListener);

        withResultOption2 = (Button) findViewById(R.id.with_result_option_activity2);
        withResultOption2.setOnClickListener(onClickListener);

    }

    protected View.OnClickListener getOnCloseClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PARAMS_FOR_RESULT_ACTIVITY:
                ParamsForResultActivityResultLoader.ParamsForResultActivityResult result1 = ParamsForResultActivityResultLoader.load(data);
                textViewResult.setText(result1.stringResult);
                break;
            case GROUPED_FOR_RESULT_ACTIVITY:
                GroupedParamsForResultActivityResultLoader.GroupedParamsForResultActivityResult result = GroupedParamsForResultActivityResultLoader.load(data);
                textViewResult.setText(result.stringResult);
                break;
            case FOR_RESULT_ACTIVITY:
                ForResultActivityResultLoader.ForResultActivityResult result3 = ForResultActivityResultLoader.load(data);
                textViewResult.setText(result3.stringResult + "\n" + result3.book);
                break;
            case GROUPED_RESULTS_FOR_RESULT_ACTIVITY1:
                GroupedResultsForResultActivityResultLoader.GroupedResultsForResultActivityResult result4 = GroupedResultsForResultActivityResultLoader.load(data);
                textViewResult.setText(result4.stringResult);
                break;
            case GROUPED_RESULTS_FOR_RESULT_ACTIVITY2:
                GroupedResultsForResultActivityResultLoader.GroupedResultsForResultActivityFirstGroupResult result5 = GroupedResultsForResultActivityResultLoader.loadFirstGroup(data);
                textViewResult.setText(result5.booleanResult + "");
                break;
            case GROUPED_RESULTS_FOR_RESULT_ACTIVITY3:
                GroupedResultsForResultActivityResultLoader.GroupedResultsForResultActivitySecondGroupResult result7 = GroupedResultsForResultActivityResultLoader.loadSecondGroup(data);
                textViewResult.setText(result7.integerResult + "\n" + result7.doubleResult);
                break;
            case WITH_RESULT_OPTION_ACTIVITY:
                ActivityWithResultOptionResultLoader.ActivityWithResultOptionFirstResultGroupResult result8 = ActivityWithResultOptionResultLoader.loadFirstResultGroup(data);
                textViewResult.setText(result8.stringResult + "\n" + result8.bookResult);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void runAllParamsActivity() {
        ArrayList<CharSequence> charSequences = new ArrayList<>();
        charSequences.add("aaaaaaa");
        charSequences.add("bbbbbb");
        charSequences.add("cccccc");

        ArrayList<String> strings = new ArrayList<>();
        strings.add("aaaaaaa");
        strings.add("bbbbbb");
        strings.add("cccccc");

        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(132);
        integers.add(123);
        integers.add(312);

        Book book1 = new Book();
        book1.setAuthor("Adam");
        book1.setBookName("navigator");
        book1.setPublishTime(21351);
        Book book2 = new Book();
        book2.setAuthor("Adam2");
        book2.setBookName("navigator2");
        book2.setPublishTime(53235);
        Book book3 = new Book();
        book3.setAuthor("Adam3");
        book3.setBookName("navigator3");
        book3.setPublishTime(65567);

        ArrayList<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);

        Person person = new Person();
        person.setAge(25);
        person.setName("Adam");

        Navigator.allParamsActivity(BaseActivity.this
                , new boolean[]{true, false, true}
                , true
                , true
                , new byte[]{(byte) 0, (byte) 1, (byte) 2, (byte) 3}
                , (byte) 200
                , (byte) 300
                , new char[]{'a', 'b', 'c'}
                , 'b'
                , 'g'
                , new CharSequence[]{"aaaaaa", "bbbbbb"}
                , charSequences
                , "aaaaaaaa"
                , new double[]{1.123, 2.123, 3.123, 4.123, 5.123, 6.123}
                , 1.123
                , 1.123
                , new float[]{1.123f, 2.123f}
                , 1.123f
                , 1.123f
                , new int[]{1, 2, 3, 4, 5, 6}
                , 123
                , 321
                , integers
                , new long[]{123L, 312L, 213L}
                , 213L
                , 312L
                , new Book[]{book1, book2, book3}
                , books
                , book1
                , person
                , new short[]{(short) 1, (short) 2, (short) 3, (short) 4}
                , (short) 231
                , (short) 432
                , new String[]{"aaaaa", "bbbbb", "ccccc"}
                , strings
                , "bbbbbb");
    }
}
