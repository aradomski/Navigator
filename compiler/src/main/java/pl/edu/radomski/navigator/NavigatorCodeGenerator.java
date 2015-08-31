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

import android.app.Activity;
import android.content.Intent;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

import pl.edu.radomski.navigator.navigable.NavigableAnnotatedClass;

import static com.squareup.javapoet.JavaFile.builder;

final class NavigatorCodeGenerator {
    private static final String CLASS_NAME = "Navigator";
    private static HashMap<String, TypeSpec.Builder> builderForInnerClass = new HashMap<>();
    private ProcessingEnvironment processingEnv;

    public NavigatorCodeGenerator(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }


    public void generateClass(List<NavigableAnnotatedClass> navigableAnnotatedClassList) throws IOException, NoPackageNameException {
        TypeSpec.Builder builder = TypeSpec.classBuilder(CLASS_NAME).addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        TypeSpec.Builder innerClassBuilder;
        for (NavigableAnnotatedClass navigableAnnotatedClass : navigableAnnotatedClassList) {
            if (navigableAnnotatedClass.getGroup().trim().length() == 0) {
                builder.addMethods(createMethod(navigableAnnotatedClass));
            } else {
                if ((innerClassBuilder = builderForInnerClass.get(navigableAnnotatedClass.getGroup())) == null) {
                    innerClassBuilder = TypeSpec.classBuilder(navigableAnnotatedClass.getGroup()).addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC);
                    builderForInnerClass.put(navigableAnnotatedClass.getGroup(), innerClassBuilder);
                }
                innerClassBuilder.addMethods(createMethod(navigableAnnotatedClass));
            }
        }
        for (TypeSpec.Builder builder1 : builderForInnerClass.values()) {
            builder.addType(builder1.build());
        }
        TypeSpec generatedClass = builder.build();

        String packageName = Utils.getPackageName(processingEnv.getElementUtils(), navigableAnnotatedClassList.get(0).getTypeElement());

