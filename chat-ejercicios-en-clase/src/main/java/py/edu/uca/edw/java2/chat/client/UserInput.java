package py.edu.uca.edw.java2.chat.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import py.edu.uca.edw.java2.chat.i18n.Messages;

//Sinartisi pou xeirizete to I/O apo ti konsola me to xristi. den xreiazete me to gui
public class UserInput extends Thread
{
	public void run()
	{
		BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));		
		while(true)
		{
			//otan ginoume logout allazi (allaze prin to gui) 
			//i metavliti logout gia na termatizoun ta alla threads
			if (Client.logout)
			{
				return;
			}
			
			//Logout palia ekdosi
			//isos buggy
			try
			{
				String command = kin.readLine();
				if (command.equals(Messages.getString("UserInput.0"))) //$NON-NLS-1$
				{
					Client.send(command);
					
					String response = Client.read();
					Client.logout = true;
					return;
				}
				else
				{
					Client.send(command);
				}
			}
			catch (Exception e)
			{
				
			}
		}
	}
}