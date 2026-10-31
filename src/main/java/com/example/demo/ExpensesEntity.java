package com.example.demo;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "expenses")
@Data
public class ExpensesEntity {
	@Id //主キーの指定
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id; //ID
    
    @Column(nullable = true)
    private LocalDate date; // 日にち
    
    @Column(length = 10, nullable = true)
    private String classification; // 分類
    
    @Column(length = 20, nullable = true)
    private String name; // 名称

    @Column(nullable = true)
    private Integer price; // 価格
    
}