        JavaFile javaFile;
        javaFile = builder(packageName, generatedClass).build();
        javaFile.writeTo(processingEnv.getFiler());
    }

    private List<MethodSpec> createMethod(NavigableAnnotatedClass value) {
        ArrayList<MethodSpec> methods = new ArrayList<>();

        if (value.getParamAnnotatedFields().size() == 0 && value.getResultAnnotatedFields().size() == 0) {
            methods.add(handleNoParamNoResultActivity(value));
        } else if (value.getParamAnnotatedFields().size() > 0 && value.getResultAnnotatedFields().size() == 0) {
            for (String group : value.getParamAnnotatedFields().keySet()) {
                methods.add(handleParamActivity(group, value));
            }
        } else if (value.getParamAnnotatedFields().size() == 0 && value.getResultAnnotatedFields().size() > 0) {
//            for (String group : value.getResultAnnotatedFields().keySet()) {
            methods.add(handleResultActivity(value));
//            }
        } else if (value.getParamAnnotatedFields().size() > 0 && value.getResultAnnotatedFields().size() > 0) {
//            for (String group : value.getResultAnnotatedFields().keySet()) {
//                methods.add(handleResultActivity( value));
//            }
            for (String group : value.getParamAnnotatedFields().keySet()) {
                methods.add(handleParamActivity(group, value));
            }
        } else {
            throw new RuntimeException("Unpredicted situation");
        }


        return methods;
    }

    private MethodSpec handleResultActivity(NavigableAnnotatedClass value) {
        TypeName activityTypeName = ClassName.get(Activity.class);
        TypeName intentTypeName = ClassName.get(Intent.class);
        String activityQualifiedName = value.getTypeElement().getQualifiedName().toString();
        ArrayTypeName integerArray = ArrayTypeName.of(ClassName.get(Integer.class));


        String annotatedClassName = CodeGeneratorHelper.buildMethodName(value.getAnnotatedClassName() + "ForResult");
        MethodSpec.Builder builder = MethodSpec.methodBuilder(annotatedClassName)
                .addParameter(activityTypeName, "activity", Modifier.FINAL)
                .addParameter(TypeName.INT, "requestCode", Modifier.FINAL)
                .addParameter(integerArray, "flags", Modifier.FINAL)
                .varargs()
                .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                .addStatement("$T intent = new $T(activity, " + activityQualifiedName + ".class)", intentTypeName, intentTypeName)
                .beginControlFlow("for (Integer flag : flags)")
                .addStatement("intent.addFlags(flag)")
                .endControlFlow()
                .addStatement("activity.startActivityForResult(intent, requestCode)");
        return builder.build();
    }

    private MethodSpec handleParamActivity(String group, NavigableAnnotatedClass value) {
        TypeName activityTypeName = ClassName.get(Activity.class);
        TypeName intentTypeName = ClassName.get(Intent.class);
        String activityQualifiedName = value.getTypeElement().getQualifiedName().toString();
        ArrayTypeName integerArray = ArrayTypeName.of(ClassName.get(Integer.class));

        int forResultCounter = 0;
        for (VariableElement param : value.getParamAnnotatedFields().get(group)) {
            if (param.getAnnotation(Param.class).forResult()) {
                forResultCounter++;
            }
        }

        String methodName = value.getAnnotatedClassName()+CodeGeneratorHelper.firstLetterToUpperCase(group);
        if (forResultCounter > 0) {
            methodName += "ForResult";
        }
        String annotatedClassName = CodeGeneratorHelper.buildMethodName(methodName);
        MethodSpec.Builder builder = MethodSpec.methodBuilder(annotatedClassName);
        builder.addParameter(activityTypeName, "activity", Modifier.FINAL);
        TypeName paramTypeName;
        System.out.println(value.getParamAnnotatedFields() + "   group: " + group);
        for (VariableElement param : value.getParamAnnotatedFields().get(group)) {
            paramTypeName = TypeName.get(param.asType());
            builder.addParameter(paramTypeName, param.getSimpleName().toString(), Modifier.FINAL);
        }

        if (forResultCounter > 0 && (forResultCounter != value.getParamAnnotatedFields().get(group).size())) {
            throw new RuntimeException("When at least one param is marked as \"forResult\" each element of this group should be marked as \"forResult\"");
        }


        if (forResultCounter > 0) {
            builder.addParameter(TypeName.INT, "requestCode", Modifier.FINAL);
        }

        builder.addParameter(integerArray, "flags", Modifier.FINAL)
                .varargs()
                .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                .addStatement("$T intent = new $T(activity, " + activityQualifiedName + ".class)", intentTypeName, intentTypeName)
                .beginControlFlow("for (Integer flag : flags)")
                .addStatement("intent.addFlags(flag)")
                .endControlFlow();


        for (VariableElement param : value.getParamAnnotatedFields().get(group)) {
            builder.addStatement("intent.putExtra(\"" + param.getSimpleName().toString() + "\"," + param.getSimpleName().toString() + ")");
        }
        if (forResultCounter > 0) {
            builder.addStatement("activity.startActivityForResult(intent, requestCode)");
        } else {
            builder.addStatement("activity.startActivity(intent)");
        }
        return builder.build();
    }


    private static MethodSpec handleNoParamNoResultActivity(NavigableAnnotatedClass value) {
        TypeName activityTypeName = ClassName.get(Activity.class);
        TypeName intentTypeName = ClassName.get(Intent.class);
        String activityQualifiedName = value.getTypeElement().getQualifiedName().toString();
        ArrayTypeName integerArray = ArrayTypeName.of(ClassName.get(Integer.class));


        String annotatedClassName = CodeGeneratorHelper.buildMethodName(value.getAnnotatedClassName());
        MethodSpec.Builder builder = MethodSpec.methodBuilder(annotatedClassName)
                .addParameter(activityTypeName, "activity", Modifier.FINAL)
                .addParameter(integerArray, "flags", Modifier.FINAL)
                .varargs()
                .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                .addStatement("$T intent = new $T(activity, " + activityQualifiedName + ".class)", intentTypeName, intentTypeName)
                .beginControlFlow("for (Integer flag : flags)")
                .addStatement("intent.addFlags(flag)")
                .endControlFlow()
                .addStatement("activity.startActivity(intent)");
        return builder.build();
    }
}
