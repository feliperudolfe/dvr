package br.com.vr.autorizador.domain.sk.repository;

import java.util.Optional;

import br.com.vr.autorizador.domain.sk.domain.Aggregate;
import br.com.vr.autorizador.domain.sk.domain.DomainObject;

public interface Repository<A extends Aggregate<I>, I> extends DomainObject {

	A save(A aggregate);

	void delete(A aggregate);

	void deleteById(I id);

	Optional<A> findById(I id);

	boolean existsById(I id);

}