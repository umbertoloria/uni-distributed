package capitolo2;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class Main {

	public static void main(String[] args) {
		Weld weld = new Weld();
		WeldContainer container = weld.initialize();
		BookService bookService = container.instance().select(BookService.class).get();
		Book book = bookService.createBook("Harry Potter e il Principe Mezzosangue",
				15f, "Libro moooooolto bello");
		System.out.println(book);
		weld.shutdown();
	}

}
