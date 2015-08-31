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

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

import pl.edu.radomski.navigator.Navigable;
import pl.edu.radomski.navigator.Param;
import pl.edu.radomski.navigator.params.Book;
import pl.edu.radomski.navigator.params.Person;

/**
 * Created by adam on 8/23/15.
 */
@Navigable
public class AllParamsActivity extends BaseActivity {

    @Param
    boolean[] booleanPrimitiveArray;
    @Param
    Boolean booleanObject;
    @Param
    boolean booleanPrimitive;


    @Param
    byte[] bytePrimitiveArray;
    @Param
    Byte byteObjectParam;
    @Param
    byte bytePrimitveParam;

    @Param
    char[] charPrimitiveArray;
    @Param
    Character charObjectParam;
    @Param
    char charPrimitveParam;


    @Param
    CharSequence[] charSequenceArray;
    @Param
    ArrayList<CharSequence> charSequenceArrayList;
    @Param
    CharSequence charSequence;

    @Param
    double[] doublePrimitiveArray;
    @Param
    Double doubleObjectParam;
    @Param
    double doublePrimitiveParam;

    @Param
    float[] floatPrimitiveArray;
    @Param
    Float floatObjectParam;
    @Param
    float floatPrimitiveParam;

    @Param
    int[] intPrimitiveArray;
    @Param
    Integer intObjectParam;
    @Param
    int intPrimitiveParam;
    @Param
    ArrayList<Integer> integerArrayList;


    @Param
    long[] longPrimitiveArray;
    @Param
    Long longObjectParam;
    @Param
    long longPrimitiveParam;


    @Param
    Book[] booksArray;
    @Param
    ArrayList<Book> bookArrayList;
    @Param
    Book book;

    @Param
    Person person;

    @Param
    short[] shortPrimitiveArray;
    @Param
    Short shortObjectParam;
    @Param
    short shortPrimitiveParam;

    @Param
    String[] stringArray;
    @Param
    ArrayList<String> stringArrayList;
    @Param
    String stringParam;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AllParamsActivityParamLoader.load(this);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getCanonicalName());
        stringBuilder.append("\n");
        stringBuilder.append(toString());
        classInfo.setText(stringBuilder);
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AllParamsActivity{");
        sb.append("floatObjectParam=").append(floatObjectParam).append("\n");
        sb.append(", booleanPrimitiveArray=").append(Arrays.toString(booleanPrimitiveArray)).append("\n");
        sb.append(", booleanObject=").append(booleanObject).append("\n");
        sb.append(", booleanPrimitive=").append(booleanPrimitive).append("\n");
        sb.append(", bytePrimitiveArray=").append(Arrays.toString(bytePrimitiveArray)).append("\n");
        sb.append(", byteObjectParam=").append(byteObjectParam).append("\n");
        sb.append(", bytePrimitveParam=").append(bytePrimitveParam).append("\n");
        sb.append(", charPrimitiveArray=").append(Arrays.toString(charPrimitiveArray)).append("\n");
        sb.append(", charObjectParam=").append(charObjectParam).append("\n");
        sb.append(", charPrimitveParam=").append(charPrimitveParam).append("\n");
        sb.append(", charSequenceArray=").append(Arrays.toString(charSequenceArray)).append("\n");
        sb.append(", charSequenceArrayList=").append(charSequenceArrayList).append("\n");
        sb.append(", charSequence=").append(charSequence).append("\n");
        sb.append(", doublePrimitiveArray=").append(Arrays.toString(doublePrimitiveArray)).append("\n");
        sb.append(", doubleObjectParam=").append(doubleObjectParam).append("\n");
        sb.append(", doublePrimitiveParam=").append(doublePrimitiveParam).append("\n");
        sb.append(", floatPrimitiveArray=").append(Arrays.toString(floatPrimitiveArray)).append("\n");
        sb.append(", floatPrimitiveParam=").append(floatPrimitiveParam).append("\n");
        sb.append(", intPrimitiveArray=").append(Arrays.toString(intPrimitiveArray)).append("\n");
        sb.append(", intObjectParam=").append(intObjectParam).append("\n");
        sb.append(", intPrimitiveParam=").append(intPrimitiveParam).append("\n");
        sb.append(", integerArrayList=").append(integerArrayList).append("\n");
        sb.append(", longPrimitiveArray=").append(Arrays.toString(longPrimitiveArray)).append("\n");
        sb.append(", longObjectParam=").append(longObjectParam).append("\n");
        sb.append(", longPrimitiveParam=").append(longPrimitiveParam).append("\n");
        sb.append(", booksArray=").append(Arrays.toString(booksArray)).append("\n");
        sb.append(", bookArrayList=").append(bookArrayList).append("\n");
        sb.append(", book=").append(book).append("\n");
        sb.append(", person=").append(person).append("\n");
        sb.append(", shortPrimitiveArray=").append(Arrays.toString(shortPrimitiveArray)).append("\n");
        sb.append(", shortObjectParam=").append(shortObjectParam).append("\n");
        sb.append(", shortPrimitiveParam=").append(shortPrimitiveParam).append("\n");
        sb.append(", stringArray=").append(Arrays.toString(stringArray)).append("\n");
        sb.append(", stringArrayList=").append(stringArrayList).append("\n");
        sb.append(", stringParam='").append(stringParam).append('\'').append("\n");
        sb.append('}');
        return sb.toString();
    }
}