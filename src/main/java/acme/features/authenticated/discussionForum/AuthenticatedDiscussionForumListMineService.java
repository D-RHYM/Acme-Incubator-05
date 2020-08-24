
package acme.features.authenticated.discussionForum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.discussionForum.DiscussionForum;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;

@Service
public class AuthenticatedDiscussionForumListMineService implements AbstractListService<Authenticated, DiscussionForum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedDiscussionForumRepository repository;


	@Override
	public boolean authorise(final Request<DiscussionForum> request) {
		assert request != null;

		return request.getPrincipal().hasRole(Authenticated.class);
	}

	@Override
	public void unbind(final Request<DiscussionForum> request, final DiscussionForum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "investmentRound.title", "investmentRound.description", "creator.userAccount.username", "investmentRound.ticker");

	}

	@Override
	public Collection<DiscussionForum> findMany(final Request<DiscussionForum> request) {
		assert request != null;
		/*
		 * List<DiscussionForum> lista = new ArrayList<>();
		 * Collection<DiscussionForum> res;
		 * int id = request.getPrincipal().getActiveRoleId();
		 * if (request.getPrincipal().hasRole(Entrepreneur.class)) {
		 * lista = this.repository.findDiscussionForumsByEntrepreneurId(id).stream().distinct().collect(Collectors.toList());
		 * } else if (request.getPrincipal().hasRole(Investor.class)) {
		 * Collection<Application> listaApplication = this.repository.findApplicationByInvestorId(id);
		 * for (Application a : listaApplication) {
		 * for (DiscussionForum d : this.repository.findManyAll()) {
		 * if (a.getInvestmentRound().getId() == d.getInvestmentRound().getId()) {
		 * lista.add(d);
		 * }
		 * }
		 * }
		 * } else {
		 * lista = this.repository.findManyAll().stream().filter(d -> d.getCreator().getId() == request.getPrincipal().getActiveRoleId()).collect(Collectors.toList());
		 * }
		 * res = lista;
		 */

		Collection<DiscussionForum> result;
		Principal principal;

		principal = request.getPrincipal();
		result = this.repository.findManyByAuthenticatedId(principal.getActiveRoleId());
		return result;
	}

}
