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

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import pl.edu.radomski.navigator.exceptions.NoPackageNameException;
import pl.edu.radomski.navigator.navigable.NavigableAnnotatedClass;
import pl.edu.radomski.navigator.utils.tuple.Tuple;
import pl.edu.radomski.navigator.utils.tuple.Tuple2;

public final class Utils {

    private Utils() {
        // no instances
    }

    public static String getPackageName(Elements elementUtils, List<NavigableAnnotatedClass> type) throws NoPackageNameException {
        List<String> packageNames = new ArrayList<>();
        PackageElement packageElement;
        for (NavigableAnnotatedClass navigableAnnotatedClass : type) {
            packageElement = elementUtils.getPackageOf(navigableAnnotatedClass.getTypeElement());
            if (!packageElement.isUnnamed()) {
                packageNames.add(packageElement.getQualifiedName().toString());
            }
        }
        return StringUtils.longestCommonSubstring(packageNames);

    }


}
