# Navigator
Navigator code generator for Android.

##I want to:

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


### Create group of activities
```java
@Navigable(group = "subgroup")
public class GroupedActivity extends Activity
```

```java
Navigator.subgroup.groupedActivity(BaseActivity.this);
```

