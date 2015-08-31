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


import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * Created by adam on 8/18/15.
 */
public class AnnotationValidator {
    protected static boolean isPublic(Element annotatedClass) {
        return annotatedClass.getModifiers().contains(Modifier.PUBLIC);
    }

    protected static boolean isPackage(Element annotatedClass) {
        return !isPublic(annotatedClass) && !isProtected(annotatedClass) && !isPrivate(annotatedClass);
    }

    protected static boolean isProtected(Element annotatedClass) {
        return annotatedClass.getModifiers().contains(Modifier.PROTECTED);
    }

    protected static boolean isPrivate(Element annotatedClass) {
        return annotatedClass.getModifiers().contains(Modifier.PRIVATE);
    }

    protected static boolean isAbstract(Element annotatedClass) {
        return annotatedClass.getModifiers().contains(Modifier.ABSTRACT);
    }

    protected static boolean hasOneOfModifiers(Element element, Modifier... modifiers) {
        for (Modifier modifier : modifiers) {
            if (element.getModifiers().contains(modifier)) {
                return true;
            }
        }
        return false;
    }


    public static <T extends Class> boolean isAnnotated(Element variableElement, T t) {
        return variableElement.getAnnotation(t) != null;
    }
}
