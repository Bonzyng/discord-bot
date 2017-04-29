import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class Bot {
    protected IDiscordClient client;

    public void createClient(String token, boolean login) { // Returns a new instance of the Discord client
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(token); // Adds the login info to the builder
        try {
            if (login) {
                client = clientBuilder.login(); // Creates the client instance and logs the client in
            } else {
                client = clientBuilder.build(); // Creates the client instance but it doesn't log the client in yet, you would have to call client.login() yourself
            }
        } catch (DiscordException e) { // This is thrown if there was a problem building the client
            e.printStackTrace();
        }
    }

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) { // This method is called when the ReadyEvent is dispatched
        // Send message?
    }

    @EventSubscriber
    public void onMessageEvent(MessageEvent event) {
        if (event.getMessage().getContent().startsWith("!test")) {
            IMessage message = event.getMessage(); // Gets the message from the event object NOTE: This is not the content of the message, but the object itself
            IChannel channel = message.getChannel(); // Gets the channel in which this message was sent.
            try {
                // Builds (sends) and new message in the channel that the original message was sent with the content of the original message.
                new MessageBuilder(this.client).withChannel(channel).withContent("Hi!").build();
            } catch (RateLimitException e) { // RateLimitException thrown. The bot is sending messages too quickly!
                System.err.print("Sending messages too quickly!");
                e.printStackTrace();
            } catch (DiscordException e) { // DiscordException thrown. Many possibilities. Use getErrorMessage() to see what went wrong.
                System.err.print(e.getErrorMessage()); // Print the error message sent by Discord
                e.printStackTrace();
            } catch (MissingPermissionsException e) { // MissingPermissionsException thrown. The bot doesn't have permission to send the message!
                System.err.print("Missing permissions for channel!");
                e.printStackTrace();
            }

        }
    }
}
