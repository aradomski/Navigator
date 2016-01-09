package pl.edu.radomski.navigator;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by adam on 1/9/16.
 */
public class TestUtils {

    public static void testMethodParams(ParameterSpec parameterSpec, String parameterName, String parameterType, Modifier... modifiers) {
        assertEquals(parameterName, parameterSpec.name);
        assertEquals(parameterType, parameterSpec.type.toString());
        assertEquals(0, parameterSpec.annotations.size());

        assertEquals(modifiers.length, parameterSpec.modifiers.size());
        for (Modifier modifier : modifiers) {
            assertTrue(parameterSpec.modifiers.contains(modifier));
        }
    }

    public static void testMethodSpec(MethodSpec methodSpec, String name, TypeName returnType, Modifier... modifiers) {
        assertEquals(name, methodSpec.name);

        assertEquals(modifiers.length, methodSpec.modifiers.size());
        for (Modifier modifier : modifiers) {
            assertTrue(methodSpec.modifiers.contains(modifier));
        }
        assertEquals(returnType, methodSpec.returnType);
    }

    public static void testMethodSpecForResult(MethodSpec methodSpec, String name, String groupName, TypeName returnType, Modifier... modifiers) {
        assertEquals(name + pl.edu.radomski.navigator.utils.CodeGeneratorHelper.firstLetterToUpperCase(groupName) + "ForResult", methodSpec.name);

        assertEquals(modifiers.length, methodSpec.modifiers.size());
        for (Modifier modifier : modifiers) {
            assertTrue(methodSpec.modifiers.contains(modifier));
        }
        assertEquals(returnType, methodSpec.returnType);
    }
}
