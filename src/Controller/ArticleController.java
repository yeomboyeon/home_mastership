package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Article.Article;
import Article.Member;
import Container.Container;
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
		articles = Container.articleDao.articles; // Dao articleDao 에서 일하도록 옮김
	}

// 글 생성시 글 번호 중복되는 거 조치 필요
	private void doWrite() {
// container 에서 가져오도록 코드 보완
		int id = Container.articleDao.getNewId();
		
		String regDate = Util.getNowDateStr();
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		Article article = new Article(id, regDate, loginedMember.id, title, body);
// container 에서 가져오도록 코드 보완
		Container.articleDao.add(article);

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
// 글간격 보완
		System.out.println("   번호|    작성자|     제목|           내용|   조회|");
		for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
			Article article = forPrintArticles.get(i);
			String writerName = "null";

			List<Member> members = Container.memberDao.members;

			for (Member member : members) {
				if (article.memberId == member.id) {
					writerName = member.name;
					break;
				}
			}
// 글간격 보완
			System.out.printf("%5d| %7s| %6s| %-11s| %4d|\n", article.id, writerName, article.title, article.body, article.hit);
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

	private void domodify(String command) {
		String[] commandBits = command.split(" ");

		int id = Integer.parseInt(commandBits[2]);

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}
		if (foundArticle.memberId != loginedMember.id) {
			System.out.printf("권한이 없습니다.\n");
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

	private void dodelete(String command) {
		String[] commandBits = command.split(" ");

		int id = Integer.parseInt(commandBits[2]);
		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}

		if (foundArticle.memberId != loginedMember.id) {
			System.out.printf("권한이 없습니다.\n");
			return;
		}

		articles.remove(foundArticle);
		System.out.printf("%d번 게시물을 삭제했습니다.\n", id);

	}
// Container articleDao 에 있는 자료를 넘겨받는다.// 글간격 보완
	public void makeTestData() {
		System.out.println("테스트를 위한 게시물 데이터를 생성합니다.");
		Container.articleDao.add(new Article(Container.articleDao.getNewId(), Util.getNowDateStr(), 1, "제목1", "안녕하세요", 11));
		Container.articleDao.add(new Article(Container.articleDao.getNewId(), Util.getNowDateStr(), 2, "제목2", "Good Night", 22));
		Container.articleDao.add(new Article(Container.articleDao.getNewId(), Util.getNowDateStr(), 2, "제목3", "Hello World!", 33));
		Container.articleDao.add(new Article(Container.articleDao.getNewId(), Util.getNowDateStr(), 2, "제목4", "어서오세요", 1));
		Container.articleDao.add(new Article(Container.articleDao.getNewId(), Util.getNowDateStr(), 2, "제목5", "끝까지 가보자", 300));

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