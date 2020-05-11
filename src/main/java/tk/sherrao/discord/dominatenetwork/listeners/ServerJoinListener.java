package tk.sherrao.discord.dominatenetwork.listeners;

import java.awt.Color;
import java.util.EventListener;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;
import tk.sherrao.discord.dominatenetwork.Bot;
import tk.sherrao.discord.dominatenetwork.BotInformation;
import tk.sherrao.logging.Logger;

public class ServerJoinListener implements EventListener {

    private final Bot bot;
    private final JDA api;
    private final Logger log;
    
    private TextChannel channel;
    
    public ServerJoinListener( final Bot bot ) {
        this.bot = bot;
        this.api = bot.getApi();
        this.log = bot.getLog();
        
        this.channel = api.getTextChannelById( BotInformation.JOIN_CHANNEL );
        
    }
    
    @SubscribeEvent
    public void onUserJoinServer( GuildMemberJoinEvent event ) {
        Member member = event.getMember();
        User user = member.getUser();
        log.info( "User " + user.getName() + "#" + user.getDiscriminator()  + " has joined the server!" );
        
        channel.sendMessage( new EmbedBuilder()
                .setThumbnail( user.getAvatarUrl() )
                .setAuthor( user.getName() + " Has Joined the Server!", null, "https://www.shareicon.net/download/2017/06/21/887435_logo_512x512.png" )
                .setTitle( "Everybody welcome them to the server!" )
                .setColor( Color.GREEN )
                .build() )
        
        .complete();
        
        
    }
    
    public Bot getBot() {
        return bot;
        
    }

}
