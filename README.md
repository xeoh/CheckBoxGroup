# **Android CheckBoxGroup**
[![version 1.0.1](https://img.shields.io/badge/version-1.0.0-green.svg)]()
[![API Level ≥9](https://img.shields.io/badge/platform-android-lightgrey.svg)](https://developer.android.com/index.html)
[![API ≥2.3](https://img.shields.io/badge/android-API%20Level%20%E2%89%A59-blue.svg)](https://developer.android.com/about/versions/android-2.3.html)
[![API ≥9](https://img.shields.io/badge/android-API%20%E2%89%A52.3-blue.svg)](https://developer.android.com/about/versions/android-2.3.html)
[![MIT LICENSE](https://img.shields.io/github/license/mashape/apistatus.svg)](https://spdx.org/licenses/MIT.html#licenseText)
[![javadoc](https://img.shields.io/badge/document-javadoc-yellow.svg)](https://xeoh.github.io/CheckBoxGroup/)

## Introduction
**CheckBoxGroup provides easy handling of multiple "CheckBox".** Don't use switch, if, or multiple listeners for handling CheckBox. Instead, group all CheckBox with CheckBoxGroup.

![Sample](./images/CheckBoxGroup.gif)

## Requirements
Android API ≥ 2.3 (API Level 9)

## Gradle
You can import CheckBoxGroup from **jcenter**.
```gradle
repositories {
    jcenter()
}

dependencies {
    compile 'com.xeoh.android:checkboxgroup:1.0.1'
}
```

### Don't: Traditional way to use CheckBoxes as options
We need to deal with every `CheckBox` when one changes.
```java
public class MainActivity extends AppCompatActivity {
  private ArrayList<String> options = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    CheckBox option1Btn = (CheckBox) findViewById(R.id.option1);
    CheckBox option2Btn = (CheckBox) findViewById(R.id.option2);
    CheckBox option3Btn = (CheckBox) findViewById(R.id.option3);
    CheckBox option4Btn = (CheckBox) findViewById(R.id.option4);
    CheckBox option5Btn = (CheckBox) findViewById(R.id.option5);

    options.add("option1");
    options.add("option2");
    options.add("option3");
    options.add("option4");
    options.add("option5");

    option1Btn.setOnCheckedChangeListener(onCheckedChangeListener);
    option2Btn.setOnCheckedChangeListener(onCheckedChangeListener);
    option3Btn.setOnCheckedChangeListener(onCheckedChangeListener);
    option4Btn.setOnCheckedChangeListener(onCheckedChangeListener);
    option5Btn.setOnCheckedChangeListener(onCheckedChangeListener);
  }

  private CheckBox.OnCheckedChangeListener onCheckedChangeListener
      = new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
      switch (buttonView.getId()) {
        case R.id.option1:
          if (isChecked) options.add("options1");
          else options.remove("options1");
          break;
        case R.id.option2:
          if (isChecked) options.add("options2");
          else options.remove("options2");
          break;
        case R.id.option3:
          if (isChecked) options.add("options3");
          else options.remove("options3");
          break;
        case R.id.option4:
          if (isChecked) options.add("options4");
          else options.remove("options4");
          break;
        case R.id.option5:
          if (isChecked) options.add("options5");
          else options.remove("options5");
          break;
      }
    }
    Toast.makeText(MainActivity.this,
        options.toString(),
        Toast.LENGTH_LONG).show();
  };
}
```

### Do: Now deal with multiple checkboxes with one class
You can deal with multiple `CheckBoxes` with `CheckBoxGroup`.
Map your checkBoxes to any value. `CheckBoxGroup` gives mapped value for all
selected checkBoxes.

```java
public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    HashMap<CheckBox, String> checkBoxMap = new HashMap<>();
    checkBoxMap.put((CheckBox) findViewById(R.id.option1), "Option1");
    checkBoxMap.put((CheckBox) findViewById(R.id.option2), "Option2");
    checkBoxMap.put((CheckBox) findViewById(R.id.option3), "Option3");
    checkBoxMap.put((CheckBox) findViewById(R.id.option4), "Option4");
    checkBoxMap.put((CheckBox) findViewById(R.id.option5), "Option5");

    CheckBoxGroup<String> checkBoxGroup = new CheckBoxGroup<>(checkBoxMap,
        new CheckBoxGroup.CheckedChangeListener<String>() {
          @Override
          public void onOptionChange(ArrayList<String> options) {
            Toast.makeText(MainActivity.this,
                options.toString(),
                Toast.LENGTH_LONG).show();
          }
        });
  }

  // Becareful. CheckBoxGroup sets onCheckedChangeListener of checkboxes
  // internally. If you need identical listeners for each checkboxes implement
  // that algorithm with CheckBox.OnClick
}
```

### More Usage
```java
// set option1 and 2 checked, 3,4, and 5 unchecked
checkBoxGroup.setValues(Arrays.asList("Option1", "Option2"));

// get entire checkbox values as ArrayList
ArrayList<String> selectedValues = checkBoxGroup.getValues();

// add option6 and make it checked
checkBoxGroup.add((CheckBox) findViewById(R.id.option6), "Option6", true);

// remove option6
checkBoxGroup.add((CheckBox) findViewById(R.id.option6));

// toggle option5's check value
checkBoxGroup.toggleCheckBox("Option5");
```

See more on [Sample Application](https://github.com/xeoh/CheckBoxGroup/tree/master/sample) and [JavaDoc](https://xeoh.github.io/CheckBoxGroup/)

## License
CheckBoxGroup is available under the MIT license. See the LICENSE file for more info.
