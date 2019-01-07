
package converters;

import java.net.URLEncoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.CreditCard;

@Component
@Transactional
public class CreditCardToStringConverter implements Converter<CreditCard, String> {

	@Override
	public String convert(final CreditCard cc) {
		String result;
		StringBuilder builder;

		if (cc == null)
			result = null;
		else
			try {

				builder = new StringBuilder();
				builder.append(URLEncoder.encode(cc.getBrandName(), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(cc.getHolderName(), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(cc.getNumber(), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(Integer.toString(cc.getCVV()), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(Integer.toString(cc.getExpirationMonth()), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(Integer.toString(cc.getExpirationYear()), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(Integer.toString(cc.getId()), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(Integer.toString(cc.getVersion()), "UTF-8"));

				result = builder.toString();
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}

		return result;
	}
}
