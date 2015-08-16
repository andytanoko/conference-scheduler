package foo.bar.conference.event;

import lombok.Data;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import foo.bar.conference.scheduler.helper.StringConstants;

@Data
public class Event implements Comparable<Event>
{
	private String topic;
	private int duration;
	private LocalTime startTime;

	public Event(String topic, int duration)
	{
		this.topic = topic;
		this.duration = duration;
	}

	@Override
	public int compareTo(Event other)
	{
		return Integer.compare(this.duration, other.duration);
	}

	public LocalTime getEndTime()
	{
		return startTime.plusMinutes(duration);
	}

	/*
	 * - This method is overridden to return the event details required in output format.
	 * - The format is - <start_time_of_event_in_`hh:mma`_format> <topic> <duration_of_event>
	 * - Duration of event is not added for `Lunch` and `Networking Event` and is mentioned as `ligntning` for the
	 * events lasting for 5 minutes
	 */
	@Override
	public String toString()
	{
		StringBuilder scheduleStringBuilder = new StringBuilder();

		DateTimeFormatter formatter = DateTimeFormat.forPattern(StringConstants.TIME_PATTERN);
		String startTimeString = formatter.print(startTime);
		scheduleStringBuilder.append(startTimeString);

		scheduleStringBuilder.append(StringConstants.SPACE);
		scheduleStringBuilder.append(topic);

		if (!topic.equals(StringConstants.LUNCH) && !topic.equals(StringConstants.NETWORKING_EVENT))
		{
			scheduleStringBuilder.append(StringConstants.SPACE);

			if (duration == 5)
			{
				scheduleStringBuilder.append(StringConstants.LIGHTNING);
			}
			else
			{
				scheduleStringBuilder.append(duration + StringConstants.MINUTE);
			}
		}
		return scheduleStringBuilder.toString();
	}
}
