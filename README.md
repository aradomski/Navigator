# Navigator 
Navigator code generator for Android.
This library creates boilerplate code useful to start new activities.

Provides **Navigator** class that keeps all start activity methods with appropriate parameters.

Provides **\*ParamLoader** classes for easy loading parameters from **Intents**

Provides **\*ResultFiller** classes for easy returning values from **Activities** by filling **Intents**

Provides **\*ResultLoader** classes for easy loading results from **Activities**

Provides **\*Result** classes for keeping results from **Activities**

##I want to:

### Get library from maven and include it in my project
#### navigator-compiler [![Maven Central](https://maven-badges.herokuapp.com/maven-central/pl.edu.radomski/navigator-compiler/badge.svg)](https://maven-badges.herokuapp.com/maven-central/pl.edu.radomski/navigator-compiler)
#### navigator-annotations [![Maven Central](https://maven-badges.herokuapp.com/maven-central/pl.edu.radomski/navigator-annotations/badge.svg)](https://maven-badges.herokuapp.com/maven-central/pl.edu.radomski/navigator-annotations)
In project build.gradle file add:

```
classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
```

On top of module build.gradle file add:
```
apply plugin: 'com.neenbedankt.android-apt'
```
then add:
```
 compile 'pl.edu.radomski:navigator-annotations:1.0.0'
 apt 'pl.edu.radomski:navigator-compiler:1.0.3'
``` 
in **dependencies**

### Navigate to simple activity

```java
@Navigable
public class SimpleActivity extends Activity 
```

```java
Navigator.simpleActivity(Activity.this);
```

### Navigate to activity with params

```java
@Navigable
public class ParamsActivity extends Activity {

  @Param
  Integer integerParam;
  @Param
  String stringParam;
  @Param
  Book parcelableParam;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      ParamsActivityParamLoader.load(this);
  }
}
```


```java
Navigator.paramsActivity(Activity.this, 2123, "abcdefgh", book);
```
### Navigate to activity with various groups of params

```java
@Navigable
public class GroupedParamsActivity extends Activity {

    @Param(group = "firstGroup")
    String stringParam;
    @Param(group = "firstGroup")
    Boolean booleanParam;
    @Param(group = "secondGroup")
    Double doubleParam;
    @Param(group = "secondGroup")
    Integer integerParam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GroupedParamsActivityParamLoader.load(this);
    }
}
```

```java
Navigator.groupedParamsActivity(Activity.this, 32.12, 2145);
Navigator.groupedParamsActivity(Activity.this, "abcdefgh", true);
```

### Start Activity for result

```java
@Navigable
public class ForResultActivity extends Activity {
    @Result
    String stringResult = "abcdefghijk";
    
    @Override
    protected View.OnClickListener getOnCloseClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ForResultActivityResultFiller.fillResult(ForResultActivity.this);
                setResult(RESULT_OK, intent);
                finish();
            }
        };
    }
}
```

```java
Navigator.forResultActivityForResult(Activity.this, 123);
```
```java
 ForResultActivityResultLoader.ForResultActivityResult result3 = ForResultActivityResultLoader.load(data);
```

### Start parameterized Activity  for result

```java
@Navigable
public class ParamsForResultActivity extends Activity {

    @Param(forResult = true)
    Integer integerParam;
    @Param(forResult = true)
    String stringParam;
    @Param(forResult = true)
    Book parcelableParam;

    @Result
    String stringResult = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParamsForResultActivityParamLoader.load(this);
    }  
    
    @Override
    protected View.OnClickListener getOnCloseClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ParamsForResultActivityResultFiller.fillResult(ParamsForResultActivity.this);
                setResult(RESULT_OK, intent);
                finish();
            }
        };
    }
}
```

