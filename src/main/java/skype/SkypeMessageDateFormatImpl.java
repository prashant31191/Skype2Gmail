package skype;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SkypeMessageDateFormatImpl implements SkypeMessageDateFormat {
	private SimpleDateFormat simpleDateFormat;

	public SkypeMessageDateFormatImpl() {
		simpleDateFormat = new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss]");
	}

	@Override
	public String format(Date time) {
		return simpleDateFormat.format(time);
	}

	@Override
	public Date parse(String messageTimeText) {
		try {
			return simpleDateFormat.parse(messageTimeText);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
