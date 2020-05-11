package tk.sherrao.discord.dominatenetwork;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Game.GameType;
import net.dv8tion.jda.core.hooks.AnnotatedEventManager;
import tk.sherrao.discord.dominatenetwork.listeners.AlertCommand;
import tk.sherrao.discord.dominatenetwork.listeners.IPCommand;
import tk.sherrao.discord.dominatenetwork.listeners.RoleMessageReactionAddListener;
import tk.sherrao.discord.dominatenetwork.listeners.RoleMessageReactionRemoveListener;
import tk.sherrao.discord.dominatenetwork.listeners.ServerJoinListener;
import tk.sherrao.logging.Logger;

public class Bot {

    private final Logger log;
    private JDA api;
    private ScheduledExecutorService executor;
    
    public Bot() {
        this.log = new Logger( "Dominate Network Bot" );
        try {
            this.api = new JDABuilder( AccountType.BOT )
                    .setAudioEnabled( false )
                    .setToken( BotInformation.DISCORD_TOKEN )
                    .setGame( Game.of( GameType.DEFAULT, "dominateuhc.us" ) )
                    .build();
            
            api.awaitReady();
            api.setEventManager( new AnnotatedEventManager() );
            api.addEventListener( new ServerJoinListener( this ) );
            api.addEventListener( new RoleMessageReactionAddListener( this ) );
            api.addEventListener( new RoleMessageReactionRemoveListener( this ) );
            api.addEventListener( new IPCommand( this ) );
            api.addEventListener( new AlertCommand( this ) );
            
        } catch ( LoginException e ) {
            log.error( "Failed to connect to Discord authentication servers. The internet connection on this machine, or Discord might be down at the moment", e );
            System.exit( 0 );
            
        } catch( InterruptedException e ) {
            log.error( "Failed to load the Discord JDA API!", e );
            System.exit( 0 );
            
        }
        
        this.executor = Executors.newScheduledThreadPool( 3 );
        executor.scheduleWithFixedDelay( new TwitterUpdater( this ), 1L, 2L, TimeUnit.SECONDS );

    }
    
    public Logger getLog() {
        return log;
        
    }
    
    public JDA getApi() {
        return api;
        
    }
    
}
