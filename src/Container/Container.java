package Container;

import Dao.ArticleDao;
import Dao.MemberDao;
import Service.ArticleService;

public class Container {
	public static ArticleDao articleDao;
	public static MemberDao memberDao;
	public static ArticleService articleService;
	
	static {
		articleDao = new ArticleDao();
		memberDao = new MemberDao();
		articleService = new ArticleService();
	} 
}
