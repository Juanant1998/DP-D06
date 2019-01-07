
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PhaseRepository;
import domain.FixUpTask;
import domain.Phase;

@Transactional
@Service
public class PhaseService {

	@Autowired
	private PhaseRepository	phaseRepository;


	public Phase create() {
		return new Phase();
	}
	public Collection<Phase> findAll() {
		return this.phaseRepository.findAll();
	}

	public Phase findOne(final int phaseId) {
		return this.phaseRepository.findOne(phaseId);
	}
	public Phase save(final Phase phase) {
		final Phase p = this.phaseRepository.save(phase);

		return p;
	}
	public Phase saveNew(final Phase phase, final FixUpTask f) {
		final Phase p = this.phaseRepository.save(phase);
		final Collection<Phase> phases = f.getPhases();
		phases.add(phase);
		f.setPhases(phases);

		return p;
	}

	public void delete(final Phase phase) {
		this.phaseRepository.delete(phase);
	}

}
