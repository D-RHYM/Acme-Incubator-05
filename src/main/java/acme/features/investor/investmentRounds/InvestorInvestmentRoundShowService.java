
package acme.features.investor.investmentRounds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Investor;
import acme.features.investor.applications.InvestorApplicationRepository;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class InvestorInvestmentRoundShowService implements AbstractShowService<Investor, InvestmentRound> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InvestorInvestmentRoundRepository	repository;

	@Autowired
	private InvestorApplicationRepository		applicationRepository;


	@Override
	public boolean authorise(final Request<InvestmentRound> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<InvestmentRound> request, final InvestmentRound entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		Principal principal = request.getPrincipal();
		int investorId = principal.getActiveRoleId();
		int investmentId = entity.getId();
		Application application = this.applicationRepository.findOneByInvestorIdAndInvestmentRoundId(investorId, investmentId);

		int id = request.getPrincipal().getActiveRoleId();
		int totalApplications = this.applicationRepository.findApplicationsByInvestmentRoundId(entity.getId(), id);

		model.setAttribute("applications", totalApplications);

		request.getModel().setAttribute("application", application);

		request.unbind(entity, model, "ticker", "moment", "round", "title", "description", "amount", "link");

	}

	@Override
	public InvestmentRound findOne(final Request<InvestmentRound> request) {
		assert request != null;

		InvestmentRound res;
		int id;
		id = request.getModel().getInteger("id");
		res = this.repository.findOneById(id);
		return res;
	}

}
