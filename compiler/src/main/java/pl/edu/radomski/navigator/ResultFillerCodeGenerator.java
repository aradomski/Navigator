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

import android.content.Intent;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

import pl.edu.radomski.navigator.navigable.NavigableAnnotatedClass;

import static com.squareup.javapoet.JavaFile.builder;

/**
 * Created by adam on 8/24/15.
 */
public class ResultFillerCodeGenerator {
    private final ProcessingEnvironment processingEnv;

    public ResultFillerCodeGenerator(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    public void createResultFiller(NavigableAnnotatedClass value) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(value.getAnnotatedClassName() + "ResultFiller").addModifiers(Modifier.FINAL);
        TypeName intentTypeName = ClassName.get(Intent.class);
        String qualifiedName = value.getTypeElement().getQualifiedName().toString();
        String activityPackage = qualifiedName.substring(0, qualifiedName.lastIndexOf("."));


        TypeName typeName = ClassName.get(activityPackage, value.getAnnotatedClassName());



        MethodSpec.Builder fillBuilder;


        for (String groups : value.getResultAnnotatedFields().keySet()) {
            fillBuilder = MethodSpec.methodBuilder("fillResult" + groups);
            fillBuilder.addModifiers(Modifier.STATIC);
            fillBuilder.addParameter(typeName,"activity", Modifier.FINAL);
            fillBuilder.returns(intentTypeName);
            fillBuilder.addCode("$T intent = new $T();\n", intentTypeName, intentTypeName);
            for (VariableElement variableElement : value.getResultAnnotatedFields().get(groups)) {
//                fillBuilder.addParameter(TypeName.get(variableElement.asType()), variableElement.getSimpleName().toString(), Modifier.FINAL);

                fillBuilder.addCode("intent.putExtra(\"" + variableElement.getSimpleName().toString() + "\", activity." + variableElement.getSimpleName().toString() + ");\n");

            }

            fillBuilder.addCode("return intent;\n");
            builder.addMethod(fillBuilder.build());
        }

        TypeSpec typeSpec = builder.build();
        JavaFile javaFile;
        javaFile = builder(activityPackage, typeSpec).build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
