
package acme.features.authenticated.accountingRecords;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.accountingRecords.AccountingRecord;
import acme.entities.customisationParameters.CustomisationParameters;
import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedAccountingRecordRepository extends AbstractRepository {

	@Query("select a from AccountingRecord a where a.investmentRound.id = ?1")
	Collection<AccountingRecord> findManyByInvestmentRoundId(int id);

	@Query("select i from InvestmentRound i where i.id = ?1")
	InvestmentRound findOneInvestmentRoundById(int id);

	@Query("select a from AccountingRecord a where a.id = ?1")
	AccountingRecord findOneAccountingRecordById(int id);

	@Query("select c from CustomisationParameters c")
	CustomisationParameters findOneCustomisationParameters();
}
