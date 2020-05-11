package tk.sherrao.discord.dominatenetwork.listeners;

import java.util.EventListener;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageReaction.ReactionEmote;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;
import tk.sherrao.discord.dominatenetwork.Bot;
import tk.sherrao.discord.dominatenetwork.BotInformation;

public class RoleMessageReactionAddListener implements EventListener {

    private final Bot bot;
    
    public RoleMessageReactionAddListener( final Bot bot ) {
        this.bot = bot;
        
    }
    
    @SubscribeEvent
    public void onRoleMessageReactionAdd( GuildMessageReactionAddEvent event ) {
        Guild guild = event.getGuild();
        Member member = event.getMember();
        ReactionEmote reaction = event.getReactionEmote();
        if( event.getMessageId().equals( BotInformation.REACTION_ROLE_MSG_ID ) && reaction.getName().equals( BotInformation.REACTION_NAME ) ) {
            Role role = guild.getRoleById( BotInformation.REACTION_ROLE_ID );
            guild.getController().addSingleRoleToMember( member, role ).complete();
            member.getUser().openPrivateChannel()
                .complete().sendMessage( "You now have the " + role.getName() + " role!" )
                .complete();
            
        } else return;  
        
    }
    
    public Bot getBot() {
        return bot;
        
    }
    
}
     