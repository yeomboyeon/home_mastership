import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Article.Article;
import Article.Member;
import Controller.ArticleController;
import Controller.MemberController;
import YeomIT.Util;

public class App {
	private List<Article> articles;
	private List<Member> members;

	public App() {
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}

	public void start() {
		System.out.println("===== 프로그램 시작 =====");
		makeTestData();

		Scanner sc = new Scanner(System.in);

		MemberController memberController = new MemberController(sc, members);
		
		while (true) {
			System.out.printf("명령어 ) ");
			String command = sc.nextLine().trim();

			if (command.length() == 0) {
				System.out.println("명령어를 입력해주세요");
				continue;
			}

			if (command.equals("system exit")) {
				break;
			}
			if (command.equals("member join")) {
				memberController.doJoin();
			} 
			if (command.equals("article write")) {
				int id = articles.size() + 1;
				String regDate = Util.getNowDateStr();
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();
				Article article = new Article(id, regDate, title, body);
				articles.add(article);

				System.out.printf("%d번글이 생성되었습니다\n", id);
			} else if (command.startsWith("article list")) {
				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다");
					continue;
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
			} else if (command.startsWith("article detail ")) {

				String[] commandBits = command.split(" ");

				int id = Integer.parseInt(commandBits[2]);
				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
					continue;
				}

				foundArticle.increaseHit();

				System.out.printf("번호 : %d\n", foundArticle.id);
				System.out.printf("날짜 : %s\n", foundArticle.regDate);
				System.out.printf("제목 : %s\n", foundArticle.title);
				System.out.printf("내용 : %s\n", foundArticle.body);
				System.out.printf("조회 : %d\n", foundArticle.hit);

			} else if (command.startsWith("article modify ")) {

				String[] commandBits = command.split(" ");

				int id = Integer.parseInt(commandBits[2]);

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
					continue;
				}
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();

				foundArticle.title = title;
				foundArticle.body = body;

				System.out.printf("%d번 게시물을 수정했습니다.\n", id);

			} else if (command.startsWith("article delete ")) {

				String[] commandBits = command.split(" ");

				int id = Integer.parseInt(commandBits[2]);
				int foundIndex = getArticleIndexById(id);

				if (foundIndex == -1) {
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
					continue;
				}
				articles.remove(foundIndex);
				System.out.printf("%d번 게시물을 삭제했습니다.\n", id);

			} else {
				System.out.println("존재하지 않는 명령어 입니다");
			}
		}

		sc.close();

		System.out.println("===== 프로그램 끝 =====");
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

	private void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다.");
		articles.add(new Article(1, Util.getNowDateStr(), "제목1", "내용1", 11));
		articles.add(new Article(2, Util.getNowDateStr(), "제목2", "내용2", 22));
		articles.add(new Article(3, Util.getNowDateStr(), "제목3", "내용3", 33));

	}
}
// 글 삭제 후 다시 생성시 번호 중복 점검 필요