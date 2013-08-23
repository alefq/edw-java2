package py.edu.uca.edw.java2.chat.client;

import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

//klasi pou xeirizete tin epikoinonia me to Server
//antidra se gnostes entoles
public class ReadFromServer extends Thread
{
	
	Logger log = Logger.getLogger(ReadFromServer.class);

	//pios Client mas dimiourgei gia na mporoume na kaloume oles tis methodous
	//ligo mperdemeno alla doulevi
	Client c;
	
	//constructor
	ReadFromServer(Client cc)
	{
		c = cc;
	}
	
	public void run()
	{
		String s; //metavliti pou krata oti diavasi apo to Server
		//perimenoume na stali kati apo to Server
		while (true)
		{
			//an exoume gini logout
			//to Client.logout = true;
			//gia na 3eroun ta threads na termatisoun ti leitourgia tous
			//me to gui ginete System.exit() opote termatizoun ola ara den xreiazete
			if (Client.logout)
			{
				return;
			}
			
			//diavazo apo to socket
			s = Client.read();
			//An ine list
			if (s.startsWith("List"))
			{
				//otan lavoume to List simeni exoume dosi valid nick kai imaste nomima sindedemenoi
				//gui allages
				Client.mainText.setText("Connected as " + Client.nick);
				c.setTitle("Simple Java Chat - " + c.nick + " - Connected to " + c.server);
				//connected = true;
				Client.connected = true;
				//katharizo ti lista me tous clients
				//kai tin 3anadimiourgo
				Client.list.clear();
				String nextNick = "";
				
				//tokenize
				//perno san parametro otidipote meta to List 
				//kai xrisimopoio san delimiter to ", " simfona me to protokollo
				StringTokenizer st = new StringTokenizer(s.substring(5,s.length()),", ");
				
				String temp = null;
				while(st.hasMoreTokens())
				{
					temp = st.nextToken();
					//to telefteo nick exi attached ena ; sto telos to fevgo
					//giafto ena nick den mpori na periexi ; :)
					//iparxei allos tropos na figo to telefteo xaraktira tou telefteou.
					//alla varieme
					Client.list.addElement(replace(temp,";",""));
				}
				
				//ektiposi tis listas
				log.info("List updated: New names: ");
				for (int i = 0; i < Client.list.size();i++)
				{
					log.info(Client.list.get(i) + " ");
				}
				log.info("");
			}
			//an ine recieve to vazo sto mainText sto Gui (xoris to "Recieve")
			else if (s.startsWith("Recieve"))
			{
				Client.mainText.setText(Client.mainText.getText() + "\n" + s.substring(8,s.length()));
				//kolpaki gia autoscroll kato
				Client.mainText.setCaretPosition(Client.mainText.getText().length());
			}
			//Private minima
			//To idio me pano apla kollo ena "Prosopiko minima" apo pano
			else if (s.startsWith("PrivateRecieve"))
			{
				Client.mainText.setText(Client.mainText.getText() + "\n" + "Prosopiko minima: " + s.substring(14,s.length()));
				Client.mainText.setCaretPosition(Client.mainText.getText().length());
			}
			//otan to nick pou dosame ine kratimeno
			else if (s.startsWith("NewNick"))
			
			{   //gui
				//Perno ena kainourgio nick kai 3anadokimazo
				Client.mainText.setText("");
				String newnick =  JOptionPane.showInputDialog(null, "To nickname den ine diathesimo. Dose neo nick:");
				Client.connected = false;
				//prosparmozo ta ipomenou tou actions
				//oste na ine disabled osa prepi
				Client.jMenuItem1.setEnabled(true);
       	 		Client.jMenuItem2.setEnabled(false);
				
				//an patiso cancel sto parathiro pou zita neo nick
				//to newnick ine null
				//ara den kano tipote
				//an den ine null:
				if (newnick != null)
				{
					//enimerono tin metavliti nick
					//kai 3anaprospatho
					//prosarmozo ta ipomenou tou actions analoga
					Client.nick = newnick;
					Client.jMenuItem1.setEnabled(false);
       	 			Client.jMenuItem2.setEnabled(true);
       	 			Client.send("Login: "+newnick);
				}
			}
			log.info(s);
		}
	}
	//Methodos copy paste apo to google
	//Kanei replace ena string me ena allo
	//Tipote idietero apla to vrika etoimo kai to ensomatosa
	static String replace(String str, String pattern, String replace) 
	{
  	  	int s = 0;
  	  	int e = 0;
  	  	StringBuffer result = new StringBuffer();
    	while ((e = str.indexOf(pattern, s)) >= 0) 
    	{
    		result.append(str.substring(s, e));
       	    result.append(replace);
       	    s = e+pattern.length();
    	}
    	result.append(str.substring(s));
    	return result.toString();
    }
}