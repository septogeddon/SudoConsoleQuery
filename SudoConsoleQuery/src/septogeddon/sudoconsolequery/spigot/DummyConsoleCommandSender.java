package septogeddon.sudoconsolequery.spigot;

import java.util.Set;

import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import septogeddon.pluginquery.api.QueryConnection;
import septogeddon.pluginquery.utils.DataBuffer;

public class DummyConsoleCommandSender implements ConsoleCommandSender {

	private ConsoleCommandSender sender;
	private QueryConnection connection;
	private String triggerer;
	private boolean print;
	public DummyConsoleCommandSender(QueryConnection conn, String triggerer, ConsoleCommandSender sender, boolean print) {
		this.connection = conn;
		this.sender = sender;
		this.triggerer = triggerer;
		this.print = print;
	}
	
	public void sendFeedback(String... s) {
		DataBuffer buffer = new DataBuffer();
		buffer.writeUTF("CONSOLE_FEEDBACK");
		buffer.writeBytes(triggerer);
		buffer.writeInt(s.length);
		for (String x : s) buffer.writeChars(x);
		connection.sendQuery("septogeddon:sudoconsole", buffer.toByteArray());
	}
	
	
	public String getName() {
		return sender.getName();
	}

	
	public Server getServer() {
		return sender.getServer();
	}

	
	public void sendMessage(String arg0) {
		if (print) sender.sendMessage(arg0);
		sendFeedback(arg0);
	}

	
	public void sendMessage(String[] arg0) {
		if (print) sender.sendMessage(arg0);
		sendFeedback(arg0);
	}

	
	public PermissionAttachment addAttachment(Plugin arg0) {
		return sender.addAttachment(arg0);
	}

	
	public PermissionAttachment addAttachment(Plugin arg0, int arg1) {
		return sender.addAttachment(arg0, arg1);
	}

	
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2) {
		return sender.addAttachment(arg0, arg1, arg2);
	}

	
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2, int arg3) {
		return sender.addAttachment(arg0, arg1, arg2, arg3);
	}

	
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		return sender.getEffectivePermissions();
	}

	
	public boolean hasPermission(String arg0) {
		return sender.hasPermission(arg0);
	}

	
	public boolean hasPermission(Permission arg0) {
		return sender.hasPermission(arg0);
	}

	
	public boolean isPermissionSet(String arg0) {
		return sender.isPermissionSet(arg0);
	}

	
	public boolean isPermissionSet(Permission arg0) {
		return sender.isPermissionSet(arg0);
	}

	
	public void recalculatePermissions() {
		sender.recalculatePermissions();
	}

	
	public void removeAttachment(PermissionAttachment arg0) {
		sender.removeAttachment(arg0);
	}

	
	public boolean isOp() {
		return sender.isOp();
	}

	
	public void setOp(boolean arg0) {
		sender.setOp(arg0);
	}

	
	public void abandonConversation(Conversation arg0) {
		sender.abandonConversation(arg0);
	}

	
	public void abandonConversation(Conversation arg0, ConversationAbandonedEvent arg1) {
		sender.abandonConversation(arg0, arg1);
	}

	
	public void acceptConversationInput(String arg0) {
		sender.acceptConversationInput(arg0);
	}

	
	public boolean beginConversation(Conversation arg0) {
		return sender.beginConversation(arg0);
	}

	
	public boolean isConversing() {
		return sender.isConversing();
	}

	
	public void sendRawMessage(String arg0) {
		if (print) sender.sendRawMessage(arg0);
		sendFeedback(arg0);
	}

	public Spigot spigot() {
		return sender.spigot();
	}

}
