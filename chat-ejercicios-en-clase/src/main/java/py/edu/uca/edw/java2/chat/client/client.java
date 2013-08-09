package py.edu.uca.edw.java2.chat.client;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

//Vasiki klasi client me GUI
//O sxediasmos tou GUI egine me to programa NetBEANS


class client extends JFrame
{
	static boolean connected; //an imaste sindedemenoi i oxi
	static boolean logout; 
	static Socket cSocket; //To socket gia tin epikoinonia
	static PrintWriter out; //
	static BufferedReader in;// ta streams gia tin epikoinonia meso tou socket
	static userInput uinput; //Thread pou analamvani tin epikoinonia tou  xristi me to Client se konsola
							 //Den xreiazete amesa me ti leitourgia tou gui
	static readFromServer sinput; //Thread pou analamvani epikoinonia me to server
	static DefaultListModel list; //i lista me ta nicknames gia to gui

 	
 	
	void run()
	{
		
		setTitle("Simple Java Chat - Disconnected");
		enter = false; //asimanti metavliti gia tin voithia leitourgias tou gui
		connected = false; //molis ani3i to client DEn imaste sindedemenoi
		
		//Gui Stuff
		//Ta pio polla dimiourgithikan aftomata apo 
		//to NetBEANS. opou alla3e kati iparxoun sxolia
		jScrollPane1 = new javax.swing.JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        inputText = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainText = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		//to list ine antikeimeno DefaultListModel
		//gia ti leitourgia tou list me ta nicknames sto gui
		//stin arxi arxikopoiite na periexi ena stoixeio "NotConnected"
		list = new DefaultListModel();
		list.addElement("Not Connected");
		
		//arxeikopoiisi tou JList antikeimeno me to DefaultListModel
		//oti allages ginonte sto DefaultListModel antikeimeno ginonte orates sto JList
		nickList = new JList(list);
        jMenuBar1 = new javax.swing.JMenuBar();
    
    	//more netbeans stuff
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
		jMenuItem2 = new javax.swing.JMenuItem();
		jMenuItem3 = new javax.swing.JMenuItem();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        inputText.setColumns(20);
        inputText.setRows(5);
        jScrollPane1.setViewportView(inputText);
		
		//oti leei ..
        sendButton.setText("Send");
        
        //merika apo to netbeans kai sto idio motivo ektisa ta ipolipa manual
        //me liga logia otan ginei click sto sendButton ekteleite i sinartisi 
        //sendButtonActionPerformed se afto to antikeimeno
        //Akolouthoun diafore listener me diafora events
        
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });
        
        //otan patithi kapio pliktro
        inputText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inputTextKeyReleased(evt);
            }
        });
        
        //otan to mous ginei clicked sto nickList (JList)
        nickList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nickListMouseClicked(evt);
            }
        });
        
        //to idio me pano alla xreiazete
        inputText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inputTextKeyReleased(evt);
            }
        });
        mainText.setColumns(20);
        mainText.setEditable(false);
        mainText.setRows(5);
        mainText.setLineWrap(true);
        inputText.setLineWrap(true);
        jScrollPane2.setViewportView(mainText);


		jScrollPane3.setViewportView(nickList);
		//Ta menus
        jMenu1.setText("Commands");
		jMenu2.setText("Help");
        jMenuItem1.setText("Connect");
        jMenuItem2.setText("Disconnect");
        jMenuItem3.setText("Usage");
		jMenuItem2.setEnabled(false);
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed3(evt);
            }
        });
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed2(evt);
            }
        });
        jMenu1.add(jMenuItem1);
		jMenu1.add(jMenuItem2);
		jMenu2.add(jMenuItem3);
		
		jMenuBar1.add(jMenu1);
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

		make();
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		mainText.setFont(new Font("Serif", Font.ITALIC, 16));
	}
	//pure netbeans
	void make()
	{
		        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sendButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        pack();
        
        
        setVisible(true);
	}
	
	//Sinartisi pou analamvani to prosopiko minima
	//Anigi ena inputbox kai zita to minima kai to prootha sto server me to analogo prothema
	//simfona me to protokolo

	private void nickListMouseClicked(java.awt.event.MouseEvent evt)
	{
		//an imaste sindedemenoi KAI den kanoume click ston eafto mas
		if (connected && (!nickList.getSelectedValue().equals(nick)))
		{
			//Diavazo to minima
			String msg =  JOptionPane.showInputDialog(null, "Dose to prosopiko minima: ");
			if (msg != null)
			{
				//apostoli me ti methodo send pou ine pio kato
				//Simfona me to protokolo ProvatePost msg, nick
				send("PrivatePost " + msg + ", "+nickList.getSelectedValue());
			}
			//ektiposi sti konsola gia skopous debugging
		 	System.out.println(nickList.getSelectedValue());
		}
	}
	
	static boolean enter; //metavliti voithitiki gia ti leitourgia tou enter
						  //gia na lisi ena provlima tou na katharizi to input kai na min 
						  //stelni tipote apo to inputText otan patithi enter kai den exei keimeno mesa
						  //gia tin idio logo kaleite me dio listener giati itan o monos tropos pou doulepse sosta
    private void inputTextKeyReleased(java.awt.event.KeyEvent evt) 
    {
    	//O protos tropos pou vrika na ele3o an patithike to enter
    	//evala na tiponi to getKeyCode, patisa enter kai tipone 10
    	//idea den exo ti simeni isos ASCII 
        if(evt.getKeyCode() == 10)
        {
           	if (enter)
    		{
    			//apostoli minimatos
    			sendInput();
    			enter = false;
    		}
    		else
    		{
    			
    			enter = true;
    		}
        }
    }
    private void jMenuItem1ActionPerformed2(java.awt.event.ActionEvent evt) 
    {
    	//handler gia to click sto disconnect
    	//stelno sto server logout kai System.exit(0); gia na stamatisoun ola (3) ta threads tou client
    	send("Logout");
		client.logout = true;
		System.exit(0);
    }
    
    //Help stuff
    //ena aplo parathiro me tis vasikes leitourgies
    //Actions - Disconnect
    private void jMenuItem1ActionPerformed3(java.awt.event.ActionEvent evt) 
    {
    	String s;
    	s = "Gia na sindethite Actions - Connect\nGia prosopiko minima click pano sti lista me tous users\nGia public minima grafo kati sto parathiro kai pato enter i send\nGia disconnect Actions - Disconect\n";
    	JOptionPane.showMessageDialog( this, s,"Usage", JOptionPane.INFORMATION_MESSAGE );
    }
    
    static String server;
	//Sidnesi me to server ekteleite otan ginei click sto Actions - Connect
	private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) 
    {
    	
    	setTitle("Connecting ...");
    	logout = false;
		
		//to thread pou analamvani epikoinonia me to server
		//pernoume san parametro ton eafto mas gia na mpori na allazi metavlites kai na kali methodous
		//tou client xoris provlimata
		//Sigekrimena xriastike gia proti fora (kai teleftea) sto gui otan kalousa tin setTitle
		//san client.setTitle apo to readFromServer antikeimeno kai ixa provlimata epidi den mporouse na klithi apo static 
		sinput = new readFromServer(this);
		
		//Isos den xreiazete me to GUI 
		//Perni input apo tin konsola
		uinput = new userInput();
		
		//Arxikopoiiseis
		cSocket = null;
		out = null;
		in = null;
		//Gia na 3ero an ixa provlimata i oxi epikoinonias me to server
		boolean error;
		error = false;
		
		//input gia na dosei o xristis to server, me default timi to localhost
		
		server = JOptionPane.showInputDialog("Parakalo dose server: ", "localhost");
		try 
		{
			//Sindesi
			cSocket = new Socket(server,9999);
			//Sindesi tou I/O me ta streams tou socket
			out = new PrintWriter(cSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
			//enar3i threads gia I/O me to server
			uinput.start();
			sinput.start();
		}
		catch(UnknownHostException e)
		{
			//Se periptosi Error epikoinonias me to server (UnknownHost)
			JOptionPane.showMessageDialog( this, "Den ine efikti i epikoinonia me to server","ERROR", JOptionPane.ERROR_MESSAGE );
			System.out.println("Host Error" + e);
			setTitle("Simple Java Chat - Cannot Connect Please Try another server");
			error = true;
		}
		catch (IOException e)
		{
			System.out.println("IOException" + e);
		}
		if (!error)
		{
			//an den ixa error zito to nickname
       	 	nick = null;
       	 	nick = JOptionPane.showInputDialog(null, "NickName: ");
       	 	
       	 	
       	 	//to nick den mpori na exi ";"
       	 	//e3igisi sti readFromServer.java sta sxolia
       	 	//Sto List
       	 	while(nick.contains(";"))
       	 	{
       	 		nick = JOptionPane.showInputDialog(null, "To Nickname den mporei na exi mesa \";\". Dose ena kainourgio.");
       	 	}
       	 	
       	 	
       	 	//stelno to Login: 
       	 	//kai i periptosi pou iparxi idi to nick kaliptete sto readFromServer antikeimeno
       	 	//O client theorite connected otan lavi List: minima
       	 	//episis sto readFromServer antikeimeno
       	 	send("Login: "+nick);
       	 	
       	 	//Gui allages sta menu
       	 	if (nick != null)
       	 	{
       	 		jMenuItem1.setEnabled(false);
       	 		jMenuItem2.setEnabled(true);       	 		
       	 	}			
		}
    }

	//kaleite otan ginei click sto koumpi Send
    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) 
    {
		sendInput();	    
    }
	
	//Apostoli minimatos
	//Geniki entoli
	static void send(String msg)
	{
		out.println(msg);
	}
	//methodos gia anagnosi apo to server
	//kaleite apo to readFromServer antikeimeno
	//isos kapos anorthodo3a
	static String read()
	{
		
		String s = null;
		try 
		{
			//Perimenei na stilei kati o server
			//kai to anatheti stin s
			s = in.readLine();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		
		return s;
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
    //Sinartisi pou kaleite na stilei to periexomenou tou input JTextArea sto server
    //kaleite otan iparxei kati sto input (den ine keno)
  
    void sendInput()
    {
    	//an den imaste sindedemenoi error kai katharizoume to input
    	if (!connected)
    	{
    		JOptionPane.showMessageDialog( this, "Not connected! Actions - Connect","Error", JOptionPane.ERROR_MESSAGE );
    		inputText.setText("");
    	}
    	//an den iparxi tipote grammeno sto input den kanoume tipote
    	else if(inputText.getText().equals("") || inputText.getText().equals("\n") ||  inputText.getText()== null  )
    	{
    		inputText.setText("");
    	}
    	else
    	{
    		  //fevgo ta new line (\n) apo to input giati dimiourgoun provlima kai varieme na alla3o to server :)
    		  //kai stelno to minima
    		  send("Post " + replace(inputText.getText(),"\n"," "));
    	      inputText.setText("");
    	}
    }
    //netbeans dilosis metavlitwn
    public static String nick;
	    private javax.swing.JTextArea inputText;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    static public javax.swing.JMenuItem jMenuItem1;
    static public javax.swing.JMenuItem jMenuItem2;
    static public javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JTextArea mainText;
    private javax.swing.JList nickList;
    private javax.swing.JButton sendButton;

}