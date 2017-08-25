package com.xeoh.android.checkboxgroup;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CheckBoxGroup controls multiple {@link CheckBox}es with mapped values.
 *
 * <p> In general, CheckBox is used for mapping some value. Using CheckBoxGroup,
 * you can simply map those value and get selected values from it.
 *
 * <p> See more on
 * <ul>
 *   <li>{@link #CheckBoxGroup(Map, CheckedChangeListener)}</li>
 *   <li>{@link #put(CheckBox, Object, boolean)}</li>
 *   <li>{@link #remove(CheckBox)}</li>
 *   <li>{@link #getValues()}</li>
 *   <li>{@link #setValues(List)}</li>
 *   <li>{@link #toggleCheckBox(Object)}</li>
 * </ul>
 *
 * @param <T> Type of mapped value
 */
public class CheckBoxGroup<T> {
  private Map<CheckBox, T> checkBoxToValue;
  private Map<T, CheckBox> valueToCheckBox;
  private CheckedChangeListener<T> listener;

  /**
   * Constructor of {@link CheckBoxGroup}.
   *
   * <p> Set mapped value for each {@link CheckBox}.
   *
   * @param checkBoxes Map of {@link CheckBox} to value.
   * @param listener Listens change of CheckBox
   */
  public CheckBoxGroup(Map<CheckBox, T> checkBoxes, CheckedChangeListener<T> listener) {
    this.checkBoxToValue = checkBoxes;
    this.listener = listener;

    valueToCheckBox = new HashMap<>();
    for (Map.Entry<CheckBox, T> entry : this.checkBoxToValue.entrySet()) {
      valueToCheckBox.put(entry.getValue(), entry.getKey());

      entry.getKey().setOnCheckedChangeListener(checkedChangeListener);
    }
  }

  /**
   * Add more {@link CheckBox} with mapped value.
   *
   * <p> Both checkBox and value should not be added before. If CheckBoxGroup already contains one
   * of checkBox or value, it ignores given parameters.
   *
   * @param checkBox {@link CheckBox} to add
   * @param value Mapped value for given checkBox
   * @param checked checked value of checkbox
   */
  public void put(CheckBox checkBox, T value, boolean checked) {
    if (!checkBoxToValue.containsKey(checkBox) && !valueToCheckBox.containsKey(value)) {
      this.checkBoxToValue.put(checkBox, value);
      this.valueToCheckBox.put(value, checkBox);
      checkBox.setChecked(checked);
      listener.onCheckedChange(getValues());
      checkBox.setOnCheckedChangeListener(checkedChangeListener);
    }
  }

  /**
   * Remove {@link CheckBox} with mapped value.
   *
   * @param checkBox checkBox to remove
   */
  public void remove(CheckBox checkBox) {
    if (checkBoxToValue.containsKey(checkBox)) {
      T value = this.checkBoxToValue.get(checkBox);
      checkBox.setOnCheckedChangeListener(null);
      this.valueToCheckBox.remove(value);
      this.checkBoxToValue.remove(checkBox);
      listener.onCheckedChange(getValues());
    }
  }

  /**
   * Get all selected CheckBoxes value.
   *
   * @return arraylist of selected checkbox's mapped value
   */
  public ArrayList<T> getValues() {
    ArrayList<T> values = new ArrayList<>();

    if (checkBoxToValue == null || checkBoxToValue.isEmpty()) {
      return values;
    }

    for (Map.Entry<CheckBox, T> entry : this.checkBoxToValue.entrySet()) {
      if (entry.getKey().isChecked()) {
        values.add(entry.getValue());
      }
    }

    return values;
  }

  /**
   * Make Checkbox checked with mapped value.
   *
   * @param options list of options to check
   */
  public void setValues(List<T> options) {
    if (options == null) {
      for (CheckBox checkBox : checkBoxToValue.keySet()) {
        checkBox.setChecked(false);
      }
      return;
    }

    for (Map.Entry<CheckBox, T> entry : this.checkBoxToValue.entrySet()) {
      entry.getKey().setChecked(options.contains(entry.getValue()));
    }

    if (listener != null) {
      listener.onCheckedChange(getValues());
    }
  }

  /**
   * Toggle only on option.
   *
   * @param value checkbox's mapped value
   */
  public void toggleCheckBox(T value) {
    CheckBox checkBox = valueToCheckBox.get(value);
    checkBox.setChecked(!checkBox.isChecked());

    if (listener != null) {
      listener.onCheckedChange(getValues());
    }
  }

  private CompoundButton.OnCheckedChangeListener checkedChangeListener
      = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
          if (CheckBoxGroup.this.listener != null) {
            CheckBoxGroup.this.listener.onCheckedChange(getValues());
          }
        }
      };

  /**
   * Interface to listen checkbox changes.
   *
   * <p> Checkbox group internally sets
   * {@link android.widget.CompoundButton.OnCheckedChangeListener}.
   * Do not set onCheckedChangeListener manually. Use this interface to get result
   * of CheckBox's checkedChange
   *
   * @param <T> Type of mapped value
   */
  public interface CheckedChangeListener<T> {
    void onCheckedChange(ArrayList<T> values);
  }
}
