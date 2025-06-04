package com.OrderNet.ProyWebIntegrado.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class NullableField<T> {

  private final T value;
  private final boolean present;

  @JsonCreator
  public NullableField(T value) {
    this.value = value;
    this.present = true;
  }

  @JsonValue
  public T getJsonValue() {
    return value;
  }

  public static <T> NullableField<T> absent() {
    return new NullableField<>(null, false);
  }

  private NullableField(T value, boolean present) {
    this.value = value;
    this.present = present;
  }
}
