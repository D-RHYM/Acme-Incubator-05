
package acme.features.entrepreneur.investmentRounds;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.customisationParameters.CustomisationParameters;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepreneur;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class EntrepreneurInvestmentRoundCreateService implements AbstractCreateService<Entrepreneur, InvestmentRound> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private EntrepreneurInvestmentRoundRepository repository;


	@Override
	public boolean authorise(final Request<InvestmentRound> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<InvestmentRound> request, final InvestmentRound entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void unbind(final Request<InvestmentRound> request, final InvestmentRound entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "moment", "title", "round", "description", "link", "amount", "finalMode");

	}

	@Override
	public InvestmentRound instantiate(final Request<InvestmentRound> request) {
		assert request != null;

		InvestmentRound res;
		res = new InvestmentRound();
		Principal principal = request.getPrincipal();
		int id = principal.getAccountId();
		Entrepreneur entrepreneur = this.repository.findOneEntrepreneurById(id);
		res.setEntrepreneur(entrepreneur);
		Date moment;
		moment = new Date();
		res.setMoment(moment);
		res.setFinalMode(false);

		return res;
	}

	@Override
	public void validate(final Request<InvestmentRound> request, final InvestmentRound entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Principal principal = request.getPrincipal();
		int id = principal.getActiveRoleId();

		Collection<String> tickers = this.repository.findAllTickers();
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		String activitySectorOfThisEntrepreneur = this.repository.findSectorOfEntrepreneur(id);

		//Ticker errors
		if (!errors.hasErrors("ticker")) {

			Boolean letters = request.getModel().getAttribute("ticker").toString().toUpperCase().substring(0, 3).equals(activitySectorOfThisEntrepreneur.toUpperCase().subSequence(0, 3));

			Boolean yearDigits = request.getModel().getAttribute("ticker").toString().substring(4, 6).equals(yearInString.substring(2, 4));

			Boolean tickerAvailable = !tickers.contains(request.getModel().getAttribute("ticker"));

			errors.state(request, letters, "ticker", "entrepreneur.investmentRound.form.error.tickerLetters", entity.getTicker());
			errors.state(request, yearDigits, "ticker", "entrepreneur.investmentRound.form.error.tickerYearDigits", entity.getTicker());
			errors.state(request, tickerAvailable, "ticker", "entrepreneur.investmentRound.form.error.existingTicker", entity.getTicker());
		}

		CustomisationParameters customisation = this.repository.findCustomisationParameters();
		String[] CustomisationParameter;
		Integer n = 0;

		//Spam title
		if (!errors.hasErrors("title")) {

			Double spam = Double.valueOf(entity.getTitle().split(" ").length) * customisation.getSpamThreshold() / 100.0;
			CustomisationParameter = customisation.getSpamWords().split(",");

			for (String s : CustomisationParameter) {
				String title = entity.getTitle().toLowerCase();
				int i = title.indexOf(s);
				while (i != -1) {
					n++;
					title = title.substring(i + 1);
					i = title.indexOf(s);
				}
				errors.state(request, n <= spam, "title", "entrepreneur.investmentRound.form.error.spamTitle");
				if (n > spam) {
					break;
				}
			}

		}

		//Spam description
		if (!errors.hasErrors("description")) {

			Double spam = Double.valueOf(entity.getDescription().split(" ").length) * customisation.getSpamThreshold() / 100.0;
			CustomisationParameter = customisation.getSpamWords().split(",");

			for (String s : CustomisationParameter) {
				String l = entity.getDescription().toLowerCase();
				int i = l.indexOf(s);
				while (i != -1) {
					n++;
					l = l.substring(i + 1);
					i = l.indexOf(s);
				}
				errors.state(request, n <= spam, "description", "entrepreneur.investmentRound.form.error.spamDescription");
				if (n > spam) {
					break;
				}
			}

		}

		//Dinero incorrecto
		if (!errors.hasErrors("amount")) {
			errors.state(request, entity.getAmount().getCurrency().equals("EUR") || entity.getAmount().getCurrency().equals("€"), "amount", "entrepreneur.investmentRound.form.error.amountError");
		}
	}

	@Override
	public void create(final Request<InvestmentRound> request, final InvestmentRound entity) {
		assert request != null;
		assert entity != null;

		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);
		entity.setFinalMode(false);

		this.repository.save(entity);

	}

}
