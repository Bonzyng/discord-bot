import sx.blah.discord.api.events.EventDispatcher;

public class Main {
    public static void main(String[] args) {
        Bot bot = new Bot();
        bot.createClient(args[0], true); // Gets the client object (from the first example)
        EventDispatcher dispatcher = bot.client.getDispatcher(); // Gets the EventDispatcher instance for this client instance
//        dispatcher.registerListener(new InterfaceListener()); // Registers the IListener example class from above
        dispatcher.registerListener(bot); // Registers the @EventSubscriber example class from above
    }
}
