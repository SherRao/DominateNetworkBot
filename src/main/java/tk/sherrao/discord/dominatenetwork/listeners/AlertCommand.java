package tk.sherrao.discord.dominatenetwork.listeners;

import java.util.EventListener;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;
import tk.sherrao.discord.dominatenetwork.Bot;
import tk.sherrao.discord.dominatenetwork.BotInformation;

public class AlertCommand implements EventListener {

    private final Bot bot;
    
    public AlertCommand( final Bot bot ) {
        this.bot = bot;
        
    }
    
    @SubscribeEvent
    public void onAlertCommandExecute( GuildMessageReceivedEvent event ) {
        Guild server = event.getGuild();
        Message message = event.getMessage();
        String raw = message.getContentRaw();
        
        if( raw.startsWith( "!alert" ) ) {
            String out = raw.replaceFirst( "!alert ", "" );
            Role role = server.getRoleById( BotInformation.REACTION_ROLE_ID );
            for( Member m : server.getMembersWithRoles( role ) ) 
                m.getUser().openPrivateChannel().complete().sendMessage( out ).complete();
            
        } else return;


    }
     
    public Bot getBot() {
        return bot;
            
    }
    
}
