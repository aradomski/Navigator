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

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by adam on 8/23/15.
 */
public class CodeGeneratorHelper {
    static String buildMethodName(String annotatedClassName) {
        char c[] = annotatedClassName.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        annotatedClassName = new String(c);
        return annotatedClassName;
    }

    static String firstLetterToUpperCase(String name) {
        if (name.trim().length() == 0) {
            return name;
        }
        char c[] = name.toCharArray();
        c[0] = Character.toUpperCase(c[0]);
        name = new String(c);
        return name;
    }

    static boolean checkIfIsParcelable(ProcessingEnvironment processingEnvironment, String type) {
        TypeElement parcelableElement = processingEnvironment.getElementUtils().getTypeElement("android.os.Parcelable");
        TypeElement elementType;
        if (type.contains("<") && type.contains(">")) {
            elementType = processingEnvironment.getElementUtils().getTypeElement(type.substring(type.indexOf("<") + 1, type.indexOf(">")));
        } else {
            elementType = processingEnvironment.getElementUtils().getTypeElement(type.replace("[]", "").replace(">", ""));
        }
        if (elementType != null) {
            for (TypeMirror typeMirror : elementType.getInterfaces()) {
                if (typeMirror.toString().equals(parcelableElement.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean checkIfIsSerializable(ProcessingEnvironment processingEnvironment, String type) {
        if (type.equals("java.lang.String") || type.equals("java.lang.Boolean") || type.equals("java.lang.Character")) {
            //it is serializable but we handle it different way
            return false;
        }


        TypeElement serializableElement = processingEnvironment.getElementUtils().getTypeElement("java.io.Serializable");
        TypeElement elementType;

        elementType = processingEnvironment.getElementUtils().getTypeElement(type);

        if (elementType != null) {
            for (TypeMirror typeMirror : elementType.getInterfaces()) {
                if (typeMirror.toString().equals(serializableElement.toString())) {
                    return true;
                }
            }
        }
        return false;
    }
}
