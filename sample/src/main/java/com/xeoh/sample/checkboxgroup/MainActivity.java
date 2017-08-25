package com.xeoh.sample.checkboxgroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xeoh.android.checkboxgroup.CheckBoxGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
  CheckBoxGroup<Option> checkBoxGroup;
  TextView resultText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    resultText = (TextView) findViewById(R.id.result_text);

    HashMap<CheckBox, Option> checkBoxMap = new HashMap<>();
    checkBoxMap.put((CheckBox) findViewById(R.id.option1), Option.OPTION1);
    checkBoxMap.put((CheckBox) findViewById(R.id.option2), Option.OPTION2);
    checkBoxMap.put((CheckBox) findViewById(R.id.option3), Option.OPTION3);
    checkBoxMap.put((CheckBox) findViewById(R.id.option4), Option.OPTION4);
    checkBoxMap.put((CheckBox) findViewById(R.id.option5), Option.OPTION5);

    checkBoxGroup = new CheckBoxGroup<>(checkBoxMap, checkedChangeListener);

    findViewById(R.id.button1).setOnClickListener(listener1);
    findViewById(R.id.button2).setOnClickListener(listener2);
    findViewById(R.id.button3).setOnClickListener(listener3);
    findViewById(R.id.button4).setOnClickListener(listener4);
    findViewById(R.id.button5).setOnClickListener(listener5);

    checkBoxGroup.setValues(new ArrayList<Option>());
  }

  private CheckBoxGroup.CheckedChangeListener<Option> checkedChangeListener
        = new CheckBoxGroup.CheckedChangeListener<Option>() {
    @Override
    public void onCheckedChange(ArrayList<Option> values) {
      int totalValue = 0;

      for (Option option : values) {
        totalValue += option.value;
      }

      resultText.setText(String.format(Locale.US, "Total: %d", totalValue));
    }
  };

  private View.OnClickListener listener1 = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      checkBoxGroup.setValues(Arrays.asList(Option.OPTION1, Option.OPTION2));
    }
  };

  private View.OnClickListener listener2 = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      checkBoxGroup.setValues(Arrays.asList(Option.OPTION3, Option.OPTION4, Option.OPTION5));
    }
  };

  private View.OnClickListener listener3 = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       checkBoxGroup.toggleCheckBox(Option.OPTION3);
    }
  };

  private View.OnClickListener listener4 = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      findViewById(R.id.option5_layout).setVisibility(View.VISIBLE);
      CheckBox checkBox = (CheckBox) findViewById(R.id.option5);
      checkBoxGroup.put(checkBox, Option.OPTION5, true);
    }
  };

  private View.OnClickListener listener5 = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      findViewById(R.id.option5_layout).setVisibility(View.INVISIBLE);
      checkBoxGroup.remove((CheckBox) findViewById(R.id.option5));
    }
  };

  private enum Option {
    OPTION1(1),
    OPTION2(3),
    OPTION3(5),
    OPTION4(10),
    OPTION5(20);

    private final int value;

    Option(int value) {
      this.value = value;
    }
  }
}
