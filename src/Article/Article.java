package Article;
// 외부에서 쓸 수 있도록 무조건 public 를 붙여야 한다.

public class Article  extends Dto{
// dto vo ?? 와따가따 해야하는 데이터를 정의
	public String title;
	public String body;
	public int hit;

// 인자 4개인 기존 코드를 받아주기 위해서 추가한 메서드
// this는 한번에 작성이 가능하고 여기에 인자를 5개로 작성하여 아래의 메서드를 읽을 수 있도록 작성
	public Article(int id, String regDate, String title, String body) {
		this(id, regDate, title, body, 0); // 다른 생성자에게 일을 떠넘기는 행위
	}

	public Article(int id, String regDate, String title, String body, int hit) {
		this.id = id;
		this.regDate = regDate;
		this.title = title;
		this.body = body;
		this.hit = hit;
	}

// 조회수를 작성하기 위해 메서드 추가
	public void increaseHit() {
		hit++;
	}
}