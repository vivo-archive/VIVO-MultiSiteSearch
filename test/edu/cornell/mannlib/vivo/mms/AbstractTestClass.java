/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms;

import static junit.framework.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * TODO
 */
public abstract class AbstractTestClass {
	/**
	 * The values produced by this Iterable should match the expected values, in
	 * no particular order.
	 */
	protected <T extends Comparable<T>> void assertUnorderedActualIterable(
			String label, Iterable<T> actual,
			@SuppressWarnings("unchecked") T... expected) {
		assertEqualSets(label, buildSet(expected), buildSet(actual));
	}

	protected <T extends Comparable<T>> void assertEqualSets(String label,
			Set<T> expected, Set<T> actual) {
		if (expected.equals(actual)) {
			return;
		}

		Set<T> missing = new TreeSet<>(expected);
		missing.removeAll(actual);
		Set<T> extras = new TreeSet<>(actual);
		extras.removeAll(expected);

		String message = label;
		if (!missing.isEmpty()) {
			message += ", missing: " + missing;
		}
		if (!extras.isEmpty()) {
			message += ", extra: " + extras;
		}
		assertEquals(message, expected, actual);
	}

	protected <T> Set<T> buildSet(@SuppressWarnings("unchecked") T... array) {
		return new HashSet<>(Arrays.asList(array));
	}

	protected <T> Set<T> buildSet(Iterable<T> iterable) {
		Set<T> set = new HashSet<>();
		for (T t : iterable) {
			set.add(t);
		}
		return set;
	}

}
