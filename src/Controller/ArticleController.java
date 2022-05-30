package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.text.AbstractDocument.BranchElement;

import Article.Article;
import YeomIT.Util;

// Controller 상속을 통해서 doAction 함수 받아오기
public class ArticleController extends Controller {
	private Scanner sc;
	private List<Article> articles;
	private String command;
	private String actionMethodName;

// 스위치(case) 함수 작성 명령어 이름 쓰고 맞다면 해당 함수 실행하고 break로 빠져나가기
	public void doAction(String command, String actionMethodName) {
		this.command = command;
		this.actionMethodName = actionMethodName;
		switch (actionMethodName) {
		case "list":
			showlist(command);
			break;
		case "write":
			doWrite();
			break;
		case "detail":
			showdetail(command);
			break;
		case "modify":
			domodify(command);
			break;
		case "delete":
			dodelete(command);
			break;
		}
	}

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

	public void showdetail(String command) {
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

	public void domodify(String command) {
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

	public void dodelete(String command) {
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