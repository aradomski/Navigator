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
import java.util.HashMap;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import pl.edu.radomski.navigator.utils.AnnotationValidator;
import pl.edu.radomski.navigator.utils.CollectionUtils;
import pl.edu.radomski.navigator.Navigable;
import pl.edu.radomski.navigator.Param;
import pl.edu.radomski.navigator.Result;
import pl.edu.radomski.navigator.exceptions.VariableElementValidationException;
import pl.edu.radomski.navigator.param.ParamAnnotatedVariableElementValidator;

/**
 * Created by adam on 8/18/15.
 */
public class NavigableAnnotatedClass {


    public static NavigableAnnotatedClass from(TypeElement typeElement) throws VariableElementValidationException {

        Navigable annotation = typeElement.getAnnotation(Navigable.class);

        NavigableAnnotatedClass navigableAnnotatedClass = new NavigableAnnotatedClass(typeElement, annotation.group());
        HashMap<String, List<VariableElement>> paramAnnotatedFields = new HashMap<>();
        HashMap<String, List<VariableElement>> resultAnnotatedFields = new HashMap<>();
        ArrayList<String> errors;
        Param paramAnnotation;
        Result resultAnnotation;
        for (Element element : typeElement.getEnclosedElements()) {
            if (!(element instanceof VariableElement)) {
                continue;
            }
            VariableElement variableElement = (VariableElement) element;
            if (AnnotationValidator.isAnnotated(variableElement, Param.class)) {
                paramAnnotation = variableElement.getAnnotation(Param.class);
                errors = ParamAnnotatedVariableElementValidator.isValid(variableElement);
                if (errors.size() == 0) {
                    CollectionUtils.addToHashMapWithList(paramAnnotatedFields, paramAnnotation.group(), variableElement);
                } else {
                    throw new VariableElementValidationException(errors);
                }
            }
            if (AnnotationValidator.isAnnotated(variableElement, Result.class)) {
                resultAnnotation = variableElement.getAnnotation(Result.class);
                errors = ParamAnnotatedVariableElementValidator.isValid(variableElement);
                if (errors.size() == 0) {
                    CollectionUtils.addToHashMapWithList(resultAnnotatedFields, resultAnnotation.group(), variableElement);
                } else {
                    throw new VariableElementValidationException(errors);
                }
            }
        }

        navigableAnnotatedClass.paramAnnotatedFields = paramAnnotatedFields;
        navigableAnnotatedClass.resultAnnotatedFields = resultAnnotatedFields;
        navigableAnnotatedClass.navigableAnnotation = annotation;

        return navigableAnnotatedClass;
    }

    private HashMap<String, List<VariableElement>> paramAnnotatedFields = new HashMap<>();
    private HashMap<String, List<VariableElement>> resultAnnotatedFields = new HashMap<>();
    private final String annotatedClassName;
    private final TypeElement typeElement;
    private final String group;
    private Navigable navigableAnnotation;


    public NavigableAnnotatedClass(TypeElement typeElement, String group) {
        this.annotatedClassName = typeElement.getSimpleName().toString();
        this.typeElement = typeElement;
        this.group = group;

    }

    public HashMap<String, List<VariableElement>> getParamAnnotatedFields() {
        return paramAnnotatedFields;
    }

    public HashMap<String, List<VariableElement>> getResultAnnotatedFields() {
        return resultAnnotatedFields;
    }

    public String getAnnotatedClassName() {
        return annotatedClassName;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }

    public String getGroup() {
        return group;
    }

    public Navigable getNavigableAnnotation() {
        return navigableAnnotation;
    }
}
