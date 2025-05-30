package com.OrderNet.ProyWebIntegrado.persistence.model.enums;

public enum Permissions {
  // User management
  CREATE_USER,
  EDIT_USER,
  DELETE_USER,
  READ_USER,

  // Waiter operations
  EDIT_WAITER,

  // Product management
  CREATE_PRODUCT,
  EDIT_PRODUCT,
  DELETE_PRODUCT,
  VIEW_PRODUCT,

  // Order operations
  CREATE_ORDER,
  EDIT_ORDER,
  DELETE_ORDER,
  VIEW_ORDER
}
