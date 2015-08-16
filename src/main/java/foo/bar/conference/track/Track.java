package foo.bar.conference.track;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalTime;

import foo.bar.conference.event.Event;
import foo.bar.conference.scheduler.helper.StringConstants;

public class Track
{
	private List<Event> morningSessionTasks = new ArrayList<Event>();
	private List<Event> afterNoonSessionTasks = new ArrayList<Event>();

	public static final int MORNING_SESSION_DURATION = 180;
	public static final int AFTERNOON_SESSION_DURATION = 240;

	public boolean addEvent(Event event)
	{
		if (getFreeTimeInAfterNoonSession() >= event.getDuration())
		{
			if (afterNoonSessionTasks.size() == 0)
			{
				LocalTime startTime = new LocalTime(13, 0);
				event.setStartTime(startTime);
			}
			else
			{
				Event lastEvent = afterNoonSessionTasks.get(afterNoonSessionTasks.size() - 1);
				LocalTime startTime = lastEvent.getEndTime();
				event.setStartTime(startTime);
			}
			afterNoonSessionTasks.add(event);
			return true;
		}
		else if (getFreeTimeInMorningSession() >= event.getDuration())
		{
			if (morningSessionTasks.size() == 0)
			{
				LocalTime startTime = new LocalTime(9, 0);
				event.setStartTime(startTime);
			}
			else
			{
				Event lastTalk = morningSessionTasks.get(morningSessionTasks.size() - 1);
				LocalTime startTime = lastTalk.getEndTime();
				event.setStartTime(startTime);
			}
			morningSessionTasks.add(event);
			return true;
		}
		else
		{
			return false;
		}
	}

	private int getScheduledTime(List<Event> talks)
	{
		int scheduledTime = 0;
		if (talks == null)
		{
			return scheduledTime;
		}
		for (Event talk : talks)
		{
			scheduledTime += talk.getDuration();
		}
		return scheduledTime;
	}

	private int getFreeTimeInAfterNoonSession()
	{
		return AFTERNOON_SESSION_DURATION - getScheduledTime(afterNoonSessionTasks);
	}

	private int getFreeTimeInMorningSession()
	{
		return MORNING_SESSION_DURATION - getScheduledTime(morningSessionTasks);
	}

	public void printSchedule()
	{
		List<Event> eventList = new ArrayList<Event>(morningSessionTasks);
		Event lunch = new Event(StringConstants.LUNCH, 60);
		LocalTime lunchstartTime = new LocalTime(12, 0);
		lunch.setStartTime(lunchstartTime);
		eventList.add(lunch);
		eventList.addAll(afterNoonSessionTasks);

		for (Event event : eventList)
		{
			System.out.println(event);
		}
	}

	public void addNetworkingEvent()
	{
		Event networkingEvent = new Event(StringConstants.NETWORKING_EVENT, 60);
		Event lastTalk = afterNoonSessionTasks.get(afterNoonSessionTasks.size() - 1);
		LocalTime startTime =
		        lastTalk.getEndTime().isBefore(new LocalTime(16, 0)) ? new LocalTime(16, 0) : lastTalk.getEndTime();
		networkingEvent.setStartTime(startTime);
		afterNoonSessionTasks.add(networkingEvent);
	}
}
