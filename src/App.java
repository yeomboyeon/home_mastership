import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Article.Article;
import YeomIT.Util;

public class App {
	// static 삭제
	private List<Article> articles;
	// static 삭제

	App() {
		articles = new ArrayList<>();
	}
	// static 삭제

	public void start() {
		System.out.println("===== 프로그램 시작 =====");
// 테스트 케이스 실행
		makeTestData();

		Scanner sc = new Scanner(System.in);

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

			if (command.equals("article write")) {
// Lastarticleid 를 지움으로써 테스트케이스를 작성한 글 이후로 글 생성 및 번호가 입력되도록 하고 아래의 코드를 작성
				int id = articles.size() + 1;
				String regDate = Util.getNowDateStr();
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();
// 기존 코드 영향을 받지 않고 놔두기 위해서 class Article 상에서 메서드를 보완
				Article article = new Article(id, regDate, title, body);
				articles.add(article);

				System.out.printf("%d번글이 생성되었습니다\n", id);

			} else if (command.equals("article list")) {
				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다");
					continue;
				}
				System.out.println("번호  |  제목  |  조회");
				for (int i = articles.size() - 1; i >= 0; i--) {
					Article article = articles.get(i);

					System.out.printf("%5d | %6s | %4d\n", article.id, article.title, article.hit);
				}
			} else if (command.startsWith("article detail ")) {

				String[] commandBits = command.split(" ");

				int id = Integer.parseInt(commandBits[2]);
// article 하나를 가져와서 foundArticle 변수에 저장하고 id를 불러오겠다
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
// 삭제할때에는 인덱스 번호를 삭제해야 한다. (0부터 시작하기 떄문에 실제 번호가 -1 됨)
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
// 검색해서 나온다면 인덱스 번호를 증가 
			i++;
		}
// 없다는 뜻의 인덱스를 불러올때 음수로 리턴
		return -1; 
	}

// 찾는 메서드 구현 진행중(3단계 구현) 
	private Article getArticleById(int id) {
// getArticleById 가 getArticleIndexById 에게 id 넘겨줄테니 인덱스 번호 알려줘
		int index = getArticleIndexById(id);
// 인덱스로 불러오되, 인덱스는 0부터 시작하기에 음수-1과 같지 않다면으로 함수 작성
// 반복문을 사용하지 않고 바로 검색 가능.
		if (index != -1) {
			return articles.get(index);
		}
// 위에 반복문을 뒤져봤더니 없어서 없다라고 null로 리턴
		return null;
	}

// 찾기 메서드 1단계 찾기 기능이 중복되어 있기에 별도로 메서드를 구현하는 단계임
// article 리스트에 글 수만큼 0부터 계속 반복해서 검색한다.글수만큼(글 번호가 9라면 8까지)
//		for (int i = 0; i < articles.size(); i++) {  
//			Article article = articles.get(i);
// 
//			if (article.id == id) {
//				return article;
//			}
//		}
// 찾기 메서드 2단계 (배열에서 써먹을 수 있는 코드작성법)
//		for (Article article : articles) {
// fir each문과 유사
//			if (article.id == id) {
//				return article;
//			}
//		}

	// static 삭제
	private void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다.");
// 인자가 4개였는데, class Article 에서 인자 hit 추가됨에 따라 여기도 인자를 1개 추가하였음(대신 조회수를 임의로 지정한 상태)
		articles.add(new Article(1, Util.getNowDateStr(), "제목1", "내용1", 11));
		articles.add(new Article(2, Util.getNowDateStr(), "제목2", "내용2", 22));
		articles.add(new Article(3, Util.getNowDateStr(), "제목3", "내용3", 33));

	}
}
