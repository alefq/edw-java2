package py.edu.uca.edw.java2.chat.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import py.edu.uca.edw.java2.chat.business.AuditoriaBC;
import py.edu.uca.edw.java2.chat.exception.BusinessException;

//i vasiki klasi tou Server gia epikoinonia me to Client
//perimeni minimata apo to Client kai antidra analoga
//exei prosvasi stous xristes tou Server meso tou antikeinenou Vector clients tou Server
//tha mporouse na ine edo mesa to clients 
//alla etsi ekatse prota opote to afisa
public class ClientThread extends Thread {
	Logger log = Logger.getLogger(ClientThread.class);

	String nick; // aftonoita
	Boolean connected;
	Socket socket;
	PrintWriter out; // I/O
	BufferedReader in;
	Socket clientSocket;
	AuditoriaBC auditoriaBC = new AuditoriaBC();

	ClientThread(Socket s) {
		super("ClientThread");
		// Molis gini i sindesi den imaste sindedemenoi ...
		connected = false;
		// ... kai den exoume nick
		nick = "";

		// arxikpooiisi anathesi sti metavliti clientSocket to socket pou pirame
		// san parametro sto
		// constructor
		clientSocket = s;
		try {
			// sindesi I/O antikeimenwn me ta streams tou socket
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
		} catch (Exception e) {
			log.error(e);
		}
	}

	// dio antikeimena ClientThread ine ta idia an e3ipiretoun to idio nick
	// edo stis sigrisis mallon tha mporouse na isxii kai to == alla den to
	// epsa3a
	public boolean equals(ClientThread c) {
		return (c.nick.equals(this.nick));
	}

	// apostoli minimatos sto Client
	// apla grafo sto socket
	synchronized void send(String msg) {
		out.println(msg);
	}

	// vasiki methodos leitourgias ClientThread
	// Perimeni kapio input apo to Client
	// An ine kapia gnosti entoli antidra analoga
	// an den stelni apla to minima piso sto Client

