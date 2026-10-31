package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

public interface ExpensesRepository extends JpaRepository<ExpensesEntity, Integer> {
	
	// 全件取得
    @NativeQuery("SELECT * FROM expenses ORDER BY date")
    public List<ExpensesEntity> selectAll();
    
    // すべてのデータの価格合計
    @NativeQuery("SELECT sum(price) FROM expenses ")
    public Integer getTotalPrice();
    
    // 分類検索（ネイティブクエリなし(リポジトリー標準機能)）
    public List<ExpensesEntity> findByClassification(String classification);
    
    // 分類ごとの価格合計
    @NativeQuery("SELECT sum(price) FROM expenses WHERE classification = ?1")
    public Integer getTotalPriceOfClassification(String classification);
    
    // 名称検索（リポジトリー標準機能でも実現可能）
    @NativeQuery("SELECT * FROM expenses WHERE name like ?1")
    public List<ExpensesEntity> findByNameLike(String name);
    
    // 名称検索結果ごとの価格合計
    @NativeQuery("SELECT sum(price) FROM expenses WHERE name like ?1")
    public Integer getTotalPriceOfNameLike(String name);
    
    
    // 問題２
    // 日にち検索（開始日・終了日入力あり）
    @NativeQuery("SELECT * FROM expenses WHERE date BETWEEN ?1 AND ?2")
    public List< ExpensesEntity > findByDateBetween(String dateFrom, String dateTo);
    
    // 日にち検索（開始日のみ入力あり）
    @NativeQuery("SELECT * FROM expenses WHERE date >= ?1")
    public List< ExpensesEntity > findByDateGreaterThanEqual(String dateFrom);
    
    // 日にち検索（終了日のみ入力あり）
    @NativeQuery("SELECT * FROM expenses WHERE date <= ?1")
    public List< ExpensesEntity > findByDateLessThanEqual(String dateTo);
    
}
