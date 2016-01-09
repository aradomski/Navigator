package pl.edu.radomski.navigator.utils;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

/**
 * Created by adam on 1/9/16.
 */
public class AndroidSpecificClassProvider {

    public static TypeName getIntentTypeName(){
//        TypeName intentTypeName = ClassName.get(Intent.class);
        TypeName intentTypeName = ClassName.get("android.content", "Intent");
        return  intentTypeName;
    }


    public static TypeName getActivityTypeName(){
//        TypeName activityTypeName = ClassName.get(Activity.class);
        TypeName activityTypeName = ClassName.get("android.app", "Activity");
        return  activityTypeName;
    }



}
