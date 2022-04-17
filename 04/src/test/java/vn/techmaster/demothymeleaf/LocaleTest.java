package vn.techmaster.demothymeleaf;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import org.junit.jupiter.api.Test;

class LocaleTest {
	@Test
	void format_Number_as_Currency_String() {
		double currencyAmount = 1500.00;
		// Create a new Locale
		Locale locale = new Locale("jp", "JP");
		// Create a Currency instance for the Locale
		Currency currency = Currency.getInstance(locale);
		// Create a formatter given the Locale
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
		// Format the Number into a Currency String
		System.out.println(currency.getDisplayName() + ": " + currencyFormat.format(currencyAmount));     
	}

	@Test
	void display_Date_in_different_Locale() {
		Locale[] locales = new Locale[] {
				Locale.JAPAN,
				Locale.CHINA,
				Locale.KOREA,
				Locale.TAIWAN,
				Locale.ITALY,
				Locale.FRANCE,
				Locale.GERMAN,
				new Locale("vi", "VN")
		};

		// Get an instance of current date time
		Date today = new Date();
		for (Locale locale : locales) {
			System.out.printf("Date format in %s = %s%n",
					locale.getDisplayName(), SimpleDateFormat.getDateInstance(
							SimpleDateFormat.LONG, locale).format(today));
		}
	}

}
