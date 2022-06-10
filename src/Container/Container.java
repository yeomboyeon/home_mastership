package Container;

import Dao.ArticleDao;
import Dao.MemberDao;
import Service.ArticleService;
import Service.MemberService;

public class Container {
	public static ArticleDao articleDao;
	public static MemberDao memberDao;
	public static ArticleService articleService;
	public static MemberService memberService;
	
	static {
		articleDao = new ArticleDao();
		memberDao = new MemberDao();
		articleService = new ArticleService();
		memberService = new MemberService();
	} 
}
