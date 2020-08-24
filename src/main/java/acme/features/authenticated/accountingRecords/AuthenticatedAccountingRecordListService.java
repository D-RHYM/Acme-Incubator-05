
package acme.features.authenticated.accountingRecords;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.accountingRecords.AccountingRecord;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractListService;

@Service
public class AuthenticatedAccountingRecordListService implements AbstractListService<Authenticated, AccountingRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedAccountingRecordRepository repository;


	@Override
	public boolean authorise(final Request<AccountingRecord> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<AccountingRecord> request, final AccountingRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "moment");

	}

	@Override
	public Collection<AccountingRecord> findMany(final Request<AccountingRecord> request) {
		assert request != null;

		Collection<AccountingRecord> res;
		int id = request.getModel().getInteger("id");
		List<AccountingRecord> lista = this.repository.findManyByInvestmentRoundId(id).stream().distinct().collect(Collectors.toList());
		res = lista;

		return res;
	}

}
