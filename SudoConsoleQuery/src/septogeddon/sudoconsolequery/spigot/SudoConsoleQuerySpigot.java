package septogeddon.sudoconsolequery.spigot;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import septogeddon.pluginquery.PluginQuery;
import septogeddon.pluginquery.api.QueryConnection;
import septogeddon.pluginquery.api.QueryMessageListener;
import septogeddon.pluginquery.utils.DataBuffer;

public class SudoConsoleQuerySpigot extends JavaPlugin implements QueryMessageListener {

	public void onEnable() {
		PluginQuery.getMessenger().getEventBus().registerListener(this);
	}

	@Override
	public void onQueryReceived(QueryConnection connection, String channel, byte[] message) {
		if ("septogeddon:sudoconsole".equals(channel)) {
			DataBuffer buf = new DataBuffer(message);
			String sub = buf.readUTF();
			if ("CONSOLE_COMMAND".equals(sub)) {
				String triggerer = buf.readBytes();
				boolean print = buf.readBoolean();
				String command = buf.readChars();
				getServer().getScheduler().runTask(this, ()->{
					try {
						DummyConsoleCommandSender dummy = new DummyConsoleCommandSender(connection, triggerer, Bukkit.getConsoleSender(), print);
						Bukkit.dispatchCommand(dummy, command);
					} catch (Throwable t) {
						StringWriter output = new StringWriter();
						t.printStackTrace(new PrintWriter(output));
						DataBuffer buffer = new DataBuffer();
						buffer.writeUTF("CONSOLE_FEEDBACK");
						buffer.writeBytes(triggerer);
						buffer.writeInt(1);
						buffer.writeChars(output.toString());
						connection.sendQuery("septogeddon:sudoconsole", buffer.toByteArray());
					}
				});
			}
		}
	}
	
}
