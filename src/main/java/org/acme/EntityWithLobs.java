package org.acme;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

import org.hibernate.graph.GraphSemantic;

import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

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

	public static List<EntityWithLobs> oneResultWithClassCastException()
	{
		return EntityWithLobs.<EntityWithLobs>findAll().firstResult();
	}

	public static EntityWithLobs uniqueResult(boolean loadLOBs)
	{
		var q = EntityWithLobs.<EntityWithLobs>findAll();
		if (loadLOBs)
		{
			q = q.withHint(GraphSemantic.FETCH.getJpaHintName(),
					Panache.getEntityManager().getEntityGraph("EntityWithLobs.withLobs"));
		}
		return q.firstResult();
	}
}