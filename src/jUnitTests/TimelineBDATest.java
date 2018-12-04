package jUnitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import BDA.TimelineBDA;

public class TimelineBDATest {
	TimelineBDA timeline = new TimelineBDA();

	@Test
	public void testTimeline(){
		assertNotNull(timeline);
		
		assertNotNull(timeline.getTimeLine());
		assertNotNull(timeline.getOnlyEmail());
		assertNotNull(timeline.getOnlyFacebook());
		assertNotNull(timeline.getOnlyTwitter());
		assertNotNull(timeline.setFilter(""));
		
		assertTrue(timeline.isShowEmail());
		assertTrue(timeline.isShowFacebook());
		assertTrue(timeline.isShowTwitter());
		timeline.setShowEmail(false);
		timeline.setShowFacebook(false);
		timeline.setShowTwitter(false);
		assertFalse(timeline.isShowEmail());
		assertFalse(timeline.isShowFacebook());
		assertFalse(timeline.isShowTwitter());
	}
}
