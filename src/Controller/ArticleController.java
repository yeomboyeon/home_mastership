package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Article.Article;
import YeomIT.Util;

public class ArticleController extends Controller {
	private Scanner sc;
	private List<Article> articles;
	private String command;
	private String actionMethodName;

	public void doAction(String command, String actionMethodName) {
		this.command = command;
		this.actionMethodName = actionMethodName;
		switch (actionMethodName) {
		case "list":
			showlist(command);
			break;
		case "write":
			// 어떤 명령어든 로그인이 되어야 사용 가능하도록 사전에 걸러주기 위해 코드 위치 조정 필요
			if (islogined() == false) {
				System.out.println("로그인 후 사용해주세요");
				break;
			}
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
		default:
			System.out.println("존재하지 않는 명령어입니다.");
			break;
		}
	}

	public ArticleController(Scanner sc) {
		this.sc = sc;
		articles = new ArrayList<>();
	}

// 글 생성시 글 번호 중복되는 거 조치 필요
	// 로그인 상태에서 작성이 되어야 하는 확인 과정을 app에서 실행토록 보완 필요
	private void doWrite() {
		int id = articles.size() + 1;
		String regDate = Util.getNowDateStr();
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		Article article = new Article(id, regDate, loginedMember.id, title, body);
		articles.add(article);

		System.out.printf("%d번 글이 생성되었습니다\n", id);

	}

	private void showlist(String command) {
		if (articles.size() == 0) {
			System.out.println("게시글이 없습니다");
			return;
		}

		String searchKeyword = command.substring("article list".length()).trim();

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
				return;
			}
		}

		System.out.println("번호   |  작성자 |  제목    |  조회");
		for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
			Article article = forPrintArticles.get(i);

			System.out.printf("%5d | %5d | %6s | %4d\n", article.id, article.memberId, article.title, article.hit);
		}

	}

	private void showdetail(String command) {
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
		System.out.printf("작성자 : %s\n", foundArticle.memberId);
		System.out.printf("제목 : %s\n", foundArticle.title);
		System.out.printf("내용 : %s\n", foundArticle.body);
		System.out.printf("조회 : %d\n", foundArticle.hit);

	}
	// 로그인 상태에서 작성이 되어야 하는 확인 과정을 app에서 실행토록 보완 필요
	private void domodify(String command) {
		String[] commandBits = command.split(" ");

		int id = Integer.parseInt(commandBits[2]);

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}
		// 글을 작성한 작성자와 로그인된 회원과 동일하지 않다면 권한이 없도록 적용
		if (foundArticle.memberId != loginedMember.id) {
			System.out.printf("권한이 않습니다.\n");
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
	// 로그인 상태에서 작성이 되어야 하는 확인 과정을 app에서 실행토록 보완 필요
	private void dodelete(String command) {
				String[] commandBits = command.split(" ");

		int id = Integer.parseInt(commandBits[2]);
		Article foundArticle = getArticleById(id);
// 인덱스 적용 부분을 게시물을 확인하고 처리되도록 보완
		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}
		if (foundArticle.memberId != loginedMember.id) {
			System.out.printf("권한이 않습니다.\n");
			return;
		}
		articles.remove(foundArticle);
		System.out.printf("%d번 게시물을 삭제했습니다.\n", id);

	}

	public void makeTestData() {
		System.out.println("테스트를 위한 게시물 데이터를 생성합니다.");
		articles.add(new Article(1, Util.getNowDateStr(), 1, "제목1", "내용1", 11));
		articles.add(new Article(2, Util.getNowDateStr(), 2, "제목2", "내용2", 22));
		articles.add(new Article(3, Util.getNowDateStr(), 2, "제목3", "내용3", 33));

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