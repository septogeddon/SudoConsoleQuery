package septogeddon.sudoconsolequery.bungeecord;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Plugin;
import septogeddon.pluginquery.PluginQuery;
import septogeddon.pluginquery.api.QueryConnection;
import septogeddon.pluginquery.api.QueryMessageListener;
import septogeddon.pluginquery.utils.DataBuffer;

public class BungeeSudoConsoleQuery extends Plugin implements QueryMessageListener {

	public void onEnable() {
		PluginQuery.getMessenger().getEventBus().registerListener(this);
		getProxy().getPluginManager().registerCommand(this, new SudoConsoleCommand());
	}

	@Override
	public void onQueryReceived(QueryConnection connection, String channel, byte[] message) {
		if ("septogeddon:sudoconsole".equals(channel)) {
			DataBuffer buffer = new DataBuffer(message);
			String sub = buffer.readUTF();
			if ("CONSOLE_FEEDBACK".equals(sub)) {
				String sender = buffer.readBytes();
				CommandSender executor;
				if (sender.startsWith("CONSOLE")) {
					executor = getProxy().getConsole();
				} else if (sender.startsWith("PLAYER:")) {
					executor = getProxy().getPlayer(sender.substring(7));
				} else executor = null;
				if (executor != null) {
					int length = buffer.readInt();
					for (int i = 0; i < length; i++) {
						String msg = buffer.readChars();
						SudoConsoleCommand.send(executor, msg);
					}
				}
			}
		}
	}
	
}
