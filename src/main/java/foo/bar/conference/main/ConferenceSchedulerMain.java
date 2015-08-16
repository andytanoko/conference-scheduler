package foo.bar.conference.main;

import foo.bar.conference.scheduler.ConferenceScheduler;

public class ConferenceSchedulerMain
{
	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("Error: Invalid number of arguments");
			System.out.println("Usage: <script_name> <path_to_input_file>");
			System.exit(1);
		}
		String fileName = args[0];
		ConferenceScheduler scheduler = new ConferenceScheduler();
		scheduler.schedule(fileName);
	}
}
