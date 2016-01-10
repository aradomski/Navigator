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


import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

import pl.edu.radomski.navigator.navigable.NavigableAnnotatedClass;
import pl.edu.radomski.navigator.utils.AndroidSpecificClassProvider;
import pl.edu.radomski.navigator.utils.CodeGeneratorHelper;

import static com.squareup.javapoet.JavaFile.builder;

/**
 * Created by adam on 8/25/15.
 */
public class ResultLoaderCodeGenerator {
    public static final String PARCELABLE = "Parcelable";
    public static final String SERIALIZABLE = "serializable";

    private final ProcessingEnvironment processingEnv;

    public ResultLoaderCodeGenerator(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    public void createResultLoader(NavigableAnnotatedClass value) {
        String resultLoaderClassName = CodeGeneratorHelper.getResultLoaderClassName(value.getAnnotatedClassName(), value.getNavigableAnnotation());
        TypeSpec.Builder builder = TypeSpec.classBuilder(resultLoaderClassName).addModifiers(Modifier.FINAL, Modifier.PUBLIC);
        TypeName intentTypeName = AndroidSpecificClassProvider.getIntentTypeName();

        String qualifiedName = value.getTypeElement().getQualifiedName().toString();
        String activityPackage = qualifiedName.substring(0, qualifiedName.lastIndexOf("."));
//        TypeName typeName = ClassName.get(activityPackage, value.getAnnotatedClassName());


        StringBuilder loadLine = new StringBuilder();
        List<VariableElement> variableElements;
        TypeSpec resultClass;
        for (Map.Entry<String, List<VariableElement>> stringListEntry : value.getResultAnnotatedFields().entrySet()) {
            String group = stringListEntry.getKey();
            if (group.trim().length() > 0) {
                group = CodeGeneratorHelper.firstLetterToUpperCase(group);
            }

            variableElements = stringListEntry.getValue();

            resultClass = generateResultClass(value.getAnnotatedClassName(), group, variableElements, value);
            builder.addType(resultClass);


            MethodSpec.Builder loadBuilder = MethodSpec.methodBuilder("load" + group);
            loadBuilder.addModifiers(Modifier.STATIC, Modifier.PUBLIC);

            loadBuilder.addParameter(intentTypeName, "intent", Modifier.FINAL);

            loadBuilder.addCode(resultClass.name + " result =new " + resultClass.name + "();\n");

            for (VariableElement variableElement : variableElements) {
                loadBuilder.beginControlFlow("if (intent.hasExtra(\"" + variableElement.getSimpleName() + "\"))");
                String qualifiedType = variableElement.asType().toString();
                String variableType = qualifiedType.substring(qualifiedType.lastIndexOf(".") + 1, qualifiedType.length());
                String serializableParcelableType = "";
                if (CodeGeneratorHelper.checkIfIsParcelable(processingEnv, qualifiedType)) {
                    serializableParcelableType = variableType;
                    variableType = PARCELABLE;
                } else if (CodeGeneratorHelper.checkIfIsSerializable(processingEnv, qualifiedType)) {
                    serializableParcelableType = variableType;
                    variableType = SERIALIZABLE;
                }
                loadLine.append("result.").append(variableElement.getSimpleName()).append(" = intent.get");
                switch (variableType) {
                    case "boolean[]":
                        loadLine.append("BooleanArray" + "Extra(\"" + variableElement.getSimpleName() + "\");");
                        break;
                    case "Boolean":
                    case "boolean":
                        loadLine.append("Boolean" + "Extra(\"" + variableElement.getSimpleName() + "\", false);");
                        break;

                    case "byte[]":
                        loadLine.append("ByteArray" + "Extra(\"" + variableElement.getSimpleName() + "\");");
                        break;
                    case "Byte":
                    case "byte":
                        loadLine.append("Byte" + "Extra(\"" + variableElement.getSimpleName() + "\", (byte) 0);");
                        break;

                    case "char[]":
                        loadLine.append("CharArray" + "Extra(\"" + variableElement.getSimpleName() + "\");");
                        break;
                    case "Character":
                    case "char":
                        loadLine.append("Char" + "Extra(\"" + variableElement.getSimpleName() + "\", '\\0');");
                        break;

                    case "CharSequence[]":
                        loadLine.append("CharSequenceArray" + "Extra(\"" + variableElement.getSimpleName() + "\");");
                        break;
                    case "CharSequence>":
                        loadLine.append("CharSequenceArrayList" + "Extra(\"" + variableElement.getSimpleName() + "\");");
                        break;
                    case "CharSequence":
                        loadLine.append("CharSequence" + "Extra(\"" + variableElement.getSimpleName() + "\");");
                        break;

                    case "double[]":
                        loadLine.append("DoubleArray" + "Extra(\"" + variableElement.getSimpleName() + "\");");
                        break;
                    case "Double":
                    case "double":
                        loadLine.append("Double" + "Extra(\"" + variableElement.getSimpleName() + "\", 0d);");
                        break;

                    case "float[]":
                        loadLine.append("FloatArray" + "Extra(\"" + variableElement.getSimpleName() + "\");");
                        break;
                    case "Float":
                    case "float":
                        loadLine.append("Float" + "Extra(\"" + variableElement.getSimpleName() + "\", 0f);");
                        break;

                    case "int[]":
                        loadLine.append("IntArray" + "Extra(\"" + variableElement.getSimpleName() + "\");");
                        break;
                    case "Integer":
                    case "int":
                        loadLine.append("Int" + "Extra(\"" + variableElement.getSimpleName() + "\", 0);");
                        break;
                    case "Integer>":
                        loadLine.append("IntegerArrayList" + "Extra(\"" + variableElement.getSimpleName() + "\");");
                        break;

                    case "long[]":
                        loadLine.append("LongArray" + "Extra(\"" + variableElement.getSimpleName() + "\");");
                        break;
                    case "Long":
                    case "long":
                        loadLine.append("Long" + "Extra(\"" + variableElement.getSimpleName() + "\", 0L);");
                        break;

                    case PARCELABLE:
                        if (serializableParcelableType.contains("[]")) {
                            String cleanType = qualifiedType.replace("[]", "");
                            loadLine = new StringBuilder();
                            loadLine.append("android.os.Parcelable[] pa").append(" = intent.get");
                            loadLine.append("ParcelableArray" + "Extra(\"" + variableElement.getSimpleName() + "\");\n");
                            loadLine.append(cleanType + "[] array = new " + cleanType + "[pa.length];\n");
                            loadLine.append("System.arraycopy(pa, 0, array, 0, pa.length);\n");
                            loadLine.append("result.").append(variableElement.getSimpleName()).append(" = array;");
                        } else if (serializableParcelableType.contains(">")) {
                            loadLine.append("ParcelableArrayList" + "Extra(\"" + variableElement.getSimpleName() + "\");");
                        } else {
                            loadLine.append("Parcelable" + "Extra(\"" + variableElement.getSimpleName() + "\");");
                        }
                        break;
                    case SERIALIZABLE:
                        loadLine = new StringBuilder();
                        loadLine.append("result.")
                                .append(variableElement.getSimpleName())
                                .append(" = (").append(qualifiedType)
                                .append(") intent.get")
                                .append("Serializable" + "Extra(\"")
                                .append(variableElement.getSimpleName())
                                .append("\");");
                        break;

                    case "short[]":
                        loadLine.append("ShortArray" + "Extra(\"" + variableElement.getSimpleName() + "\");");
                        break;
                    case "Short":
                    case "short":
                        loadLine.append("Short" + "Extra(\"" + variableElement.getSimpleName() + "\", (short) 0);");
                        break;

                    case "String[]":
                        loadLine.append("StringArray" + "Extra(\"" + variableElement.getSimpleName() + "\");");
                        break;
                    case "String":
                        loadLine.append(variableType + "Extra(\"" + variableElement.getSimpleName() + "\");");
                        break;
                    case "String>":
                        loadLine.append("StringArrayList" + "Extra(\"" + variableElement.getSimpleName() + "\");");
                        break;


                    default:
                        throw new RuntimeException("not found: " + variableType);
                }
                loadLine.append("\n");
                loadBuilder.addCode(loadLine.toString());
                loadBuilder.endControlFlow();
                loadLine = new StringBuilder();
            }
            loadBuilder.addCode("return result;\n");
            loadBuilder.returns(ClassName.get(activityPackage + "." + resultLoaderClassName, resultClass.name));

            builder.addMethod(loadBuilder.build());
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

    private TypeSpec generateResultClass(String annotatedClassName, String group, List<VariableElement> variableElements, NavigableAnnotatedClass value) {
        TypeSpec.Builder resultClassBuilder = TypeSpec.classBuilder(CodeGeneratorHelper.getResultClassName(annotatedClassName, group, value.getNavigableAnnotation()))
                .addModifiers(Modifier.FINAL, Modifier.PUBLIC, Modifier.STATIC);

        for (VariableElement variableElement : variableElements) {
            resultClassBuilder.addField(TypeName.get(variableElement.asType()), variableElement.getSimpleName().toString(), Modifier.PUBLIC);
        }

        return resultClassBuilder.build();
    }
}
