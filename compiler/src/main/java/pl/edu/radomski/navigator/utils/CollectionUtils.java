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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by adam on 8/19/15.
 */
public class CollectionUtils {


    public static <K, E> void addToHashMapWithList(Map<K, List<E>> map, K key, E element) {
        if (map.get(key) == null) {
            map.put(key, new ArrayList<E>());
        }
        map.get(key).add(element);
    }

    public static <K, E> List<E> getElementFromMapWithList(Map<K, List<E>> map) {
        ArrayList<E> list = new ArrayList<>();
        for (List<E> element : map.values()) {
            list.addAll(element);
        }
        return list;
    }

}
