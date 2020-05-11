package tk.sherrao.discord.dominatenetwork.listeners;

import java.util.EventListener;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;
import tk.sherrao.discord.dominatenetwork.Bot;

public class IPCommand implements EventListener {

    private final Bot bot;
    
    public IPCommand( final Bot bot ) {
        this.bot = bot;
        
    }
    
    @SubscribeEvent
    public void onIPCommandExecute( GuildMessageReceivedEvent event ) {
        Member member = event.getMember();
        Message message = event.getMessage();
        String raw = message.getContentRaw();
        if( raw.startsWith( "!ip" ) ) 
            message.getChannel().sendMessage( member.getAsMention() + ", the IP for the server is 'dominateuhc.us'!" ).complete();
            
        else return;
        
    }
    
    public Bot getBot() {
        return bot;
        
    }
    

}
