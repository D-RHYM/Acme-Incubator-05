
package acme.features.administrator.inquiries;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.inquiries.Inquirie;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractCreateService;

@Service
public class AdministratorInquirieCreateService implements AbstractCreateService<Administrator, Inquirie> {

	// Internal state ---------------------------------------------------------

	@Autowired
	AdministratorInquirieRepository repository;


	@Override
	public boolean authorise(final Request<Inquirie> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Inquirie> request, final Inquirie entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void unbind(final Request<Inquirie> request, final Inquirie entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "deadline", "description", "minMoney", "maxMoney", "email");

	}

	@Override
	public Inquirie instantiate(final Request<Inquirie> request) {
		Inquirie result = new Inquirie();

		Date moment = new Date(System.currentTimeMillis() - 1);
		result.setCreationDate(moment);

		return result;
	}

	@Override
	public void validate(final Request<Inquirie> request, final Inquirie entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("deadline")) {
			Boolean future = entity.getDeadline().after(new Date());
			errors.state(request, future, "deadline", "administrator.inquirie.error.deadline", entity.getDeadline());
		}

		if (!errors.hasErrors("minMoney")) {
			Boolean minMoneyEUR = entity.getMinMoney().getCurrency().equals("EUR") || entity.getMinMoney().getCurrency().equals("€");
			errors.state(request, minMoneyEUR, "minMoney", "administrator.inquirie.error.money", entity.getMinMoney());
		}

		if (!errors.hasErrors("maxMoney")) {
			Boolean maxMoneyEUR = entity.getMaxMoney().getCurrency().equals("EUR") || entity.getMaxMoney().getCurrency().equals("€");
			errors.state(request, maxMoneyEUR, "maxMoney", "administrator.inquirie.error.money", entity.getMaxMoney());
		}

		if (!errors.hasErrors("minMoney") && !errors.hasErrors("maxMoney")) {
			Boolean minMax = entity.getMinMoney().getAmount().compareTo(entity.getMaxMoney().getAmount()) < 0;
			errors.state(request, minMax, "minMoney", "administrator.inquirie.error.minMaxMoney");
		}

	}

	@Override
	public void create(final Request<Inquirie> request, final Inquirie entity) {
		assert request != null;
		assert entity != null;

		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
		entity.setCreationDate(moment);

		this.repository.save(entity);

	}

}
