package pl.edu.radomski.navigator;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.sun.org.apache.xpath.internal.operations.Mod;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import pl.edu.radomski.navigator.navigable.NavigableAnnotatedClass;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by adam on 1/9/16.
 */
@RunWith(org.junit.runners.JUnit4.class)
public class NavigatorCodeGeneratorTest {

    @Mock
    NavigableAnnotatedClass navigableAnnotatedClass;
    private NavigatorCodeGenerator navigatorCodeGenerator;

    @Before
    public void setUp() throws Exception {
        ProcessingEnvironment processingEnv = Mockito.mock(ProcessingEnvironment.class);
        Filer filer = Mockito.mock(Filer.class);
        when(processingEnv.getFiler()).thenReturn(filer);

        navigatorCodeGenerator = new NavigatorCodeGenerator(processingEnv);
    }

    @Test
    public void testCreateMethod() throws Exception {
        NavigableAnnotatedClass navigableAnnotatedClass = Mockito.mock(NavigableAnnotatedClass.class);

        List<MethodSpec> method = navigatorCodeGenerator.createMethod(navigableAnnotatedClass);

    }


    @Test
    public void testHandleParamActivity() {
        String className = TestConstants.testPackageName + ".AA";


        NavigableAnnotatedClass navigableAnnotatedClass = Mockito.mock(NavigableAnnotatedClass.class);

        TypeElement typeElement = Mockito.mock(TypeElement.class);
        Name name = Mockito.mock(Name.class);

        when(typeElement.getQualifiedName()).thenReturn(name);
        when(name.toString()).thenReturn(className);
        when(navigableAnnotatedClass.getTypeElement()).thenReturn(typeElement);
        when(navigableAnnotatedClass.getTypeElement().getQualifiedName()).thenReturn(name);

        when(navigableAnnotatedClass.getAnnotatedClassName()).thenReturn(className);




        MethodSpec methodSpec = navigatorCodeGenerator.handleParamActivity("bbb", navigableAnnotatedClass);

        // Test method signature
        TestUtils.testMethodSpec(methodSpec, className, TypeName.VOID, Modifier.PUBLIC, Modifier.STATIC);

        // Test method params
        assertEquals(2, methodSpec.parameters.size());

        TestUtils.testMethodParams(methodSpec.parameters.get(0), "activity", "android.app.Activity", Modifier.FINAL);
        TestUtils.testMethodParams(methodSpec.parameters.get(1), "flags", "java.lang.Integer[]", Modifier.FINAL);

        //Test method annotations
        assertEquals(0, methodSpec.annotations.size());

    }

    @Test
    public void testHandleResultActivity() {

    }

    @Test
    public void testHandleNoParamNoResultActivity() {
        String className = TestConstants.testPackageName + ".AA";


        NavigableAnnotatedClass navigableAnnotatedClass = Mockito.mock(NavigableAnnotatedClass.class);

        TypeElement typeElement = Mockito.mock(TypeElement.class);
        Name name = Mockito.mock(Name.class);

        when(typeElement.getQualifiedName()).thenReturn(name);
        when(name.toString()).thenReturn(className);
        when(navigableAnnotatedClass.getTypeElement()).thenReturn(typeElement);
        when(navigableAnnotatedClass.getTypeElement().getQualifiedName()).thenReturn(name);

        when(navigableAnnotatedClass.getAnnotatedClassName()).thenReturn(className);


        MethodSpec methodSpec = navigatorCodeGenerator.handleNoParamNoResultActivity(navigableAnnotatedClass);

        // Test method signature
        TestUtils.testMethodSpec(methodSpec, className, TypeName.VOID, Modifier.PUBLIC, Modifier.STATIC);

        // Test method params
        assertEquals(2, methodSpec.parameters.size());

        TestUtils.testMethodParams(methodSpec.parameters.get(0), "activity", "android.app.Activity", Modifier.FINAL);
        TestUtils.testMethodParams(methodSpec.parameters.get(1), "flags", "java.lang.Integer[]", Modifier.FINAL);

        //Test method annotations
        assertEquals(0, methodSpec.annotations.size());

    }
}