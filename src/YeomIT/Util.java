package YeomIT;
import java.text.SimpleDateFormat;
import java.util.Date;
// 가져다가 쓸려고 만들어논 유틸 클래스
public class Util {
	public static String getNowDateStr() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		return format.format(now);
	}
}