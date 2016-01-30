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

package pl.edu.radomski.navigator.utils;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import pl.edu.radomski.navigator.utils.tuple.Tuple;
import pl.edu.radomski.navigator.utils.tuple.Tuple2;

/**
 * Created by adam on 8/25/15.
 */
public final class StringUtils {

    public static boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    public static String longestCommonSubstring(List<String> values) {
        TreeMap<Integer, Tuple2<String, String>> map = new TreeMap<>();


        for (int i = 0; i < values.size() - 1; i++) {
            for (int j = i + 1; j < values.size(); j++) {
                map.put(longestCommonSubstring(values.get(i), values.get(j)), Tuple.make(values.get(i), values.get(j)));
            }
        }

        Map.Entry<Integer, Tuple2<String, String>> integerTuple2Entry = map.firstEntry();
        return integerTuple2Entry.getValue().first.substring(0, integerTuple2Entry.getKey() - 1);
    }

    // Source: http://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Longest_common_substring
    public static int longestCommonSubstring(String first, String second) {
        if (isEmpty(first) || isEmpty(second)) {
            return 0;
        }

        int maxLen = 0;
        int fl = first.length();
        int sl = second.length();
        int[][] table = new int[fl + 1][sl + 1];

        for (int s = 0; s <= sl; s++)
            table[0][s] = 0;
        for (int f = 0; f <= fl; f++)
            table[f][0] = 0;


        for (int i = 1; i <= fl; i++) {
            for (int j = 1; j <= sl; j++) {
                if (first.charAt(i - 1) == second.charAt(j - 1)) {
                    if (i == 1 || j == 1) {
                        table[i][j] = 1;
                    } else {
                        table[i][j] = table[i - 1][j - 1] + 1;
                    }
                    if (table[i][j] > maxLen) {
                        maxLen = table[i][j];
                    }
                }
            }
        }
        return maxLen;
    }

}
