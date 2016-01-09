package pl.edu.radomski.navigator;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import pl.edu.radomski.navigator.exceptions.NotAllFieldsAreAnnotatedForResultException;
import pl.edu.radomski.navigator.navigable.NavigableAnnotatedClass;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Created by adam on 1/9/16.
 */
@RunWith(Parameterized.class)
public class NavigatorCodeGeneratorParameterizedTest {

    @Mock
    NavigableAnnotatedClass navigableAnnotatedClass;
    private NavigatorCodeGenerator navigatorCodeGenerator;

    @Parameterized.Parameters(name = "{index}: {0}, {1}, {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0, new ArrayList<VariableElement>() {{
                    add(generateVariableElementMock(true, "aaaa"));
                    add(generateVariableElementMock(true, "bbbb"));
                }}, null},
                {0, new ArrayList<VariableElement>() {{
                    add(generateVariableElementMock(true, "aaaa"));
                    add(generateVariableElementMock(false, "bbbb"));
                }}, NotAllFieldsAreAnnotatedForResultException.class}
        });
    }

    @Parameterized.Parameter(value = 0)
    public int expectedResult;
    @Parameterized.Parameter(value = 1)
    public ArrayList<VariableElement> variableElements;
    @Parameterized.Parameter(value = 2)
    public Class<Throwable> exceptionClass;
    @Rule
    public ExpectedException expected = ExpectedException.none();

    private static VariableElement generateVariableElementMock(boolean forResult, String variableName) {
        VariableElement mockVariableElement = Mockito.mock(VariableElement.class);
        Param param = Mockito.mock(Param.class);
        when(param.forResult()).thenReturn(forResult);
        when(mockVariableElement.getAnnotation(Param.class)).thenReturn(param);

        TypeMirror typeMirror = Mockito.mock(TypeMirror.class);

        when(mockVariableElement.asType()).thenReturn(typeMirror);

        Name name = Mockito.mock(Name.class);
        when(name.toString()).thenReturn(variableName);


        when(mockVariableElement.getSimpleName()).thenReturn(name);
        return mockVariableElement;
    }


    @Before
    public void setUp() throws Exception {
        ProcessingEnvironment processingEnv = Mockito.mock(ProcessingEnvironment.class);
        Filer filer = Mockito.mock(Filer.class);
        when(processingEnv.getFiler()).thenReturn(filer);


        TypeName typeName = Mockito.mock(TypeName.class);
        navigatorCodeGenerator = spy(new NavigatorCodeGenerator(processingEnv));
        doReturn(typeName).when(navigatorCodeGenerator).getTypeName(any(VariableElement.class));
    }

    @Test
    public void testHandleParamActivity() {
        if (exceptionClass != null) {
            expected.expect(exceptionClass);
        }


        String className = TestConstants.testPackageName + ".ClassName";
        String groupName = "groupName";

        NavigableAnnotatedClass navigableAnnotatedClass = Mockito.mock(NavigableAnnotatedClass.class);

        TypeElement typeElement = Mockito.mock(TypeElement.class);
        Name name = Mockito.mock(Name.class);

        when(typeElement.getQualifiedName()).thenReturn(name);
        when(name.toString()).thenReturn(className);
        when(navigableAnnotatedClass.getTypeElement()).thenReturn(typeElement);
        when(navigableAnnotatedClass.getTypeElement().getQualifiedName()).thenReturn(name);

        when(navigableAnnotatedClass.getAnnotatedClassName()).thenReturn(className);

        HashMap<String, List<VariableElement>> stringListHashMap = Mockito.mock(HashMap.class);

        when(navigableAnnotatedClass.getParamAnnotatedFields()).thenReturn(stringListHashMap);
        when(stringListHashMap.get(anyString())).thenReturn(variableElements);


        MethodSpec methodSpec = navigatorCodeGenerator.handleParamActivity(groupName, navigableAnnotatedClass);


        // Test method signature
        TestUtils.testMethodSpecForResult(methodSpec, className, groupName, TypeName.VOID, Modifier.PUBLIC, Modifier.STATIC);

        // Test method params
        assertEquals(2 + variableElements.size(), methodSpec.parameters.size());

        TestUtils.testMethodParams(methodSpec.parameters.get(0), "activity", "android.app.Activity", Modifier.FINAL);
        TestUtils.testMethodParams(methodSpec.parameters.get(1), "flags", "java.lang.Integer[]", Modifier.FINAL);

        //Test method annotations
        assertEquals(0, methodSpec.annotations.size());

    }

}
