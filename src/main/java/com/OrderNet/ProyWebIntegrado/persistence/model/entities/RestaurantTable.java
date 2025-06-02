package com.OrderNet.ProyWebIntegrado.persistence.model.entities;

import java.util.List;

import com.OrderNet.ProyWebIntegrado.persistence.model.enums.TableStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "restaurant_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RestaurantTable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private Integer number;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TableStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "waiter_id")
  @JsonBackReference
  private User waiter;

  @OneToMany(mappedBy = "table", cascade = CascadeType.ALL, fetch =
  FetchType.LAZY)
  @JsonManagedReference
  private List<Order> orders;
}