	void listen() {
		try {
			// perimeno gia input
			// mexri na ternatiso
			while (true) {
				String msg = in.readLine();
				log.info(msg);
				// login pio kato i methodos
				if (msg.startsWith("Login")) {
					login(msg);
				}
				// logout
				else if (msg.equals("Logout")) {
					// an imaste sindedemenoi
					// afairoume ton eafto mas apo to Server.clients
					// stelno neo list me tous ipoloipous gia na idopoiiso
					// emmesa oti efiga
					// stelno ena minima sto Client gia na 3eri oti egine
					// epitixis sindesi
					// kleino ta streams kai return gia na termatisi to thread

					if (connected) {
						connected = false;
						int k = Server.clients.indexOf(this);
						Server.clients.remove(this);
						sendList();
						out.println("OK");
						out.close();
						in.close();
						clientSocket.close();
						return;
					}
					// aftonoito
					else {
						send("Not Logged in !!");
					}

				}
				// Apostoli minimatos se olous

				else if (msg.startsWith("Post ")) {
					// perno ti lista me ta cThreads kai kalo sto kathena tin
					// entoli send me parametro
					// Receive nick: msg
					// perno to isto stixoio to Server.clients kai elegxo an ine
					// sindedemeno
					for (int i = 0; i < Server.clients.size(); i++) {
						ClientThread threadCliente = (ClientThread) Server.clients
								.get(i);
						if (threadCliente.connected) // isos den xreiazete tora
						// prin ixe diaforetiki ilopoiisi
						{
							threadCliente.send("Recieve " + nick + ": "
									+ msg.substring(5, msg.length()));
							threadCliente.clientSocket.getInetAddress();
						}
					}
				}

				// Private post
				else if (msg.startsWith("PrivatePost ")) {
					// Filtrarisma tou string gia na paro to nickname kai to msg
					// perno san paramatro to substring(12,msg.length()) diladi
					// otidipote meta to PrivatePost
					// delimiter to ", " diladi ta tokens ine kathe ti anamesa
					// se ", " kai arxi kai telos tou string
					StringTokenizer st = new StringTokenizer(msg.substring(12,
							msg.length()), ", ");

					String message = st.nextToken();
					String to = st.nextToken();

					// metavliti gia na doume an iparxei to nick
					// me sosto Client den xreiazete o elegxos
					// alla iparxi se periptosi pou iparxi kapio lathos
					// i to list den ftasi
					boolean success = false;

					// perno to iosto stoixeio kai to elegxo an ine iso me to
					// nick pou theloyme.
					// an ine tote kaloume ti sinartisi tou send me parametro
					// PrivateRecieve nick: msg
					// kai success = true kai break giati den exi noima na
					// sinexisoume
					for (int i = 0; i < Server.clients.size(); i++) {
						ClientThread t = (ClientThread) Server.clients.get(i);
						if (t.nick.equals(to)) {
							t.send("PrivateRecieve " + nick + ": " + message);
							success = true;
							break;
						}
					}

					// an den vrethike to nickname
					if (!success) {
						send("Error: Den iparxi to nick");
					}
				}
				// se periptosi pou den ine kapia gnosti sinartisi
				// stelno piso to monima pou elava
				else {
					send(msg);
				}
			}
		} catch (SocketException e) {
			// otan exo provlima me to socket (o Client figi xoris na stili
			// logout, exit tou programmatos Client
			// gia opiodipote logo
			// idies leitourgies me to Logout
			// pou e3igithike pio pano
			if (connected) {
				try {
					connected = false;
					int k = Server.clients.indexOf(this);
					Server.clients.remove(this);
					sendList();
					out.close();
					in.close();
					clientSocket.close();
					return;
				} catch (Exception d) {
					return;
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	// otan to thread arxisi ti leitourgia kalei run
	public void run() {
		listen();
	}

	boolean login(String msg) {
		// an imaste sindedemenoi
		// den kanoume tipote
		if (connected) {
			out.println("Allready Connected!");
			return true;
		}
		// elexo an iparxi o xristis
		boolean exists = false;
		log.info("Login" + msg.substring(5, msg.length()));
		try {
			auditoriaBC.auditarLogin("Login" + msg.substring(5, msg.length()));
		} catch (BusinessException e) {
			/* Ocurrió un error al intentar auditar el login */
			e.printStackTrace();
		}
		// perno to iosto stoixeio
		// kai to sigrino me to to nick pou perno apo to Client
		for (int i = 0; i < Server.clients.size(); i++) {
			if (Server.clients.get(i) != null) {
				// an iparxei
				// exists = true break
				log.info(msg.substring(7, msg.length()));
				ClientThread temp = (ClientThread) Server.clients.get(i);
				if ((temp.nick).equals(msg.substring(7, msg.length()))) {
					exists = true;
					break;
				}

			}
		}

		// an iparxei stello NewNick
		// an den iparxeo to nicl
		// stelno to List se olous tous xristes
		if (exists) {
			out.println("NewNick");
		} else {
			connected = true;
			nick = msg.substring(7, msg.length());
			sendList();
		}
		return true;
	}

	// stelni tin entoli list se olous tous sindedemenous xristes
	void sendList() {
		// list ine to string pou tha stalei se olous
		String list = "";
		log.info("Cantidad de Clientes: " + Server.clients.size());
		if (Server.clients.size() == 0) {
			return;
		}

		// perno to vector me tous clients kai osoi ine sindedemenoi
		// tous prostheto sto list
		for (int i = 0; i < Server.clients.size(); i++) {
			ClientThread temp = (ClientThread) Server.clients.get(i);
			if (Server.clients.get(i) != null) {
				if (connected) {
					list = temp.nick + "," + list;
				}
			}
		}
		// katharisma
		list = "List " + list.substring(0, list.length() - 1) + ";";
		// Send List to all

		// 3anaperno to vector kai kalo ti sinartisi gia kathe ClientThread me
		// parametro to nick (List: ...)
		for (int i = 0; i < Server.clients.size(); i++) {
			ClientThread t = (ClientThread) Server.clients.get(i);
			if (t.connected) {
				t.send(list);
			}
		}
	}

	// Methodos copy paste apo to google
	// Kanei replace ena string me ena allo
	// Tipote idietero apla to vrika etoimo kai to ensomatosa
	static String replace(String str, String pattern, String replace) {
		int s = 0;
		int e = 0;
		StringBuffer result = new StringBuffer();
		while ((e = str.indexOf(pattern, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(replace);
			s = e + pattern.length();
		}
		result.append(str.substring(s));
		return result.toString();
	}
}