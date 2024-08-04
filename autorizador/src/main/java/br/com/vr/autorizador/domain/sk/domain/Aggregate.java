package br.com.vr.autorizador.domain.sk.domain;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import org.springframework.util.Assert;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(value = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class Aggregate<I> extends EntityDomain<I> {
	
	private static final String UNKNOWN_USER  = "unknown";

	@Transient
	private transient List<Object> domainEvents = new ArrayList<>(); // NOSONAR
	
	@CreatedBy
	private String createdBy;

	@CreationTimestamp
	private ZonedDateTime createdAt;
	
	@LastModifiedBy
	private String updatedBy;

	@UpdateTimestamp
	private ZonedDateTime updatedAt;

	@Version
	private Long version;

	@PrePersist
	protected void onCreate() {
		this.createdBy = getAuthenticatedUsername();
		this.createdAt = ZonedDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedBy = getAuthenticatedUsername();
		this.updatedAt = ZonedDateTime.now();
	}

	public abstract I id();

	protected <T> T registerEvent(T event) {
		Assert.notNull(event, "Domain event must not be null");
		this.domainEvents.add(event);
		return event;
	}

	@AfterDomainEventPublication
	protected void clearDomainEvents() {
		this.domainEvents.clear();
	}

	@DomainEvents
	protected Collection<Object> domainEvents() {
		return Collections.unmodifiableList(domainEvents);
	}
	
	private String getAuthenticatedUsername() {
		// TODO Caso utilizado spring security poderia ser obtido do contexto de seguração o usuário autenticado
		return UNKNOWN_USER;
	}

}