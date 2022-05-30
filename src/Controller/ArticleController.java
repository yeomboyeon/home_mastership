package Controller;

import java.util.List;
import java.util.Scanner;

import Article.Article;
import YeomIT.Util;

public class ArticleController {
	private Scanner sc;
	private List<Article> articles;

	public ArticleController(Scanner sc, List<Article> articles) {
		this.sc = sc;
		this.articles = articles;
	}

	public void doWrite() {
		int id = articles.size() + 1;
		String regDate = Util.getNowDateStr();
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		Article article = new Article(id, regDate, title, body);
		articles.add(article);

		System.out.printf("%d번 글이 생성되었습니다\n", id);

	}

}
// write 까지 옮기는 작업 진행중