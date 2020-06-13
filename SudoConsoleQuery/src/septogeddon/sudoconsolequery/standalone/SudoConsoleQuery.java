package septogeddon.sudoconsolequery.standalone;

import septogeddon.pluginquery.api.QueryConnection;
import septogeddon.pluginquery.api.QueryMessageListener;
import septogeddon.pluginquery.api.QueryMessenger;
import septogeddon.pluginquery.utils.DataBuffer;

public abstract class SudoConsoleQuery implements QueryMessageListener {

//	public static void main(String[]args) throws Throwable {
//		PluginQuery.initializeDefaultMessenger();
//		EncryptionToolkit toolkit = new EncryptionToolkit(EncryptionToolkit.readKey(new File("D:\\TestServer\\plugins\\PluginQuery\\secret.key")));
//		PluginQuery.getMessenger().getPipeline().addLast(
//				new QueryDecryptor(toolkit.getDecryptor()),
//				new QueryDeflater(),
//				new QueryInflater(),
//				new QueryEncryptor(toolkit.getEncryptor())
//				);
//		SudoConsoleQuery sudo = new SudoConsoleQuery("test", PluginQuery.getMessenger()) {
//			
//			@Override
//			public void response(String[] text) {
//				for (String x : text) {
//					System.out.println(x);
//				}
//			}
//		};
//		QueryConnection conn = PluginQuery.getMessenger().newConnection(new InetSocketAddress("localhost", 25565));
//		conn.connect();
//		sudo.sudoConsole(conn, "plugins", false);
//		sudo.sudoConsole(conn, "version", false);
//		sudo.sudoConsole(conn, "help", false);
//	}
	
	private String clientName;
	private QueryMessenger messenger;
	public SudoConsoleQuery(String clientName, QueryMessenger messenger) {
		this.clientName = clientName;
		this.messenger = messenger;
	}
	
	public QueryMessenger getMessenger() {
		return messenger;
	}
	
	public void sudoConsole(QueryConnection conn, String command, boolean printConsole) {
		conn.getEventBus().registerListener(this);
		DataBuffer buffer = new DataBuffer();
		buffer.writeUTF("CONSOLE_COMMAND");
		buffer.writeBytes("STANDALONE:"+clientName);
		buffer.writeBoolean(printConsole);
		buffer.writeChars(command);
		conn.sendQuery("septogeddon:sudoconsole", buffer.toByteArray());
	}
	
	@Override
	public void onQueryReceived(QueryConnection connection, String channel, byte[] message) {
		if ("septogeddon:sudoconsole".equals(channel)) {
			DataBuffer buffer = new DataBuffer(message);
			String sub = buffer.readUTF();
			if ("CONSOLE_FEEDBACK".equals(sub)) {
				String client = buffer.readBytes();
				if (!client.equals("STANDALONE:"+clientName)) return;
				int length = buffer.readInt();
				String[] array = new String[length];
				for (int i = 0; i < length; i++) array[i] = buffer.readChars();
				response(array);
			}
		}
	}
	
	public abstract void response(String[] text);
	
}
