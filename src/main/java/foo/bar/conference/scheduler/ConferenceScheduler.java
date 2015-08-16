package foo.bar.conference.scheduler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import foo.bar.conference.event.Event;
import foo.bar.conference.scheduler.helper.StringConstants;
import foo.bar.conference.track.Track;

/*
 * The class contains logic for parsing the input file, scheduling the events and printing the output
 */
public class ConferenceScheduler
{
	private List<Event> eventList = new ArrayList<Event>();
	private List<Track> trackList = new ArrayList<Track>();

	private void parseInput(String fileName)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
		{
			for (String line; (line = br.readLine()) != null;)
			{
				if (line.isEmpty())
				{
					continue;
				}
				int lastSpaceIndex = line.lastIndexOf(StringConstants.SPACE);
				String topic = line.substring(0, lastSpaceIndex);
				String durationString = line.substring(lastSpaceIndex + 1);
				int duration;
				if (durationString.equals(StringConstants.LIGHTNING))
				{
					duration = 5;
				}
				else
				{
					duration =
					        Integer.parseInt(durationString.substring(0,
					                durationString.lastIndexOf(StringConstants.MINUTE)));
				}
				Event talk = new Event(topic, duration);
				this.eventList.add(talk);
			}
		}
		catch (IOException e)
		{
			System.out.println("IOException while reading file " + e.getMessage());
			System.exit(1);
		}
	}

	private void scheduleTasks()
	{
		scheduleByFirstFitDecreasing();
	}

	/*
	 * This method schedules tasks using first fit method.
	 * Firstly, the method reverse sorts the events by duration of events, then it iterates through the reverse
	 * sorted list of events and tries to fit each event in each of the existing tracks. If it succeeds to do so, it
	 * moves to the next event and if it fails to occupy the event in one of the existing tracks, it creates new track
	 * and fits the event into it. Once all the events are occupied, a networking event is added at the end of each
	 * track.
	 */
	private void scheduleByFirstFitDecreasing()
	{
		Collections.sort(eventList);
		Collections.reverse(eventList);

		Event longestEvent = eventList.get(0);
		int longestEventDuration = longestEvent.getDuration();

		Event shortestEvent = eventList.get(eventList.size() - 1);
		int shortestEventDuration = shortestEvent.getDuration();

		if (longestEventDuration > Math.max(Track.MORNING_SESSION_DURATION, Track.AFTERNOON_SESSION_DURATION)
		        || shortestEventDuration < 0)
		{
			System.out.println("Error: Some task durations are beyond the permissible range.\nExiting..");
			System.exit(1);
		}

		for (Event talk : eventList)
		{
			boolean isTalkOccupied = false;
			for (Track track : trackList)
			{
				if (track.addEvent(talk))
				{
					isTalkOccupied = true;
					break;
				}
			}
			if (!isTalkOccupied)
			{
				Track track = new Track();
				track.addEvent(talk);
				trackList.add(track);
			}
		}
		for (Track track : trackList)
		{
			track.addNetworkingEvent();
		}
	}

	private void printTracks()
	{
		int trackCount = 1;
		for (Track track : trackList)
		{
			System.out.println("Track " + trackCount++ + ":");
			track.printSchedule();
		}
	}

	public void schedule(String fileName)
	{
		parseInput(fileName);
		scheduleTasks();
		printTracks();
	}
}
