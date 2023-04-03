package org.acme;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class EntityWithLobsTest
{
	@Test
	@TestTransaction
	void testListNoFetch()
	{
		var r = EntityWithLobs.uniqueResult(false);
		assertAll(() -> assertFalse(Hibernate.isPropertyInitialized(r, "json")),
				() -> assertFalse(Hibernate.isPropertyInitialized(r, "log")));
	}

	@Test
	@TestTransaction
	void testListWithFetch()
	{
		var r = EntityWithLobs.uniqueResult(true);
		assertAll(() -> assertTrue(Hibernate.isPropertyInitialized(r, "json")),
				() -> assertTrue(Hibernate.isPropertyInitialized(r, "log")));
	}
}
