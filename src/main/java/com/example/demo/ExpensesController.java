package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExpensesController {
	
	@Autowired
	ExpensesRepository repository;
	
	@GetMapping("/top")
	public String top(Model model) {
		// 全件取得
		List<ExpensesEntity> list = repository.selectAll();
		model.addAttribute("list", list);
		
		// 価格合計を取得
		Integer totalPrice = repository.getTotalPrice();
		model.addAttribute("totalPrice", totalPrice);
		
		return "top";
	}
	
	@GetMapping("/write")
	public String write(Model model) {
		model.addAttribute("form", new ExpensesEntity());
		return "write";
	}
	
	@PostMapping("/insert")
	@Transactional(readOnly = false)
	public String insert(
			@ModelAttribute("form") ExpensesEntity entity,
			Model model) {
		// 登録
		repository.saveAndFlush(entity);
		return "redirect:/top";
	}
	
	@GetMapping("/classification")
	public String findClassification(
			@RequestParam String classification, Model model) {
		
		// 分類検索
		List<ExpensesEntity> list = repository.findByClassification(classification);
		model.addAttribute("list", list);
		// 分類検索結果の価格合計を取得
		Integer totalPrice = repository.getTotalPriceOfClassification(classification);
		model.addAttribute("totalPrice", totalPrice);
		
		return "top";
	}
	
	@GetMapping("/nameLike")
	public String findNameLike(@RequestParam String name,
			Model model) {
		// 名称が未入力の場合、初期表示処理へリダイレクト
		if ("".equals(name)) {
			return "redirect:/top";
		}
		// 名称であいまい検索
		List<ExpensesEntity> list = repository.findByNameLike("%" + name + "%");
		model.addAttribute("list", list);
		
		// 名称検索結果の価格合計を取得
		Integer totalPrice = repository.getTotalPriceOfNameLike("%" + name + "%");
		model.addAttribute("totalPrice", totalPrice);
		
		return "top";
	}
	
	
	// 問題２
	@GetMapping("/date")
	public String findDate(
			@RequestParam("datefrom") String dateFrom,
			@RequestParam("dateto") String dateTo,
			Model model) {
		
		List<ExpensesEntity> list = null;
		// 日にち検索（開始日・終了日入力あり）
		if (!"".equals(dateFrom) && !"".equals(dateTo)) {
			list = repository.findByDateBetween(dateFrom, dateTo);
			// 日にち検索（開始日のみ入力あり）
		} else if (!"".equals(dateFrom) && "".equals(dateTo)) {
			list = repository.findByDateGreaterThanEqual(dateFrom);
			// 日にち検索（終了日のみ入力あり）
		} else if ("".equals(dateFrom) && !"".equals(dateTo)) {
			list = repository.findByDateLessThanEqual(dateTo);
		} else { // 開始日・終了日が未入力
			return "redirect:/top"; // top画面へリダイレクトする
		}
		model.addAttribute("list", list);
		// 価格合計の算出
		int totalPrice = 0;
		for (ExpensesEntity entity : list) {
			// 取得したEntityが保持する価格を合計
			totalPrice += entity.getPrice();
		}
		model.addAttribute("totalPrice", totalPrice);
		return "top";
	}
	
	
}
