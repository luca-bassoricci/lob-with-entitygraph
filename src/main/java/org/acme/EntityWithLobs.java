package org.acme;

import java.util.List;

import org.hibernate.jpa.AvailableHints;

import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;

@Entity
@Table(name = "entity_with_lobs")
@NamedEntityGraph(name = "EntityWithLobs.withLobs", attributeNodes = { @NamedAttributeNode(value = "log"),
		@NamedAttributeNode(value = "json"), })
public class EntityWithLobs extends PanacheEntityBase
{
	@Id
	public long id;
	@Column(name = "`text`")
	public String text;
	// other properties
	@Basic(fetch = FetchType.LAZY)
	@Lob
	@Column(name = "`json`")
	public byte[] json;
	@Basic(fetch = FetchType.LAZY)
	@Lob
	@Column(name = "`log`")
	public byte[] log;

	public static List<EntityWithLobs> list(boolean loadLOBs)
	{
		var q = EntityWithLobs.<EntityWithLobs>findAll();
		if (loadLOBs)
		{
			q = q.withHint(AvailableHints.HINT_SPEC_FETCH_GRAPH,
					Panache.getEntityManager().getEntityGraph("EntityWithLobs.withLobs"));
		}
		return q.firstResult();
	}

	public static EntityWithLobs uniqueResult(boolean loadLOBs)
	{
		var q = EntityWithLobs.<EntityWithLobs>findAll();
		if (loadLOBs)
		{
			q = q.withHint(AvailableHints.HINT_SPEC_FETCH_GRAPH,
					Panache.getEntityManager().getEntityGraph("EntityWithLobs.withLobs"));
		}
		return q.firstResult();
	}
}