package Dao;

import java.util.ArrayList;
import java.util.List;

import Article.Article;

public class ArticleDao {
	
	public List<Article> articles;
	
	public ArticleDao() {
		articles = new ArrayList<>(); 
	}
}
// Dao 창고관리자가 가지고 있는 articles 창고 클래스를 만들고 코드 보완
// articleController 에서 사용했던 new ArrayList 를 여기에서 사용하도록 보완