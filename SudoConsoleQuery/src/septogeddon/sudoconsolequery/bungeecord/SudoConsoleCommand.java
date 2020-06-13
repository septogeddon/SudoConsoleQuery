package septogeddon.sudoconsolequery.bungeecord;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import septogeddon.pluginquery.api.QueryConnection;
import septogeddon.pluginquery.api.QueryFuture;
import septogeddon.pluginquery.bungeecord.BungeePluginQuery;
import septogeddon.pluginquery.utils.DataBuffer;

public class SudoConsoleCommand extends Command {

	public SudoConsoleCommand() {
		super("sudoconsole", "sudoconsolequery.use", "sudocons", "sudoserver", "serverconsole");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length > 1) {
			QueryConnection conn = BungeePluginQuery.getConnection(BungeeCord.getInstance().getServerInfo(args[0]));
			if (conn == null) {
				send(sender, "&cCannot find server with name \"&f"+args[0]+"&c\".");
				return;
			}
			String msg = args[1];
			for (int i = 2; i < args.length; i++) msg += " "+args[i];
			DataBuffer buffer = new DataBuffer();
			buffer.writeUTF("CONSOLE_COMMAND");
			if (sender instanceof ProxiedPlayer) {
				buffer.writeUTF("PLAYER:"+sender.getName());
			} else {
				buffer.writeUTF("CONSOLE");
			}
			buffer.writeBoolean(true);
			buffer.writeChars(msg);
			QueryFuture<QueryConnection> future = conn.sendQuery("septogeddon:sudoconsole", msg.getBytes());
			final String command = msg;
			future.addListener(fut->{
				if (fut.isSuccess()) {
					send(sender, "&aSuccessfully executed command on server \"&f"+args[0]+"&a\": &e"+command);
				} else {
					send(sender, "&cFailed to execute command on server \"&f"+args[0]+"&c\": &e"+fut.getCause());
				}
			});
			return;
		}
		send(sender, "&7SudoConsoleCommand by Septogeddon. Usage: /sudocons <server> <command without slash>");
	}
	
	public static void send(CommandSender sender, String text) {
		sender.sendMessage(TextComponent.fromLegacyText(text));
	}
	
}
