
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Phase;

@Component
@Transactional
public class PhaseToStringConverter implements Converter<Phase, String> {

	@Override
	public String convert(final Phase c) {
		String result;
		Integer result2 = 0;

		if (c == null)
			result = null;
		else
			result2 = c.getId();

		result = result2.toString();
		return result;
	}

}
