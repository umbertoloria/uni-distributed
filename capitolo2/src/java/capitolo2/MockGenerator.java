package capitolo2;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import java.util.Random;
import java.util.logging.Logger;

@Alternative
@ThirteenDigits
public class MockGenerator implements NumberGenerator {

	@Inject
	private Logger logger;

	@Override
	public String generateNumber() {
		String mock = "MOCK-" + Math.abs(new Random().nextInt());
		logger.info("Generated MOCK: " + mock);
		return mock;
	}

}
