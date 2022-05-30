package Controller;

import java.util.ArrayList;
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

	public void showlist(String command) {
		if (articles.size() == 0) {
			System.out.println("게시글이 없습니다");
			return;
		}
		String searchKeyword = command.substring("article list ".length()).trim();

		List<Article> forPrintArticles = articles;

		if (searchKeyword.length() > 0) {
			forPrintArticles = new ArrayList<>();

			for (Article article : articles) {
				if (article.title.contains(searchKeyword)) {
					forPrintArticles.add(article);
				}
			}

			if (forPrintArticles.size() == 0) {
				System.out.println("검색결과가 없습니다");
			}
		}

		System.out.println("번호  |  제목  |  조회");
		for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
			Article article = forPrintArticles.get(i);

			System.out.printf("%5d | %6s | %4d\n", article.id, article.title, article.hit);
		}

	}

	public void showdatail(String command) {
		String[] commandBits = command.split(" ");

		int id = Integer.parseInt(commandBits[2]);
		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}

		foundArticle.increaseHit();

		System.out.printf("번호 : %d\n", foundArticle.id);
		System.out.printf("날짜 : %s\n", foundArticle.regDate);
		System.out.printf("제목 : %s\n", foundArticle.title);
		System.out.printf("내용 : %s\n", foundArticle.body);
		System.out.printf("조회 : %d\n", foundArticle.hit);

	}

	public void showmodify(String command) {
		String[] commandBits = command.split(" ");

		int id = Integer.parseInt(commandBits[2]);

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		foundArticle.title = title;
		foundArticle.body = body;

		System.out.printf("%d번 게시물을 수정했습니다.\n", id);

	}

	public void showdelete(String command) {
		String[] commandBits = command.split(" ");

		int id = Integer.parseInt(commandBits[2]);
		int foundIndex = getArticleIndexById(id);

		if (foundIndex == -1) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}
		articles.remove(foundIndex);
		System.out.printf("%d번 게시물을 삭제했습니다.\n", id);

	}

	private int getArticleIndexById(int id) {
		int i = 0;
		for (Article article : articles) {
			if (article.id == id) {
				return i;
			}
			i++;
		}
		return -1;
	}

	private Article getArticleById(int id) {
		int index = getArticleIndexById(id);

		if (index != -1) {
			return articles.get(index);
		}
		return null;
	}

}

// delete 까지 옮기는 작업 진행중