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

package pl.edu.radomski.navigator.navigable;

import java.util.ArrayList;

import javax.lang.model.element.TypeElement;

import pl.edu.radomski.navigator.AnnotationValidator;
import pl.edu.radomski.navigator.Navigable;

/**
 * Created by adam on 8/18/15.
 */
public class NavigableAnnotationValidator extends AnnotationValidator {
    private static final String ANNOTATION = "@" + Navigable.class.getCanonicalName();

    public static ArrayList<String> isValidClass(TypeElement annotatedClass) {
        ArrayList<String> errors = new ArrayList<>();
        if (!isPublic(annotatedClass)) {
            errors.add(String.format("Classes annotated with %s must be public.", ANNOTATION));
        }

        if (isAbstract(annotatedClass)) {
            errors.add(String.format("Classes annotated with %s must not be abstract.", ANNOTATION));
        }
        return errors;
    }
}
