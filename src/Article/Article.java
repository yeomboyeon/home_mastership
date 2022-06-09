package Article;

public class Article  extends Dto{
	public String title;
	public String body;
	public int hit;
	public int memberId; // 동명이인이 있을 수 있기 때문에 써먹을 수 없음
	// 1번 작성자 홍길동, 2번 작성자 홍길동 일 수가 있기 때문에..
	// memberloginId 도 가능은 하나, 유일한 값으로 유니크랑 프라이머리의 차이로..  

	public Article(int id, String regDate, int memberId, String title, String body) {
		this(id, regDate, memberId, title, body, 0);
	}

	public Article(int id, String regDate, int memberId, String title, String body, int hit) {
		this.id = id;
		this.regDate = regDate;
		this.memberId = memberId;
		this.title = title;
		this.body = body;
		this.hit = hit;
	}

	public void increaseHit() {
		hit++;
	}

}