```java
  Navigator.paramsForResultActivityForResult(Activity.this, 123, "abcdefgh", book, 737);
```
```java
 ParamsForResultActivityResultLoader.ParamsForResultActivityResult result1 = ParamsForResultActivityResultLoader.load(data);
```
### Start activity for various results
```java
@Navigable
public class GroupedResultsForResultActivity extends BaseActivity {

    @Param(forResult = true)
    Integer returnGroup;

    @Result
    String stringResult = "string result";
    @Result(group = "firstGroup")
    Boolean booleanResult = true;
    @Result(group = "secondGroup")
    Double doubleResult =123.5;
    @Result(group = "secondGroup")
    Integer integerResult= 6523;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GroupedResultsForResultActivityParamLoader.load(this);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getCanonicalName());
        stringBuilder.append("\n");
        stringBuilder.append(this.toString());
        classInfo.setText(stringBuilder);
    }

    @Override
    protected View.OnClickListener getOnCloseClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (returnGroup) {
                    case 1:
                        intent = GroupedResultsForResultActivityResultFiller.fillResult(GroupedResultsForResultActivity.this);
                        break;
                    case 2:
                        intent = GroupedResultsForResultActivityResultFiller.fillResultfirstGroup(GroupedResultsForResultActivity.this);
                        break;
                    case 3:
                        intent = GroupedResultsForResultActivityResultFiller.fillResultsecondGroup(GroupedResultsForResultActivity.this);
                        break;
                }
                setResult(RESULT_OK, intent);
                finish();
            }
        };
    }
}
```
```java
 Navigator.groupedResultsForResultActivityForResult(BaseActivity.this, 1, 123);
```
```java
 GroupedResultsForResultActivityResultLoader.GroupedResultsForResultActivityResult result4 = GroupedResultsForResultActivityResultLoader.load(data);
 GroupedResultsForResultActivityResultLoader.GroupedResultsForResultActivityFirstGroupResult result5 = GroupedResultsForResultActivityResultLoader.loadFirstGroup(data);
 GroupedResultsForResultActivityResultLoader.GroupedResultsForResultActivitySecondGroupResult result7 = GroupedResultsForResultActivityResultLoader.loadSecondGroup(data);
```

### Create Activity that could be run for result or not.
```java
@Navigable
public class ActivityWithResultOption extends BaseActivity {
    @Param(group = "firstGroup", forResult = true)
    String stringParam;
    @Param(group = "firstGroup", forResult = true)
    Boolean booleanParam;
    @Param(group = "secondGroup")
    Double doubleParam;
    @Param(group = "secondGroup")
    Integer integerParam;


    @Result(group = "firstResultGroup")
    String stringResult = "abcdefghijk";
    @Result(group = "firstResultGroup")
    Book bookResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookResult = new Book();
        bookResult.setAuthor(ForResultActivity.class.getSimpleName());
        bookResult.setPublishTime(2012);
        bookResult.setBookName("Result");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getCanonicalName());
        stringBuilder.append("\n");
        stringBuilder.append(toString());
        classInfo.setText(stringBuilder);


    }

    @Override
    protected View.OnClickListener getOnCloseClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ActivityWithResultOptionResultFiller.fillResultfirstResultGroup(ActivityWithResultOption.this);
                setResult(RESULT_OK, intent);
                finish();
            }
        };
    }
```
```java
Navigator.activityWithResultOptionFirstGroupForResult(BaseActivity.this, "abcdefgh", true, WITH_RESULT_OPTION_ACTIVITY);
ActivityWithResultOptionResultLoader.ActivityWithResultOptionFirstResultGroupResult result8 = ActivityWithResultOptionResultLoader.loadFirstResultGroup(data);
```
```java
Navigator.activityWithResultOptionSecondGroup(BaseActivity.this, 123.53, 25);
```

### Create group of activities
```java
@Navigable(group = "subgroup")
public class GroupedActivity extends Activity
```

```java
Navigator.subgroup.groupedActivity(BaseActivity.this);
```
### Create custom method and support classes name for Activity

```java
@Navigable(name = "customNameWithParams")
public class NamedParamsAndResultActivity extends BaseActivity {

    @Param(forResult = true)
    Integer integerParam;
    @Param(forResult = true)
    String stringParam;
    @Param(group = "OtherGroup")
    Book parcelableParam;

    @Result
    String stringResult = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomNameWithParamsParamLoader.load(this);
    }

    @Override
    protected View.OnClickListener getOnCloseClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CustomNameWithParamsResultFiller.fillResult(NamedParamsAndResultActivity.this);
                setResult(RESULT_OK, intent);
                finish();
            }
        };
    }
}
```

```java
Navigator.customNameWithParams(BaseActivity.this, 21, "some_string", NAMED_WITH_RESULT);
Navigator.customNameWithParams(BaseActivity.this, book);
```
```java
 CustomNameWithParamsResultLoader.CustomNameWithParamsResult result2 = CustomNameWithParamsResultLoader.load(data);
```







