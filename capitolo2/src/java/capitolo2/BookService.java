package capitolo2;

import javax.inject.Inject;
import java.util.logging.Logger;

public class BookService {

	@Inject
	private Logger logger;

	@Inject
	@ThirteenDigits
	private NumberGenerator numberGenerator;

	public Book createBook(String title, Float price, String description) {
		Book b = new Book();
		b.setTitle(title);
		b.setPrice(price);
		b.setDescription(description);
		b.setNumber(numberGenerator.generateNumber());
		logger.info("New BOOK: " + b);
		return b;
	}

}
