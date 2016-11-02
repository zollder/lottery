package org.app;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Base unit test.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestBase {

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
}
