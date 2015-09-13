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


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import pl.edu.radomski.navigator.exceptions.VariableElementValidationException;
import pl.edu.radomski.navigator.navigable.NavigableAnnotatedClass;
import pl.edu.radomski.navigator.navigable.NavigableAnnotationValidator;

import static java.util.Collections.singleton;
import static javax.lang.model.SourceVersion.latestSupported;
import static javax.tools.Diagnostic.Kind.NOTE;

/**
 * Created by adam on 8/18/15.
 */
@SupportedAnnotationTypes({"pl.edu.radomski.navigator.Param", "pl.edu.radomski.navigator.Result", "pl.edu.radomski.navigator.Navigable"})
public class NavigatorAnnotationProcessor extends AbstractProcessor {

    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return singleton(Navigable.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        TypeElement typeElement;
        HashMap<String, List<NavigableAnnotatedClass>> navigableAnnotatedClasses = new HashMap<>();

        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Navigable.class)) {
            if (!(annotatedElement instanceof TypeElement)) {
                return false;
            }
            typeElement = (TypeElement) annotatedElement;
            ArrayList<String> classErrors = NavigableAnnotationValidator.isValidClass(typeElement);
            if (classErrors.size() > 0) {
                for (String message : classErrors) {
                    messager.printMessage(Diagnostic.Kind.ERROR, message, typeElement);
                }
                return false;
            }
            try {
                Navigable annotation = typeElement.getAnnotation(Navigable.class);
                CollectionUtils.addToHashMapWithList(navigableAnnotatedClasses, annotation.group(), NavigableAnnotatedClass.from(typeElement));
            } catch (VariableElementValidationException e) {
                for (String error : e.errors) {
                    messager.printMessage(Diagnostic.Kind.ERROR, error, typeElement);
                }
                return false;
            }

        }
        try {
            generate(navigableAnnotatedClasses);
        } catch (NoPackageNameException | IOException e) {
            e.printStackTrace();
            messager.printMessage(Diagnostic.Kind.ERROR, "Couldn't generate class");
            return false;
        }

        return true;
    }

    private void generate(HashMap<String, List<NavigableAnnotatedClass>> navigableAnnotatedClasses) throws NoPackageNameException, IOException {
        if (navigableAnnotatedClasses.size() == 0) {
            return;
        }
        messager.printMessage(NOTE, "found " + navigableAnnotatedClasses.size() + " navigable groups");

        List<NavigableAnnotatedClass> elementFromMapWithList = CollectionUtils.getElementFromMapWithList(navigableAnnotatedClasses);
        NavigatorCodeGenerator navigatorCodeGenerator = new NavigatorCodeGenerator(processingEnv);
        ParamLoaderCodeGenerator paramLoaderCodeGenerator = new ParamLoaderCodeGenerator(processingEnv);
        ResultFillerCodeGenerator resultFillerCodeGenerator = new ResultFillerCodeGenerator(processingEnv);
        ResultLoaderCodeGenerator resultLoaderCodeGenerator = new ResultLoaderCodeGenerator(processingEnv);


        for (NavigableAnnotatedClass navigableAnnotatedClass : elementFromMapWithList) {
            if (navigableAnnotatedClass.getParamAnnotatedFields().size() > 0) {
                paramLoaderCodeGenerator.createParamLoader(navigableAnnotatedClass);
            }
        }

        for (NavigableAnnotatedClass navigableAnnotatedClass : elementFromMapWithList) {
            if (navigableAnnotatedClass.getResultAnnotatedFields().size() > 0) {
                resultFillerCodeGenerator.createResultFiller(navigableAnnotatedClass);
                resultLoaderCodeGenerator.createResultLoader(navigableAnnotatedClass);
            }
        }


        navigatorCodeGenerator.generateClass(elementFromMapWithList);


    }
}


