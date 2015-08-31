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

package pl.edu.radomski.navigator;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

final class Utils {

    private Utils() {
        // no instances
    }

    static String getPackageName(Elements elementUtils, TypeElement type)
            throws NoPackageNameException {
        PackageElement pkg = elementUtils.getPackageOf(type);
        if (pkg.isUnnamed()) {
            throw new NoPackageNameException(type);
        }
        return pkg.getQualifiedName().toString();
    }
}
