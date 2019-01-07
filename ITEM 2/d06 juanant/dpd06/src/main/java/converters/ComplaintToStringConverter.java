
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Complaint;

@Component
@Transactional
public class ComplaintToStringConverter implements Converter<Complaint, String> {

	@Override
	public String convert(final Complaint c) {
		String result;
		Integer result2;

		if (c == null)
			result = null;
		else {
			result2 = c.getId();
			result = result2.toString();
		}
		return result;
	}

}
